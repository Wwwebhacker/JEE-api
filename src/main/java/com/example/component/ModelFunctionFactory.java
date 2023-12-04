package com.example.component;

import com.example.borrowedItem.model.function.ItemToEditModelFunction;
import com.example.borrowedItem.model.function.ItemToModelFunction;
import com.example.borrowedItem.model.function.ItemsToModelFunction;
import com.example.borrowedItem.model.function.UpdateItemWithModelFunction;
import com.example.product.model.function.ProductToModelFunction;
import com.example.product.model.function.ProductsToModelFunction;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ModelFunctionFactory {
    public ProductsToModelFunction productsToModel() {
        return new ProductsToModelFunction();
    }
    public ProductToModelFunction productToModel() {
        return new ProductToModelFunction();
    }

    public ItemsToModelFunction itemsToModel(){
        return new ItemsToModelFunction();
    }

    public ItemToModelFunction itemToModel(){
        return new ItemToModelFunction();
    }

    public ItemToEditModelFunction itemToEditModelFunction(){
        return new ItemToEditModelFunction();
    }

    public UpdateItemWithModelFunction updateItem(){
        return new UpdateItemWithModelFunction();
    }
}
