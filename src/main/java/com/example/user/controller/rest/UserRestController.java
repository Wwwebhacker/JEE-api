package com.example.user.controller.rest;

import com.example.component.DtoFunctionFactory;
import com.example.controller.servlet.exception.NotFoundException;
import com.example.user.controller.api.UserController;
import com.example.user.dto.GetUserResponse;
import com.example.user.dto.GetUsersResponse;
import com.example.user.dto.LoginRequest;
import com.example.user.dto.PutUserRequest;
import com.example.user.entity.UserRoles;
import com.example.user.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
public class UserRestController implements UserController {
    private UserService service;
    private final SecurityContext securityContext;

    private final DtoFunctionFactory factory;


    @Inject
    public UserRestController( @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext, DtoFunctionFactory factory){
        this.securityContext = securityContext;
        this.factory = factory;
    }

    @EJB
    public void setService(UserService userService) {
        this.service = userService;
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

    @SneakyThrows
    @Override
    public void putUser(UUID id, PutUserRequest request) {
        try{
            service.create(factory.requestToUser().apply(id, request));

            throw new WebApplicationException(Response.Status.CREATED);
        } catch (EJBException ex) {
            //Any unchecked exception is packed into EJBException. Business exception can be introduced here.
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
        }
    }

}
