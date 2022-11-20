package com.ry.config;

import com.ry.pojo.Configuration;
import com.ry.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * 解析 mapper 标签
 * @author ryang
 * @Description
 * @date 2022年08月30日 8:51 上午
 */
public class XmlMapperBuilder {
    private Configuration configuration;

    public XmlMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException, ClassNotFoundException {
        Document document = new SAXReader().read(inputStream);
        // 根节点
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");

        // 获取所有select元素节点
        List<Element> selectElements = rootElement.selectNodes("select");
        for (Element selectElement : selectElements) {
            String id = selectElement.attributeValue("id");
            String resultType = selectElement.attributeValue("resultType");
            String parameterType = selectElement.attributeValue("parameterType");
            String sqlText = selectElement.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSql(sqlText);
            String key = namespace + "." + id;
            configuration.getMappedStatement().put(key, mappedStatement);
        }
    }

}
