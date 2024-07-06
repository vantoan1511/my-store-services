package com.mystore.service.user.control;

import com.mystore.service.user.ErrorMessage;
import com.mystore.service.user.entity.UserException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UserExceptionMapper implements ExceptionMapper<UserException> {

    @Override
    public Response toResponse(UserException exception) {
        return Response.status(exception.getResponse().getStatus())
                .entity(new ErrorMessage(exception.getMessage()))
                .build();
    }

}
