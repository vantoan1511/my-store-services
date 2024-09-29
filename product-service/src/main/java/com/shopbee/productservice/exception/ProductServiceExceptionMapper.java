package com.shopbee.productservice.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ProductServiceExceptionMapper implements ExceptionMapper<ProductServiceException> {

    @Override
    public Response toResponse(ProductServiceException e) {
        return Response.status(e.getResponse().getStatus())
                .entity(e.getMessage())
                .build();
    }

}
