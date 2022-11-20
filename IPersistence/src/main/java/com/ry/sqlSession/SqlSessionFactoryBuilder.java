package com.ry.sqlSession;

import com.ry.config.XmlConfigBuild;
import com.ry.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @author ryang
 * @Description
 * @date 2022年08月25日 9:20 上午
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream in) throws PropertyVetoException, DocumentException, ClassNotFoundException {
        // 1.解析sqlMapConfig.xml 配置文件，将相关信息封装到 configuration 中
        XmlConfigBuild xmlConfigBuild = new XmlConfigBuild();
        Configuration configuration = xmlConfigBuild.parseConfig(in);

        // 2.创建 SqlSessionFactory 对象
        return new DefaultSqlSessionFactory(configuration);
    }

}
