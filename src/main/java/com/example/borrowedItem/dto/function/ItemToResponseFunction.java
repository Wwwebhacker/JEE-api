package com.example.borrowedItem.dto.function;

import com.example.borrowedItem.dto.GetItemResponse;
import com.example.borrowedItem.entity.BorrowedItem;

import java.util.function.Function;

public class ItemToResponseFunction implements Function<BorrowedItem, GetItemResponse> {
    @Override
    public GetItemResponse apply(BorrowedItem borrowedItem) {
        return GetItemResponse.builder()
                .id(borrowedItem.getId())
                .date(borrowedItem.getDate())
                .build();
    }
}
