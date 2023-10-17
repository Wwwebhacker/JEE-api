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
    private final FileService fileService;

    public UserService(UserRepository repository, FileService fileService) {
        this.repository = repository;
        this.fileService = fileService;
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
        this.fileService.deleteFile(id);
    }

    public byte[] getFile(UUID id) throws IOException {
        return this.fileService.getFile(id);
    }

    public void saveToFile(UUID id, InputStream is) throws IOException {
        this.fileService.saveToFile(id, is);
    }



}
