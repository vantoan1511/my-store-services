package com.shopbee.productservice.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class ProductServiceException extends WebApplicationException {

    public ProductServiceException(String message, Response.Status status) {
        super(message, status);
    }
}
