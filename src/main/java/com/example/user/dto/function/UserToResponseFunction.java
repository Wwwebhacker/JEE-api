package com.example.user.dto.function;

import com.example.borrowedItem.entity.BorrowedItem;
import com.example.user.dto.GetUserResponse;
import com.example.user.entity.User;

import java.util.function.Function;

public class UserToResponseFunction implements Function<User, GetUserResponse> {
    @Override
    public GetUserResponse apply(User entity) {
        return GetUserResponse.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .password(entity.getPassword())
                .name(entity.getName())
                .registrationDate(entity.getRegistrationDate())
                .roles(entity.getRoles())
                .items(entity.getItems().stream().map(BorrowedItem::getId).toList())
                .build();
    }
}
