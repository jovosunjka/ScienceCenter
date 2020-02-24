package com.jovo.ScienceCenter.controller;


import com.jovo.ScienceCenter.dto.QueriesDTO;
import com.jovo.ScienceCenter.model.elasticsearch.*;
import com.jovo.ScienceCenter.service.ResultRetrieverService;
import com.jovo.ScienceCenter.service.SearchService;
import com.jovo.ScienceCenter.util.QueryBuilder;
import org.apache.lucene.queryparser.classic.ParseException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping(value = "/search")
public class SearchController {

    @Autowired
    private ResultRetrieverService resultRetrieverService;

    @Autowired
    private SearchService searchService;


    @RequestMapping(value = "/custom", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResultData>> customSearch(@RequestBody QueriesDTO queriesDTO) throws Exception {
        List<ResultData> results = searchService.customSearch(queriesDTO.getQueries());
        return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
    }

    @RequestMapping(value = "/term", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
                                                                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResultData>> searchTermQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {
        org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.regular, simpleQuery.getField(), simpleQuery.getValue());
        List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
        rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
        List<ResultData> results = resultRetrieverService.getResults(query, rh);
        return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
    }

    @RequestMapping(value = "/fuzzy", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
                                                                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResultData>> searchFuzzy(@RequestBody SimpleQuery simpleQuery) throws Exception {
        org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.fuzzy, simpleQuery.getField(), simpleQuery.getValue());
        List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
        rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
        List<ResultData> results = resultRetrieverService.getResults(query, rh);
        return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
    }

    @RequestMapping(value = "/prefix", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
                                                                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResultData>> searchPrefix(@RequestBody SimpleQuery simpleQuery) throws Exception {
        org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.prefix, simpleQuery.getField(), simpleQuery.getValue());
        List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
        rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
        List<ResultData> results = resultRetrieverService.getResults(query, rh);
        return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
    }

    @RequestMapping(value = "/range", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
                                                                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResultData>> searchRange(@RequestBody SimpleQuery simpleQuery) throws Exception {
        org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.range, simpleQuery.getField(), simpleQuery.getValue());
        List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
        rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
        List<ResultData> results = resultRetrieverService.getResults(query, rh);
        return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
    }

    @RequestMapping(value = "/phrase", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
                                                                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResultData>> searchPhrase(@RequestBody SimpleQuery simpleQuery) throws Exception {
        org.elasticsearch.index.query.QueryBuilder query= QueryBuilder.buildQuery(SearchType.phrase, simpleQuery.getField(), simpleQuery.getValue());
        List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
        rh.add(new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue()));
        List<ResultData> results = resultRetrieverService.getResults(query, rh);
        return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
    }

    @RequestMapping(value = "/boolean", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
                                                                        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResultData>> searchBoolean(@RequestBody AdvancedQuery advancedQuery) throws Exception {
        org.elasticsearch.index.query.QueryBuilder query1 = QueryBuilder.buildQuery(SearchType.regular, advancedQuery.getField1(), advancedQuery.getValue1());
        org.elasticsearch.index.query.QueryBuilder query2 = QueryBuilder.buildQuery(SearchType.regular, advancedQuery.getField2(), advancedQuery.getValue2());

        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        if(advancedQuery.getOperation().equalsIgnoreCase("AND")){
            builder.must(query1);
            builder.must(query2);
        }else if(advancedQuery.getOperation().equalsIgnoreCase("OR")){
            builder.should(query1);
            builder.should(query2);
        }else if(advancedQuery.getOperation().equalsIgnoreCase("NOT")){
            builder.must(query1);
            builder.mustNot(query2);
        }

        List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
        rh.add(new RequiredHighlight(advancedQuery.getField1(), advancedQuery.getValue1()));
        rh.add(new RequiredHighlight(advancedQuery.getField2(), advancedQuery.getValue2()));
        List<ResultData> results = resultRetrieverService.getResults(builder, rh);
        return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
    }

    @RequestMapping(value = "/queryParser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
                                                                         produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResultData>> search(@RequestBody SimpleQuery simpleQuery) throws Exception {
        org.elasticsearch.index.query.QueryBuilder query=QueryBuilders.queryStringQuery(simpleQuery.getValue());
        List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();
        List<ResultData> results = resultRetrieverService.getResults(query, rh);
        return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
    }
}

