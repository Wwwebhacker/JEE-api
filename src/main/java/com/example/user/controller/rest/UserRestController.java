package com.example.user.controller.rest;

import com.example.component.DtoFunctionFactory;
import com.example.controller.servlet.exception.NotFoundException;
import com.example.user.controller.api.UserController;
import com.example.user.dto.GetUserResponse;
import com.example.user.dto.GetUsersResponse;
import com.example.user.dto.PutUserRequest;
import com.example.user.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.extern.java.Log;

import java.util.UUID;

@Path("")
@Log
public class UserRestController implements UserController {
    private final UserService service;
    private final DtoFunctionFactory factory;


    @Inject
    public UserRestController(UserService userService, DtoFunctionFactory factory){
        this.service = userService;
        this.factory = factory;
    }

    @Override
    public GetUsersResponse getUsers() {
        return factory.usersToResponse().apply(service.findAll());
    }

    @Override
    public GetUserResponse getUser(UUID id) {
        return service.find(id)
                .map(factory.userToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putUser(UUID id, PutUserRequest request) {
        try{
            service.create(factory.requestToUser().apply(id, request));

            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }
}
