package com.scientificcenter.exceptions;

import lombok.Getter;

@Getter
public class InvalidLoginException extends RuntimeException {

    public InvalidLoginException(){
        super("Invalid login, email or password are not correct.\nPlease try again.");
    }
}
