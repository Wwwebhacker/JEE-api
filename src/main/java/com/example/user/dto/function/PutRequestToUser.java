package com.example.user.dto.function;

import com.example.user.dto.PutUserRequest;
import com.example.user.entity.User;
import com.example.user.entity.UserRoles;

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

public class PutRequestToUser implements BiFunction<UUID, PutUserRequest, User> {
    @Override
    public User apply(UUID uuid, PutUserRequest putUserRequest) {
        return User.builder()
                .id(uuid)
                .login(putUserRequest.getLogin())
                .password(putUserRequest.getPassword())
                .roles(List.of(UserRoles.USER))
                .build();
    }
}
