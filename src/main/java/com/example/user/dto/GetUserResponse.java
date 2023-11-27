package com.example.user.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUserResponse {
    private UUID id;

    private String name;
    private String login;
    private String password;
    private LocalDate registrationDate;
    private List<String> roles;
    private List<UUID> items;
}
