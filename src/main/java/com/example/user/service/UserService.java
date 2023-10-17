package com.example.user.service;

import com.example.controller.servlet.exception.NotFoundException;
import com.example.user.entity.User;
import com.example.user.repository.api.UserRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {

    private final UserRepository repository;
    private String filePath = "";

    public UserService(UserRepository repository, String filePath) {
        this.repository = repository;
        this.filePath = filePath;
    }
    public Optional<User> find(UUID id) {
        return repository.find(id);
    }

    public List<User> findAll() {
        return repository.findAll();
    }


    public void create(User user) {
        repository.create(user);
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
