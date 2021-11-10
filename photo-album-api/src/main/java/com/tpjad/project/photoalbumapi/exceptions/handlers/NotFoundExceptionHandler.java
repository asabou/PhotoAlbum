package com.tpjad.project.photoalbumapi.exceptions.handlers;

import com.tpjad.project.photoalbumapi.exceptions.NotFoundException;
import com.tpjad.project.photoalbumapi.exceptions.model.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class NotFoundExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDTO handleNotFoundException(NotFoundException e) {
        return new ErrorDTO(e.getMessage());
    }
}
