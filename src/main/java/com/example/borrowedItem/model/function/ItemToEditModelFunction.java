package com.example.borrowedItem.model.function;

import com.example.borrowedItem.entity.BorrowedItem;
import com.example.borrowedItem.model.ItemEditModel;

import java.util.function.Function;

public class ItemToEditModelFunction implements Function<BorrowedItem, ItemEditModel> {
    @Override
    public ItemEditModel apply(BorrowedItem borrowedItem) {
        return ItemEditModel.builder()
                .date(borrowedItem.getDate().toString())
                .version(borrowedItem.getVersion())
                .build();
    }
}
