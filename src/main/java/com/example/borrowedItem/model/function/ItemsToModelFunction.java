package com.example.borrowedItem.model.function;

import com.example.borrowedItem.entity.BorrowedItem;
import com.example.borrowedItem.model.ItemsModel;

import java.util.List;
import java.util.function.Function;

public class ItemsToModelFunction implements Function<List<BorrowedItem>, ItemsModel> {

    @Override
    public ItemsModel apply(List<BorrowedItem> entities) {
        return ItemsModel.builder()
                .items(entities.stream()
                        .map(borrowedItem -> ItemsModel.Item.builder().date(borrowedItem.getDate()).id(borrowedItem.getId()).build())
                        .toList())
                .build();
    }
}
