package com.ry.sqlSession;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ryang
 * @Description
 * @date 2022年08月30日 3:54 下午
 */
public interface SqlSession {

    /**
     * 查询所有
     * @author ryang
     * @date 2022/8/30 3:58 下午
     * @param statementId
     * @param params
     * @return java.util.List<E>
     */
    <E> List<E> selectList(String statementId, Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, InstantiationException;

    /**
     * 查询单个
     * @author ryang
     * @date 2022/8/30 3:59 下午
     * @param statementId
     * @param params
     * @return T
     */
    <T> T selectOne(String statementId, Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, InstantiationException;

    /**
     * 为mapper接口生成代理类
     * @author ryang
     * @date 2022/8/30 4:08 下午
     * @param mapperClass
     * @return T
     */
    <T> T getMapper(Class<?> mapperClass);
}
