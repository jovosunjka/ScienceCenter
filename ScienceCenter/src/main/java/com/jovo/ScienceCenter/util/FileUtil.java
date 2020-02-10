package com.jovo.ScienceCenter.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    public static String loadFileContent(String path) throws IOException {
        byte[] allBytes = Files.readAllBytes(Paths.get(path));
        return new String(allBytes, StandardCharsets.UTF_8);
    }
}
