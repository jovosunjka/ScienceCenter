package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.handlers.DocumentHandler;
import com.jovo.ScienceCenter.handlers.PDFHandler;
import com.jovo.ScienceCenter.model.City;
import com.jovo.ScienceCenter.model.ScientificPaper;
import com.jovo.ScienceCenter.model.UserData;
import com.jovo.ScienceCenter.model.elasticsearch.CityWithGeoPoint;
import com.jovo.ScienceCenter.model.elasticsearch.IndexUnit;
import com.jovo.ScienceCenter.repository.elasticsearch.CityWithGeoPointElasticsearchRepository;
import com.jovo.ScienceCenter.repository.elasticsearch.ScientificPaperElasticsearchRepository;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CityIndexServiceImpl implements CityIndexService {

	
	@Autowired
	private CityWithGeoPointElasticsearchRepository cityWithGeoPointElasticsearchRepository;


	@Override
	public boolean delete(String name){
		if(cityWithGeoPointElasticsearchRepository.existsById(name)){
			cityWithGeoPointElasticsearchRepository.deleteById(name);
			return true;
		} else
			return false;
		
	}

	@Override
	public void deleteAll(){
		cityWithGeoPointElasticsearchRepository.deleteAll();
	}

	@Override
	public boolean add(CityWithGeoPoint cityWithGeoPoint){
		if (cityWithGeoPointElasticsearchRepository.existsById(cityWithGeoPoint.getName())) {
			System.out.println(cityWithGeoPoint.getName() + " already added in index for city");
			return false;
		}

		cityWithGeoPoint = cityWithGeoPointElasticsearchRepository.index(cityWithGeoPoint);
		if(cityWithGeoPoint!=null)
			return true;
		else
			return false;
	}

	

}