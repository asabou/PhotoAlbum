package com.tpjad.project.photoalbumapi.exceptions.handlers;

import com.tpjad.project.photoalbumapi.exceptions.BadRequestException;
import com.tpjad.project.photoalbumapi.exceptions.model.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BadRequestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleBadRequestException(BadRequestException e) {
        return new ErrorDTO(e.getMessage());
    }
}
