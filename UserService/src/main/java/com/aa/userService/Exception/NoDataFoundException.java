package com.aa.userservice.Exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NoDataFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String message;

    public NoDataFoundException(String message) {
        super(message);
        this.message = message;
    }

    public NoDataFoundException(Exception e) {
        super(e);
        this.message = "The requested data could not be found. Please verify your input and try again.";
    }
}

