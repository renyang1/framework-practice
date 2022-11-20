package com.ry.sqlSession;

import com.ry.pojo.Configuration;

/**
 * @author ryang
 * @Description
 * @date 2022年08月30日 4:23 下午
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
