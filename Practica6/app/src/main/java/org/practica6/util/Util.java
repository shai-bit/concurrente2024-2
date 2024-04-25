package org.practica6.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Util {
    public static String readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readString(path);
    }

    public static void createFolder(String folderPath) {
        new File(folderPath).mkdirs();
    }

    public static void saveFile(String filePath, String content) throws IOException {
        Files.writeString(Paths.get(filePath), content);
    }
}

