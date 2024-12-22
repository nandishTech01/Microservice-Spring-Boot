package com.aa.userservice.Exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper=false)
public class InvalidTokenException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3952215105519401565L;

    private static final String MESSAGE = "Invalid Token Found.";

    public InvalidTokenException(final String errorMessage) {
        super(MESSAGE + " " + errorMessage);
    }

    public InvalidTokenException() {
        super(MESSAGE);
    }
}
