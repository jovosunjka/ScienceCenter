package com.jovo.ScienceCenter.service;


import com.jovo.ScienceCenter.exception.EmptyFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    String save(MultipartFile file, String scientificArea) throws EmptyFileException, SecurityException, IOException;

    void remove(String relativePathToFile) throws IOException;

    byte[] getPdfContent(String relativePathToFile) throws IOException;
}
