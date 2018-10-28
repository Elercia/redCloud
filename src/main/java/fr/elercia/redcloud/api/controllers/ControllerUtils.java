package fr.elercia.redcloud.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class ControllerUtils {

    @Autowired
    private HttpServletRequest request;

    public HttpServletRequest getRequest() {
        return request;
    }

    public String getAuthToken() {
        return null;
    }
}
