package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.handlers.DocumentHandler;
import com.jovo.ScienceCenter.model.elasticsearch.CityWithGeoPoint;
import com.jovo.ScienceCenter.model.elasticsearch.IndexUnit;

import java.io.File;
import java.util.List;

public interface IndexService {

    boolean delete(String filename);

    boolean update(IndexUnit unit);

    boolean add(IndexUnit unit);

    boolean add(File file, String magazineName, String authorsAndCoauthors, String scientificArea);

    int index(File file);

    String getText(File file);

}
