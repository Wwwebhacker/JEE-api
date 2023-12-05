package com.example.borrowedItem.view;

import com.example.borrowedItem.entity.BorrowedItem;
import com.example.borrowedItem.model.ItemEditModel;
import com.example.borrowedItem.model.ItemModel;
import com.example.borrowedItem.service.BorrowedItemService;
import com.example.component.ModelFunctionFactory;
import jakarta.ejb.EJBTransactionRolledbackException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.RollbackException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class ItemEdit implements Serializable {
    private final BorrowedItemService itemService;
    private final ModelFunctionFactory factory;
    private final FacesContext facesContext;


    @Getter
    @Setter
    private String id;

    @Getter
    private ItemEditModel item;

    @Inject
    public ItemEdit(BorrowedItemService itemService, ModelFunctionFactory factory, FacesContext facesContext) {
        this.itemService = itemService;
        this.factory = factory;
        this.facesContext = facesContext;
    }

    public void init() throws IOException {
        UUID uuid = UUID.fromString(id);
        Optional<BorrowedItem> borrowedItemOptional = itemService.find(uuid);
        if (borrowedItemOptional.isPresent()){
            this.item = factory.itemToEditModelFunction().apply(borrowedItemOptional.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Item not found");
        }
    }

    public String saveAction() throws IOException {
        try {
            itemService.update(factory.updateItem().apply(itemService.find(UUID.fromString(id)).orElseThrow(), item));
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            return viewId + "?faces-redirect=true&includeViewParams=true";
        } catch (Exception ex) {

            facesContext.addMessage(null, new FacesMessage("Version collision."));
//            System.out.println(ex);
//            if (ex.getCause().getCause() instanceof OptimisticLockException) {
////                init();
//                facesContext.addMessage(null, new FacesMessage("Version collision."));
//            }

            return null ;
        }
    }
}
