package com.example.component;

import com.example.product.dto.function.ProductToResponseFunction;
import com.example.product.dto.function.ProductsToResponseFunction;
import com.example.product.dto.function.RequestToProductFunction;
import com.example.product.dto.function.UpdateProductWithRequestFunction;
import com.example.user.dto.function.UserToResponseFunction;
import com.example.user.dto.function.UsersToResponseFunction;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DtoFunctionFactory {

    public ProductsToResponseFunction productsToResponse() {
        return new ProductsToResponseFunction();
    }

    public UsersToResponseFunction usersToResponse() {
        return new UsersToResponseFunction();
    }

    public UserToResponseFunction userToResponse() {
        return new UserToResponseFunction();
    }

    public ProductToResponseFunction productToResponse() {
        return new ProductToResponseFunction();
    }

    public RequestToProductFunction requestToProduct() {
        return new RequestToProductFunction();
    }

    public UpdateProductWithRequestFunction updateProduct(){
        return new UpdateProductWithRequestFunction();
    }
}
