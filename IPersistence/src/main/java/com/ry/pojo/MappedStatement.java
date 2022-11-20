package com.ry.pojo;

import java.util.Objects;

/**
 * @author ryang
 * @Description
 * @date 2022年08月25日 9:08 上午
 */
public class MappedStatement {
    // id 标识
    private String id;
    private Class<?> resultType;
    private Class<?> parameterType;
    private String sql;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Class<?> getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) throws ClassNotFoundException {
        this.resultType = getClassType(resultType);
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) throws ClassNotFoundException {
        this.parameterType = getClassType(parameterType);
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    private Class<?> getClassType(String className) throws ClassNotFoundException {
        if (Objects.nonNull(className)) {
            return Class.forName(className);
        }
        return null;
    }
}
