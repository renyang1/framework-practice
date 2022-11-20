package com.ry.sqlSession;

/**
 * @author ryang
 * @Description
 * @date 2022年08月25日 9:21 上午
 */
public interface SqlSessionFactory {

    SqlSession openSession();
}
