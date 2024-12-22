package com.aa.userservice.Exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserAlreadyExistsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID =-2178948664026920647L;

    private static final String MESSAGE= "User already exists";

    public UserAlreadyExistsException() {
        super(MESSAGE);
    }

    public UserAlreadyExistsException(final String message) {
        super(MESSAGE + " " + message);
    }
}
