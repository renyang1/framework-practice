package com.ry.sqlSession;

import com.ry.utils.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ryang
 * @Description
 * @date 2022年08月30日 6:53 下午
 */
public class BoundSql {

    /** 解析后的sql语句 */
    private String sqlText;
    /** 解析后得到的占位符中的变量名集合 */
    private List<ParameterMapping> parameterMappingList = new ArrayList<>();

    public BoundSql(String sqlText, List<ParameterMapping> parameterMappings) {
        this.sqlText = sqlText;
        this.parameterMappingList = parameterMappings;
    }


    public String getSqlText() {
        return sqlText;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }
}
