package com.example.user.controller;




import com.example.component.DtoFunctionFactory;
import com.example.controller.servlet.exception.NotFoundException;
import com.example.user.dto.GetUserResponse;
import com.example.user.dto.GetUsersResponse;
import com.example.user.dto.PutUserRequest;
import com.example.user.service.UserService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;
import jakarta.servlet.ServletContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Level;

@RequestScoped
@NoArgsConstructor(force = true)
public class UserController {
    private final UserService service;
    private final DtoFunctionFactory factory;


    @Inject
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

    public void putUser(UUID uuid, PutUserRequest request){
        try{
            service.create(factory.requestToUser().apply(uuid, request));

            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    public byte[] getUserAvatar(UUID id) throws IOException {
        this.service.find(id).orElseThrow(NotFoundException::new);
        try {
            return this.service.getFile(id);
        } catch (Exception t){
            throw new NotFoundException();
        }
    }

    public void putUserAvatar(UUID id, InputStream avatar) throws IOException {
        this.service.find(id).orElseThrow(NotFoundException::new);

        this.service.saveToFile(id, avatar);
    }

    public void deleteUserAvatar(UUID id) throws IOException {
        this.service.find(id).orElseThrow(NotFoundException::new);
        try {
            this.service.deleteFile(id);
        } catch (Exception t){
            throw new NotFoundException();
        }
    }


}
