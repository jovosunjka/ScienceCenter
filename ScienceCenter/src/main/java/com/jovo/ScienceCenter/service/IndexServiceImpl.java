package com.jovo.ScienceCenter.service;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

import com.jovo.ScienceCenter.handlers.DocumentHandler;
import com.jovo.ScienceCenter.handlers.PDFHandler;
import com.jovo.ScienceCenter.model.elasticsearch.IndexUnit;
import com.jovo.ScienceCenter.repository.elasticsearch.ScientificPaperElasticsearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class IndexServiceImpl implements IndexService {

	@Value("${scientific-papers}")
	private Resource scientificPapersDirectory;

	@Autowired
	private ScientificPaperElasticsearchRepository scientificPaperElasticsearchRepository;

	private DocumentHandler handler;


	@PostConstruct
	private void setupDocumentHandlerAndPutStoredFilesInIndex() {
		handler = getHandler("******.pdf");
		try {
			index(scientificPapersDirectory.getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public boolean delete(String filename){
		if(scientificPaperElasticsearchRepository.existsById(filename)){
			scientificPaperElasticsearchRepository.deleteById(filename);
			return true;
		} else
			return false;
		
	}

	@Override
	public boolean update(IndexUnit unit){
		unit = scientificPaperElasticsearchRepository.save(unit);
		if(unit!=null)
			return true;
		else
			return false;
	}

	@Override
	public boolean add(IndexUnit unit){
		if (scientificPaperElasticsearchRepository.existsById(unit.getFilename())) {
			System.out.println(unit.getFilename() + " already added in index");
			return false;
		}

		unit = scientificPaperElasticsearchRepository.index(unit);
		if(unit!=null)
			return true;
		else
			return false;
	}

	@Override
	public boolean add(File file) {
		if (!file.isFile()) {
			return false;
		}

		IndexUnit indexUnit =  handler.getIndexUnit(file);
		return add(indexUnit);
	}
	
	/**
	 * 
	 * @param file Direktorijum u kojem se nalaze dokumenti koje treba indeksirati
	 */
	@Override
	public int index(File file){
		// DocumentHandler handler = null;
		String fileName = null;
		int retVal = 0;
		try {
			File[] files;
			if(file.isDirectory()){
				files = file.listFiles();
			}else{
				files = new File[1];
				files[0] = file;
			}
			for(File newFile : files){
				if(newFile.isFile()){
					fileName = newFile.getName();
					//handler = getHandler(fileName);
					if(handler == null){
						System.out.println("Nije moguce indeksirati dokument sa nazivom: " + fileName);
						continue;
					}	
					if(add(newFile))
						retVal++;
				} else if (newFile.isDirectory()){
					retVal += index(newFile);
				}
			}
			System.out.println("indexing done");
		} catch (Exception e) {
			System.out.println("indexing NOT done");
		}
		return retVal;
	}

	private DocumentHandler getHandler(String fileName){
		if(fileName.endsWith(".pdf")){
			return new PDFHandler();
		}
		else{
			return null;
		}
	}

}