package com.example.user.service;

import com.example.user.entity.User;
import com.example.user.repository.api.UserRepository;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class UserService {

    private final UserRepository repository;
    private final FileService fileService;
    private final Pbkdf2PasswordHash passwordHash;
    private final SecurityContext securityContext;

    @Inject
    public UserService(UserRepository repository, FileService fileService, Pbkdf2PasswordHash passwordHash, SecurityContext securityContext) {
        this.repository = repository;
        this.fileService = fileService;
        this.passwordHash = passwordHash;
        this.securityContext = securityContext;
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

    public Optional<User> getLoggedUser() {
        var principal = securityContext.getCallerPrincipal();
        if (principal == null){
            return Optional.empty();
        }
        return this.repository.findByLogin(principal.getName());
    }

}
