package com.example.product.controller.rest;

import com.example.borrowedItem.dto.GetItemResponse;
import com.example.borrowedItem.dto.GetItemsResponse;
import com.example.borrowedItem.dto.PatchItemRequest;
import com.example.borrowedItem.dto.PutItemRequest;
import com.example.borrowedItem.entity.BorrowedItem;
import com.example.borrowedItem.service.BorrowedItemService;
import com.example.component.DtoFunctionFactory;
import com.example.product.controller.api.ItemController;
import com.example.product.controller.api.ProductController;
import com.example.product.dto.PatchProductRequest;
import com.example.product.entity.Product;
import com.example.product.service.ProductService;
import com.example.user.entity.User;
import com.example.user.entity.UserRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.Optional;
import java.util.UUID;

@Path("")
@RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
public class ItemRestController implements ItemController {

    private BorrowedItemService itemService;
    private ProductService productService;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;


    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public ItemRestController(DtoFunctionFactory factory,  @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo){
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setItemService(BorrowedItemService itemService){
        this.itemService = itemService;
    }
    @EJB
    public void setProductService(ProductService productService){
        this.productService = productService;
    }

    @Override
    public GetItemsResponse getProductItems(UUID id) {
        return itemService.findAllByProduct(id)
                .map(factory.itemsToResponse())
                .orElseThrow(NotFoundException::new);
    }



    @Override
    public GetItemResponse getProductItem(UUID productId, UUID itemId) {
        return itemService.findAllByProduct(productId)
                .map(borrowedItems -> borrowedItems.stream().filter(borrowedItem -> borrowedItem.getId().equals(itemId)).findFirst()
                            .map(bItem -> factory.itemToResponse().apply(bItem))
                            .orElseThrow(NotFoundException::new)
                )
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetItemsResponse getItems() {
        return factory.itemsToResponse().apply(itemService.findAll());
    }


    @Override
    public void putItem(UUID productId, UUID itemId, PutItemRequest request) {
        try {
        productService.find(productId).ifPresentOrElse(
            product ->{
                BorrowedItem item = factory.requestToItem().apply(itemId, request);
                item.setProduct(product);
                itemService.create(item);

                var items = product.getItems();

                items.add(item);
                product.setItems(items);
                productService.update(product);
            },
                () -> {
                    throw new NotFoundException();
                }
        );
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(ItemController.class, "getProductItem")
                    .build(productId, itemId)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }



    @Override
    public void patchItem(UUID productId, UUID itemId, PatchItemRequest request) {
        try {
            productService.find(productId).ifPresentOrElse(
                    product ->{
                        BorrowedItem item = factory.updateItem().apply(itemId, request);

                        var prev = item.getProduct();
                        item.setProduct(product);
                        itemService.update(item);

//                        if (!prev.equals(product)) {
//                            var items = product.getItems();
//                            items.remove(item);
//                            product.setItems(items);
//                            productService.update(product);
//                        }
                    },
                    () -> {
                        throw new NotFoundException();
                    }
            );
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(ItemController.class, "getProductItem")
                            .build(productId, itemId)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void deleteItem(UUID productId, UUID itemId) {
         BorrowedItem item = itemService.findAllByProduct(productId)
                .map(borrowedItems -> borrowedItems.stream().filter(borrowedItem -> borrowedItem.getId().equals(itemId)).findFirst()
                        .orElseThrow(NotFoundException::new)
                )
                .orElseThrow(NotFoundException::new);
         itemService.delete(item.getId());
    }
}
