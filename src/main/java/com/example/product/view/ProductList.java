package com.example.product.view;

import com.example.component.ModelFunctionFactory;
import com.example.product.model.ProductsModel;
import com.example.product.service.ProductService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named
public class ProductList {

    private ProductService service;
    private final ModelFunctionFactory factory;

    private ProductsModel products;

    @Inject
    public ProductList(ModelFunctionFactory factory){
        this.factory = factory;
    }

    @EJB
    public void setService(ProductService service) { this.service = service; }

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
