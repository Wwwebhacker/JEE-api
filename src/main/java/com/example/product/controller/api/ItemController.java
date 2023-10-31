package com.example.product.controller.api;

import com.example.borrowedItem.dto.GetItemResponse;
import com.example.borrowedItem.dto.GetItemsResponse;
import com.example.borrowedItem.dto.PatchItemRequest;
import com.example.borrowedItem.dto.PutItemRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

@Path("")
public interface ItemController {

    @GET
    @Path("/products/{id}/items")
    @Produces(MediaType.APPLICATION_JSON)
    GetItemsResponse getProductItems(@PathParam("id")UUID id);

    @GET
    @Path("/products/{id_product}/items/{id_item}")
    @Produces(MediaType.APPLICATION_JSON)
    GetItemResponse getProductItem(@PathParam("id_product") UUID productId, @PathParam("id_item") UUID itemId);

    @PUT
    @Path("/products/{id_product}/items/{id_item}")
    @Produces(MediaType.APPLICATION_JSON)
    void putItem(@PathParam("id_product") UUID productId, @PathParam("id_item") UUID itemId, PutItemRequest request);

    @PATCH
    @Path("/products/{id_product}/items/{id_item}")
    @Produces(MediaType.APPLICATION_JSON)
    void patchItem(@PathParam("id_product") UUID productId, @PathParam("id_item") UUID itemId, PatchItemRequest request);

    @DELETE
    @Path("/products/{id_product}/items/{id_item}")
    void deleteItem(@PathParam("id_product") UUID productId, @PathParam("id_item") UUID itemId);
}
