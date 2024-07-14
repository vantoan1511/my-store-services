package com.mystore.user;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class UserException extends WebApplicationException {

    public UserException(String message, Response.Status status) {
        super(message, status);
    }

    public UserException(String message, Response response) {
        super(message, response);
    }


}
