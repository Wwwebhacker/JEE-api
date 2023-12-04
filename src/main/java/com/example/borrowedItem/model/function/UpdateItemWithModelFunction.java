package com.example.borrowedItem.model.function;

import com.example.borrowedItem.entity.BorrowedItem;
import com.example.borrowedItem.model.ItemEditModel;
import com.example.borrowedItem.model.ItemModel;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.function.BiFunction;

public class UpdateItemWithModelFunction implements BiFunction<BorrowedItem, ItemEditModel, BorrowedItem>, Serializable {
    @Override
    @SneakyThrows
    public BorrowedItem apply(BorrowedItem borrowedItem, ItemEditModel itemModel) {
        return BorrowedItem.builder()
                .id(borrowedItem.getId())
                .user(borrowedItem.getUser())
                .product(borrowedItem.getProduct())
                .date(LocalDate.parse(itemModel.getDate()) )
                .version(itemModel.getVersion())
                .build();
    }
}
