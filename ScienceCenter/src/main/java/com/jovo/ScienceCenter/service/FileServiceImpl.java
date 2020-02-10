package com.jovo.ScienceCenter.service;


import com.jovo.ScienceCenter.exception.EmptyFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;


@Service
public class FileServiceImpl implements FileService {

    @Value("${scientific-papers}")
    private Resource scientificPapersDirectory;


    @Override
    public String save(MultipartFile file, String scientificArea) throws EmptyFileException, SecurityException, IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        if (file.isEmpty()) {
            throw new EmptyFileException("Failed to store empty file " + filename);
        }
        if (filename.contains("..")) {
            // Cannot store file with relative path outside current directory (this is a security check)
            throw new SecurityException("Cannot store file with relative path outside current directory (filename="
                            + filename + ").");
        }

        Path scientificAreaDirectoryPath = Paths.get(scientificPapersDirectory.getFile().getAbsolutePath(), scientificArea);
        if (!Files.exists(scientificAreaDirectoryPath)) {
            Files.createDirectory(scientificAreaDirectoryPath);
        }

        Path filePath = Paths.get(scientificAreaDirectoryPath.toString(), filename);

        InputStream inputStream = file.getInputStream();
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        return filename;
    }

    @Override
    public void remove(String relativePathToFile) throws IOException {
        Path absolutePathToFile = Paths.get(scientificPapersDirectory.getFile().getAbsolutePath(), relativePathToFile);
        if (Files.exists(absolutePathToFile)) {
            Files.delete(absolutePathToFile);
        }
    }

    @Override
    public byte[] getPdfContent(String relativePathToFile) throws IOException {
        Path absolutePathToFile = Paths.get(scientificPapersDirectory.getFile().getAbsolutePath(), relativePathToFile);
        byte[] byteArray=null;

        FileInputStream fis = new FileInputStream(absolutePathToFile.toString());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];

        for (int readNum; (readNum = fis.read(buf)) != -1;) {
            bos.write(buf, 0, readNum); //no doubt here is 0
            //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
            System.out.println("read " + readNum + " bytes,");
        }

        return bos.toByteArray();

        /*try {
            InputStream inputStream = new FileInputStream(absolutePathToFile.toString());


            String inputStreamToString = inputStream.toString();
            byteArray = inputStreamToString.getBytes();

            inputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not found"+e);
        } catch (IOException e) {
            System.out.println("IO Ex"+e);
        }
        return byteArray;*/
    }

    /*
    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            log.error("Failed to read stored files", e);
            return null;
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                log.error(
                        "Could not read file: " + filename);
                return null;

            }
        }
        catch (MalformedURLException e) {
            log.error("Could not read file: " + filename);
            return null;
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            log.error("Could not initialize storage");
        }
    }*/
}
