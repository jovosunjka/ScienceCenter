package com.jovo.ScienceCenter.repository.elasticsearch;

import com.jovo.ScienceCenter.model.elasticsearch.CityWithGeoPoint;
import com.jovo.ScienceCenter.model.elasticsearch.IndexUnit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;


public interface CityWithGeoPointElasticsearchRepository extends ElasticsearchRepository<CityWithGeoPoint, String> {

}
