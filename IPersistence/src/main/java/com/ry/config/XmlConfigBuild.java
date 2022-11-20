package com.ry.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ry.io.Resources;
import com.ry.pojo.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author ryang
 * @Description
 * @date 2022年08月25日 9:28 上午
 */
public class XmlConfigBuild {
    private Configuration configuration;

    public XmlConfigBuild() {
        this.configuration = new Configuration();
    }

    /**
     * 解析配置文件，封装 Configuration
     * @author ryang
     * @date 2022/8/25 9:29 上午
     * @param inputStream
     * @return com.ry.pojo.Configuration
     */
    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException, ClassNotFoundException {
        Document document = new SAXReader().read(inputStream);
        // 根节点
        Element rootElement = document.getRootElement();
        // 1. 解析dataSource节点
        Element dataSource = rootElement.element("dataSource");
        List<Element> propertyList = dataSource.selectNodes("property");
        Properties dataSourceProperties = new Properties();

        for (Element element : propertyList) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            dataSourceProperties.setProperty(name, value);
        }
        // 创建数据库连接池对象
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(dataSourceProperties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(dataSourceProperties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(dataSourceProperties.getProperty("username"));
        comboPooledDataSource.setPassword(dataSourceProperties.getProperty("password"));
        configuration.setDataSource(comboPooledDataSource);

        // 2.解析mapper节点
        List<Element> mapperElementList = rootElement.selectNodes("mapper");
        for (Element element : mapperElementList) {
            String mapperPath = element.attributeValue("resource");
            InputStream resourceAsStream = Resources.getResourceAsStream(mapperPath);
            new XmlMapperBuilder(configuration).parse(resourceAsStream);
        }
        return configuration;
    }
}
