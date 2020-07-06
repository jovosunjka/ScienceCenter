package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.model.UserData;
import com.jovo.ScienceCenter.model.elasticsearch.RequiredHighlight;
import com.jovo.ScienceCenter.model.elasticsearch.ResultData;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.List;

public interface ResultRetrieverService {

    List<ResultData> getResults(org.elasticsearch.index.query.QueryBuilder query,
                                List<RequiredHighlight> requiredHighlights);

    List<UserData> getMoreLikeThisReviewers(org.elasticsearch.index.query.QueryBuilder query);

    boolean getGeoSpatialResult(QueryBuilder queryBuilder, int numOfCities);
}
