package com.example.user.dto.function;

import com.example.user.dto.GetUserResponse;
import com.example.user.entity.User;

import java.util.function.Function;

public class UserToResponseFunction implements Function<User, GetUserResponse> {
    @Override
    public GetUserResponse apply(User entity) {
        return GetUserResponse.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .name(entity.getName())
                .registrationDate(entity.getRegistrationDate())
                .roles(entity.getRoles())
                .build();
    }
}
