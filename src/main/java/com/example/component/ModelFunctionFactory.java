package com.example.component;

import com.example.borrowedItem.model.function.ItemsToModelFunction;
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
}
