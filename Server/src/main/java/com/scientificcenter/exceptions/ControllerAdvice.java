package com.scientificcenter.exceptions;

import com.scientificcenter.controller.UserController;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@org.springframework.web.bind.annotation.ControllerAdvice(assignableTypes = UserController.class)
@RequestMapping(produces = "application/vnd.error+json")
public class ControllerAdvice {


    private ResponseEntity<VndErrors> error(final Exception exception, final HttpStatus httpStatus, final String logRef) {
        final String message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new VndErrors(logRef, message), httpStatus);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<VndErrors> assertionException(final IllegalArgumentException e) {
        return error(e, HttpStatus.NOT_FOUND, e.getLocalizedMessage());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<VndErrors> internalError(final AlreadyExistsException e) {
        return error(e, HttpStatus.CONFLICT, e.getMessage());
    }

}
