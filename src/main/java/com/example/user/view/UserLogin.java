package com.example.user.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import static jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;


@RequestScoped
@Named
@Log
public class UserLogin {
    /**
     * Current HTTP request.
     */
    private final HttpServletRequest request;
    private final FacesContext facesContext;

    /**
     * Security context.
     */
    private final SecurityContext securityContext;

    /**
     * View model, username.
     */
    @Getter
    @Setter
    private String login;

    /**
     * VIew model, password.
     */
    @Getter
    @Setter
    private String password;

    @Inject
    public UserLogin(HttpServletRequest request, FacesContext facesContext, @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.request = request;
        this.facesContext = facesContext;
        this.securityContext = securityContext;

    }

    @SneakyThrows
    public String loginAction() {
        Credential credential = new UsernamePasswordCredential(login, new Password(password));
        AuthenticationStatus status = securityContext.authenticate(request, extractResponseFromFacesContext(),
                withParams().credential(credential));
        facesContext.responseComplete();
        return "index.xhtml?faces-redirect=true";
    }

    private HttpServletResponse extractResponseFromFacesContext() {
        return (HttpServletResponse) facesContext.getExternalContext().getResponse();
    }
}
