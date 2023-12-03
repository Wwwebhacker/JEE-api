package com.example.borrowedItem.dto.function;

import com.example.borrowedItem.dto.PatchItemRequest;
import com.example.borrowedItem.entity.BorrowedItem;

import java.util.UUID;
import java.util.function.BiFunction;

public class UpdateItemWithRequestFunction implements BiFunction<UUID, PatchItemRequest, BorrowedItem> {
    @Override
    public BorrowedItem apply(UUID borrowedItemId, PatchItemRequest patchItemRequest) {
        return BorrowedItem.builder()
                .id(borrowedItemId)
                .date(patchItemRequest.getDate())
                .version(patchItemRequest.getVersion())
                .build();
    }
}
