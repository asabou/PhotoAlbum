package com.tpjad.project.photoalbumapi.exceptions.handlers;

import com.tpjad.project.photoalbumapi.exceptions.InternalServerErrorException;
import com.tpjad.project.photoalbumapi.exceptions.model.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class InternalServerErrorExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDTO handleInternalServerError(InternalServerErrorException e) {
        return new ErrorDTO(e.getMessage());
    }
}
