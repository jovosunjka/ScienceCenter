package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.model.elasticsearch.RequiredHighlight;
import com.jovo.ScienceCenter.model.elasticsearch.ResultData;

import java.util.List;

public interface ResultRetrieverService {

    List<ResultData> getResults(org.elasticsearch.index.query.QueryBuilder query,
                                List<RequiredHighlight> requiredHighlights);
}
