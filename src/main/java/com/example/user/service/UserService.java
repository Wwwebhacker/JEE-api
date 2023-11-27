package com.example.user.service;

import com.example.crypto.component.Pbkdf2PasswordHash;
import com.example.user.entity.User;
import com.example.user.repository.api.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class UserService {

    private final UserRepository repository;
    private final FileService fileService;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public UserService(UserRepository repository, FileService fileService, Pbkdf2PasswordHash passwordHash) {
        this.repository = repository;
        this.fileService = fileService;
        this.passwordHash = passwordHash;
    }
    public Optional<User> find(UUID id) {
        return repository.find(id);
    }

    public List<User> findAll() {
        return repository.findAll();
    }


    public void create(User user) {
        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
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
