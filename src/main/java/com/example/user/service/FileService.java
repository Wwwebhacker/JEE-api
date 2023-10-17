package com.example.user.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileService {
    private String filePath = "";

    public FileService(String filePath){
        this.filePath = filePath;
    }

    public void deleteFile(UUID id) throws IOException {

        Path path = Paths.get(filePath, id.toString()+ ".png");

        Files.delete(path);
    }

    public byte[] getFile(UUID id) throws IOException {
        Path dirPath = Paths.get(filePath, id.toString()+ ".png");
        return Files.readAllBytes(dirPath);
    }

    public void saveToFile(UUID id, InputStream is) throws IOException {

        Path dirPath = Paths.get(filePath);

        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }


        byte[] buffer = new byte[4096];
        int bytesRead;

        try (FileOutputStream fos = new FileOutputStream(dirPath + File.separator + id.toString() + ".png")) {
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }
}