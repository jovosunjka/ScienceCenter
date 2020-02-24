package com.jovo.ScienceCenter.service;


import com.jovo.ScienceCenter.dto.SimpleQueryWithOperator;
import com.jovo.ScienceCenter.model.elasticsearch.LogicalOperator;
import com.jovo.ScienceCenter.model.elasticsearch.RequiredHighlight;
import com.jovo.ScienceCenter.model.elasticsearch.ResultData;
import com.jovo.ScienceCenter.model.elasticsearch.SearchType;
import com.jovo.ScienceCenter.util.QueryBuilder;
import org.apache.lucene.queryparser.classic.ParseException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ResultRetrieverService resultRetrieverService;

    @Override
    public List<ResultData> customSearch(List<SimpleQueryWithOperator> queries) {
        List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();

        BoolQueryBuilder boolQueryBuilder =  QueryBuilders.boolQuery();
        for(SimpleQueryWithOperator q : queries) {
            if (q.getField() == null || q.getValue() == null) {
                continue;
            }

            q.setValue(q.getValue().trim());

            if (q.getValue().isEmpty()) {
                // ako je value prazan string, pretragu po ovom field-u necemo uzeti u obzir
                continue;
            }

            org.elasticsearch.index.query.QueryBuilder query = null;
            try {
                if (q.getValue().startsWith("\"") && q.getValue().endsWith("\"")) {
                    q.setValue(q.getValue().substring(1, q.getValue().length()-1));
                    query = QueryBuilder.buildQuery(SearchType.phrase, q.getField(), q.getValue());
                }
                else {
                    query = QueryBuilder.buildQuery(SearchType.match, q.getField(), q.getValue());
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

            BoolQueryBuilder newBoolQueryBuilder = QueryBuilders.boolQuery();

            if (q.getOperator() == LogicalOperator.NO_OPERATOR) {
                newBoolQueryBuilder.must(query);
            }else if(q.getOperator() == LogicalOperator.AND){
                newBoolQueryBuilder.must(boolQueryBuilder);
                newBoolQueryBuilder.must(query);

            }else if(q.getOperator() == LogicalOperator.OR){
                newBoolQueryBuilder.should(boolQueryBuilder);
                newBoolQueryBuilder.should(query);
            }else if(q.getOperator() == LogicalOperator.AND_NOT){
                newBoolQueryBuilder.must(boolQueryBuilder);
                newBoolQueryBuilder.mustNot(query);
            }

            boolQueryBuilder = newBoolQueryBuilder;

            rh.add(new RequiredHighlight(q.getField(), q.getValue()));
        }

        List<ResultData> results = resultRetrieverService.getResults(boolQueryBuilder, rh);
        return results;
    }
}
