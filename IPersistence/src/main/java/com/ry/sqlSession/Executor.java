package com.ry.sqlSession;

import com.ry.pojo.Configuration;
import com.ry.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ryang
 * @Description
 * @date 2022年08月30日 5:16 下午
 */
public interface Executor {
    <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, NoSuchFieldException, IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException;
}
