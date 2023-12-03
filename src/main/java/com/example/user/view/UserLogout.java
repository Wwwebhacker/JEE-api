package com.example.user.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;

@RequestScoped
@Named
public class UserLogout {

    /**
     * Current HTTP request.
     */
    private final HttpServletRequest request;

    @Inject
    public UserLogout(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Action initiated by clicking logout button.
     *
     * @return navigation case to the same page
     */
    @SneakyThrows
    public String logoutAction() {
        request.logout();//Session invalidate can possibly not work with JASPIC.
//        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return "/index.xhtml?faces-redirect=true";
    }

}
