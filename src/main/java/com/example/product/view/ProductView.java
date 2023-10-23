package com.example.product.view;


import com.example.borrowedItem.entity.BorrowedItem;
import com.example.borrowedItem.model.ItemsModel;
import com.example.borrowedItem.service.BorrowedItemService;
import com.example.component.ModelFunctionFactory;
import com.example.product.entity.Product;
import com.example.product.model.ProductModel;
import com.example.product.model.ProductsModel;
import com.example.product.service.ProductService;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@ViewScoped
@Named
public class ProductView implements Serializable {
    private final ProductService service;
    private final BorrowedItemService itemService;

    private final ModelFunctionFactory factory;


    @Getter
    @Setter
    private String id;

    @Getter
    private ProductModel product;

    @Getter
    private ItemsModel items;
    @Inject
    public ProductView(ProductService service, BorrowedItemService itemService, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
        this.itemService = itemService;
    }

    public void init() throws IOException {
        UUID uuid = UUID.fromString(id);
        Optional<Product> product = service.find(uuid);
        if(product.isPresent()){
            this.product = factory.productToModel().apply(product.get());
            itemService.findAllByProduct(uuid).ifPresent(borrowedItems -> this.items = factory.itemsToModel().apply(borrowedItems));
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Character not found");
        }
    }

    public String deleteItemAction(ItemsModel.Item item) {
        itemService.delete(item.getId());
        return "product_list?faces-redirect=true";
    }
}
