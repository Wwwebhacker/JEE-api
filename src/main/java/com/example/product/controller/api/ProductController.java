package com.example.product.controller.api;


import com.example.product.dto.GetProductResponse;
import com.example.product.dto.GetProductsResponse;
import com.example.product.dto.PatchProductRequest;
import com.example.product.dto.PutProductRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

@Path("")
public interface ProductController {

    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    GetProductsResponse getProducts();

    @GET
    @Path("/products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetProductResponse getProduct(@PathParam("id") UUID id);

    @PUT
    @Path("/products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void putProduct(@PathParam("id") UUID id, PutProductRequest request);

    @DELETE
    @Path("/products/{id}")
    void deleteProduct(@PathParam("id") UUID id);

    @PATCH
    @Path("/products/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchProduct(@PathParam("id") UUID id, PatchProductRequest request);
}
