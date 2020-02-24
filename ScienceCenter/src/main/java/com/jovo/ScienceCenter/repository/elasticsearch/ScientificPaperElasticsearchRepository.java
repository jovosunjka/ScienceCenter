package com.jovo.ScienceCenter.repository.elasticsearch;

import java.util.List;

import com.jovo.ScienceCenter.model.elasticsearch.IndexUnit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface ScientificPaperElasticsearchRepository extends ElasticsearchRepository<IndexUnit, String> {

	List<IndexUnit> findByTitle(String title);

	IndexUnit findByFilename(String filename);
}
