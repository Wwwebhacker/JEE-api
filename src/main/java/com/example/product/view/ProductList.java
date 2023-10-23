package com.example.product.view;

import com.example.component.ModelFunctionFactory;
import com.example.product.model.ProductsModel;
import com.example.product.service.ProductService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named
public class ProductList {

    private final ProductService service;
    private final ModelFunctionFactory factory;

    private ProductsModel products;

    @Inject
    public ProductList(ProductService service, ModelFunctionFactory factory){
        this.service = service;
        this.factory = factory;
    }

    public ProductsModel getProducts(){
        if (products == null){
            products = factory.productsToModel().apply(service.findAll());
        }

        return products;
    }

    public String deleteAction(ProductsModel.Product product) {
        service.delete(product.getId());
        return "product_list?faces-redirect=true";
    }
}
