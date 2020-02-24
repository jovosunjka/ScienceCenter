package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.dto.SimpleQueryWithOperator;
import com.jovo.ScienceCenter.model.elasticsearch.ResultData;

import java.util.List;

public interface SearchService {


    List<ResultData> customSearch(List<SimpleQueryWithOperator> queries);
}
