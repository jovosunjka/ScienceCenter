package com.jovo.ScienceCenter.dto;

import com.jovo.ScienceCenter.model.elasticsearch.LogicalOperator;
import com.jovo.ScienceCenter.model.elasticsearch.SimpleQuery;

public class SimpleQueryWithOperator extends SimpleQuery {
    private LogicalOperator operator;

    public SimpleQueryWithOperator() {
        super();
    }


    public SimpleQueryWithOperator(LogicalOperator operator) {
        this.operator = operator;
    }


    public LogicalOperator getOperator() {
        return operator;
    }

    public void setOperator(LogicalOperator operator) {
        this.operator = operator;
    }
}
