package com.example.borrowedItem.dto.function;

import com.example.borrowedItem.dto.PutItemRequest;
import com.example.borrowedItem.entity.BorrowedItem;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToItemFunction implements BiFunction<UUID, PutItemRequest, BorrowedItem> {
    @Override
    public BorrowedItem apply(UUID uuid, PutItemRequest putItemRequest) {
        return BorrowedItem.builder()
                .id(uuid)
                .date(putItemRequest.getDate())
                .build();
    }
}
