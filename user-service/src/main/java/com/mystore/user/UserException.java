package com.mystore.user;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

public class UserException extends ClientErrorException {

    public UserException(String message, Response.Status status) {
        super(message, status);
    }

    public UserException(String message, Response response) {
        super(message, response);
    }


}
