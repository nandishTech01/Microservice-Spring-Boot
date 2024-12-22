package com.aa.userservice.Exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper=false)
public class MethodNotAllowedException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    private final String message;

    public MethodNotAllowedException(String message) {
        super(message);
        this.message = message;
    }

    public MethodNotAllowedException(Exception e) {
        super(e);
        this.message = "Method not allowed";
    }
}
