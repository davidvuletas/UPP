package com.scientificcenter.exceptions;

import lombok.Getter;

@Getter
public class AlreadyExistsException extends RuntimeException {

    String email;
    public AlreadyExistsException(String email){
        super("Already exists user with email : ".concat(email));
        this.email = email;
    }
}
