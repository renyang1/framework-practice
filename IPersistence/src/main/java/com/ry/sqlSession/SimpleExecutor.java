package com.ry.sqlSession;

import com.ry.pojo.Configuration;
import com.ry.pojo.MappedStatement;
import com.ry.utils.GenericTokenParser;
import com.ry.utils.ParameterMapping;
import com.ry.utils.ParameterMappingTokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ryang
 * @Description
 * @date 2022年08月30日 5:15 下午
 */
public class SimpleExecutor implements Executor{

    @Override
    public <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, NoSuchFieldException, IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException {
        // 1.获取数据库连接
        Connection connection = configuration.getDataSource().getConnection();

        // 2. 获取sql语句 : select * from user where id = #{id} and username = #{username}
        //    转换sql语句： select * from user where id = ? and username = ? ，转换的过程中，还需要对#{}里面的值进行解析存储
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        // 3.获取预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        // 4.设置参数
        Class<?> parameterTypeClass = mappedStatement.getParameterType();
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            // 占位符中变量名
            String content = parameterMapping.getContent();

            // 通过反射获取参数值
            Field declaredField = parameterTypeClass.getDeclaredField(content);
            declaredField.setAccessible(true);
            Object param = declaredField.get(params[0]);
            preparedStatement.setObject(i+1, param);
        }

        // 5.执行sql
        ResultSet resultSet = preparedStatement.executeQuery();

        // 6.封装返回结果
        Class<?> resultTypeClass = mappedStatement.getResultType();
        List<Object> resultObjects = new ArrayList<>();
        while (resultSet.next()) {
            Object resultObject = resultTypeClass.newInstance();
            // 元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                // 字段名
                String columnName = metaData.getColumnName(i);
                // 字段值
                Object object = resultSet.getObject(columnName);

                // 使用内省完成将查询结果对返回象对象属性填充
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(resultObject, object);
            }
            resultObjects.add(resultObject);
        }
        return (List<T>) resultObjects;
    }

    /**
     * 解析原sql,将#{}、${}解析为？，并将{}中的变量名进行存储
     * @author ryang
     * @date 2022/8/31 8:47 上午
     * @param sql
     * @return com.ry.sqlSession.BoundSql
     */
    private BoundSql getBoundSql(String sql) {
        // 标记处理类：解析原sql中的占位符并得到占位符中的变量名
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{}", "}", parameterMappingTokenHandler);

        // 解析后的sql
        String parseSql = genericTokenParser.parse(sql);
        // 解析后得到的占位符中的变量名
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

        return new BoundSql(parseSql, parameterMappings);

    }
}
