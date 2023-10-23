package com.example.borrowedItem.view;

import com.example.borrowedItem.entity.BorrowedItem;
import com.example.borrowedItem.model.ItemModel;
import com.example.borrowedItem.model.ItemsModel;
import com.example.borrowedItem.service.BorrowedItemService;
import com.example.component.ModelFunctionFactory;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class ItemView implements Serializable {
    private final BorrowedItemService itemService;
    private final ModelFunctionFactory factory;

    @Getter
    @Setter
    private String id;

    @Getter
    private ItemModel item;

    @Inject
    public ItemView(BorrowedItemService itemService, ModelFunctionFactory factory) {
        this.itemService = itemService;
        this.factory = factory;
    }

    public void init() throws IOException {
        UUID uuid = UUID.fromString(id);
        Optional<BorrowedItem> borrowedItemOptional = itemService.find(uuid);
        if (borrowedItemOptional.isPresent()){
            this.item = factory.itemToModel().apply(borrowedItemOptional.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Character not found");
        }
    }
}
