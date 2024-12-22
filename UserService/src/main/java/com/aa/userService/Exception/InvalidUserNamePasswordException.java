package com.aa.userservice.Exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper=false)
public class InvalidUserNamePasswordException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7389659106153108528L;

    private static final String MESSAGE= "Invalid Username or Password.";

    public InvalidUserNamePasswordException() {
        super(MESSAGE);
    }

    public InvalidUserNamePasswordException(final String message) {
        super(MESSAGE + " " + message);
    }
}
