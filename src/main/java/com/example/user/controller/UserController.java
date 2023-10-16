package com.example.user.controller;




import com.example.component.DtoFunctionFactory;
import com.example.controller.servlet.exception.NotFoundException;
import com.example.user.dto.GetUserResponse;
import com.example.user.dto.GetUsersResponse;
import com.example.user.service.UserService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class UserController {
    private final UserService service;
    private final DtoFunctionFactory factory;


    public UserController(UserService userService, DtoFunctionFactory factory){
        this.service = userService;
        this.factory = factory;
    }

    public GetUsersResponse getUsers() {
        return factory.usersToResponse().apply(service.findAll());
    }

    public GetUserResponse getUser(UUID uuid) {
        return service.find(uuid)
                .map(factory.userToResponse())
                .orElseThrow(NotFoundException::new);
    }

    public byte[] getUserAvatar(UUID id) throws IOException {
        this.service.find(id).orElseThrow(NotFoundException::new);
        return getFile(id);
    }

    public void putUserAvatar(UUID id, InputStream avatar) throws IOException {
        this.service.find(id).orElseThrow(NotFoundException::new);

        saveToFile(id, avatar);
    }

    public void deleteUserAvatar(UUID id) throws IOException {
        this.service.find(id).orElseThrow(NotFoundException::new);

        deleteFile(id);
    }

    public void deleteFile(UUID id) throws IOException {
        Path path = Paths.get("avatars", id.toString()+ ".png");
        Files.delete(path);
    }

    public byte[] getFile(UUID id) throws IOException {
        Path dirPath = Paths.get("avatars", id.toString()+ ".png");
        return Files.readAllBytes(dirPath);
    }

    public void saveToFile(UUID id, InputStream is) throws IOException {

        Path dirPath = Paths.get("avatars");

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
