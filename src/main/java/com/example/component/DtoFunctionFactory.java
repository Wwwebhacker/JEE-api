package com.example.component;

import com.example.user.dto.function.UserToResponseFunction;
import com.example.user.dto.function.UsersToResponseFunction;

public class DtoFunctionFactory {
    public UsersToResponseFunction usersToResponse() { return new UsersToResponseFunction(); };
    public UserToResponseFunction userToResponse() { return new UserToResponseFunction(); };
}
