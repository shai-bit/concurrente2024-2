package org.practica6.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilTest {

    @TempDir
    Path tempDir; // JUnit creates and cleans up a temporary directory

    @Test
    public void testReadFile() throws IOException {
        // Create a temporary file and write some content
        Path testFile = tempDir.resolve("testfile.txt");
        String content = "Hello, world!";
        Files.writeString(testFile, content);

        // Test readFile
        String readContent = Util.readFile(testFile.toString());
        assertEquals(content, readContent, "The content read should match the content written.");
    }

    @Test
    public void testCreateFolder() {
        // Create a path for a new folder
        String folderPath = tempDir.resolve("newFolder").toString();

        // Test createFolder
        Util.createFolder(folderPath);
        File folder = new File(folderPath);
        assertTrue(folder.exists() && folder.isDirectory(), "The folder should exist and be a directory.");
    }

    @Test
    public void testSaveFile() throws IOException {
        // Define file path and content
        String filePath = tempDir.resolve("output.txt").toString();
        String content = "Test content for writing.";

        // Test saveFile
        Util.saveFile(filePath, content);
        String readContent = Files.readString(Path.of(filePath));
        assertEquals(content, readContent, "The content written should match the content read back.");
    }
}

