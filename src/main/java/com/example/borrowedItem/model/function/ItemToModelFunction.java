package com.example.borrowedItem.model.function;

import com.example.borrowedItem.entity.BorrowedItem;
import com.example.borrowedItem.model.ItemModel;

import java.util.function.Function;

public class ItemToModelFunction implements Function<BorrowedItem, ItemModel> {
    @Override
    public ItemModel apply(BorrowedItem borrowedItem) {
        return ItemModel.builder()
                .id(borrowedItem.getId())
                .date(borrowedItem.getDate())
                .build();
    }
}
