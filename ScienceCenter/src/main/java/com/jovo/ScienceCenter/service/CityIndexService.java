package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.model.elasticsearch.CityWithGeoPoint;
import com.jovo.ScienceCenter.model.elasticsearch.IndexUnit;

import java.io.File;
import java.util.List;

public interface CityIndexService {

    boolean delete(String name);

    void deleteAll();

    boolean add(CityWithGeoPoint cityWithGeoPoint);

}
