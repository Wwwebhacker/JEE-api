package com.example.user.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class User implements Serializable {
    private UUID id;

    private String name;
    private String login;
    private LocalDate registrationDate;
    private List<String> roles;

}
