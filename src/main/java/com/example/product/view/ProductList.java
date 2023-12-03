package com.example.product.view;

import com.example.component.ModelFunctionFactory;
import com.example.product.model.ProductsModel;
import com.example.product.service.ProductService;
import com.example.user.entity.UserRoles;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import lombok.Getter;
import lombok.Setter;

@RequestScoped
@Named
public class ProductList {

    private ProductService service;
    private final ModelFunctionFactory factory;
    private final SecurityContext securityContext;

    private ProductsModel products;

    private boolean admin = false;

    @Inject
    public ProductList(ModelFunctionFactory factory, SecurityContext securityContext){
        this.factory = factory;
        this.securityContext = securityContext;
        admin = securityContext.isCallerInRole(UserRoles.ADMIN);
    }

    @EJB
    public void setService(ProductService service) { this.service = service; }

    public boolean getAdmin() {
        return admin;
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
