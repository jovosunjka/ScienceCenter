package com.jovo.ScienceCenter.service;

import com.jovo.ScienceCenter.handlers.DocumentHandler;
import com.jovo.ScienceCenter.model.elasticsearch.IndexUnit;

import java.io.File;

public interface IndexService {

    boolean delete(String filename);

    boolean update(IndexUnit unit);

    boolean add(IndexUnit unit);

    boolean add(File file, String magazineName, String authorsAndCoauthors, String scientificArea);

    int index(File file);

}
