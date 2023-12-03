package com.example.borrowedItem.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ItemsModel {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Item{
        private UUID id;
        private LocalDate date;
        private Long version;
        private LocalDateTime creationDateTime;
        private LocalDateTime updateDateTime;
    }

    @Singular
    private List<Item> items;
}
