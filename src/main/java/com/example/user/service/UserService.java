package com.example.user.service;

import com.example.controller.servlet.exception.NotFoundException;
import com.example.user.entity.User;
import com.example.user.repository.api.UserRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
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





}
