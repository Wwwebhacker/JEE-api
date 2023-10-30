package com.example.product.controller.rest;

import com.example.component.DtoFunctionFactory;
import com.example.product.controller.api.ProductController;
import com.example.product.dto.GetProductResponse;
import com.example.product.dto.GetProductsResponse;
import com.example.product.dto.PatchProductRequest;
import com.example.product.dto.PutProductRequest;
import com.example.product.service.ProductService;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.UUID;

@Path("")
public class ProductRestController implements ProductController {


    private final ProductService service;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;


    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public ProductRestController(ProductService service, DtoFunctionFactory factory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @Override
    public GetProductsResponse getProducts() {
        return factory.productsToResponse().apply(service.findAll());
    }

    @Override
    public GetProductResponse getProduct(UUID id) {
        return service.find(id)
                .map(factory.productToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putProduct(UUID id, PutProductRequest request) {
        try {
            service.create(factory.requestToProduct().apply(id, request));

            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(ProductController.class, "getProduct")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }

    }

    @Override
    public void deleteProduct(UUID id) {
        service.find(id).ifPresentOrElse(
            entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void patchProduct(UUID id, PatchProductRequest request) {
        service.find(id).ifPresentOrElse(
                entity ->  service.update(factory.updateProduct().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
