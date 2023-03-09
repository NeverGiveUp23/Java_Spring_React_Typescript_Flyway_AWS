package com.felixvargas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.OK)
public class CustomerSuccessException extends RuntimeException{

    public CustomerSuccessException(String message){
        super(message);
    }
}
