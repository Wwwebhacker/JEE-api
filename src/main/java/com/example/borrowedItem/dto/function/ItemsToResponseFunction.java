package com.example.borrowedItem.dto.function;

import com.example.borrowedItem.dto.GetItemsResponse;
import com.example.borrowedItem.entity.BorrowedItem;

import java.util.List;
import java.util.function.Function;

public class ItemsToResponseFunction implements Function<List<BorrowedItem>, GetItemsResponse> {

    @Override
    public GetItemsResponse apply(List<BorrowedItem> entities) {
        return GetItemsResponse.builder()
                .items(entities.stream()
                        .map(borrowedItem -> GetItemsResponse.Item.builder()
                                .id(borrowedItem.getId())
                                .date(borrowedItem.getDate())
                                .build()
                        ).toList())
                .build();
    }
}
