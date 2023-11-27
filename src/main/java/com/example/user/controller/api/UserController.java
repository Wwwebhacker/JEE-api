package com.example.user.controller.api;

import com.example.user.dto.GetUserResponse;
import com.example.user.dto.GetUsersResponse;
import com.example.user.dto.PutUserRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

@Path("")
public interface UserController {
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    GetUsersResponse getUsers();

    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetUserResponse getUser(@PathParam("id") UUID id);

    @PUT
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void putUser(@PathParam("id") UUID id, PutUserRequest request);
}
