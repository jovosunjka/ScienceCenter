package com.jovo.ScienceCenter.service;

import java.util.ArrayList;
import java.util.List;

import com.jovo.ScienceCenter.model.elasticsearch.IndexUnit;
import com.jovo.ScienceCenter.model.elasticsearch.RequiredHighlight;
import com.jovo.ScienceCenter.model.elasticsearch.ResultData;
import com.jovo.ScienceCenter.repository.elasticsearch.ScientificPaperElasticsearchRepository;
import com.jovo.ScienceCenter.service.ResultRetrieverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ResultRetrieverServiceImpl implements ResultRetrieverService {
	
	@Autowired
	private ScientificPaperElasticsearchRepository scientificPaperElasticsearchRepository;


	@Override
	public List<ResultData> getResults(org.elasticsearch.index.query.QueryBuilder query,
									   List<RequiredHighlight> requiredHighlights) {
		if (query == null) {
			return null;
		}
			
		List<ResultData> results = new ArrayList<ResultData>();
       
        for (IndexUnit indexUnit : scientificPaperElasticsearchRepository.search(query)) {
        	results.add(new ResultData(indexUnit.getTitle(), indexUnit.getKeywords(), indexUnit.getFilename(), ""));
		}
        
		
		return results;
	}

}
