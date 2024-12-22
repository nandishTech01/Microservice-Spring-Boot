package com.aa.userservice.Exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper=false)
public class TokenAlreadyInvalidatedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String message;

    public TokenAlreadyInvalidatedException(String message) {
        super(message);
        this.message = message;
    }

    public TokenAlreadyInvalidatedException(Exception e) {
        super(e);
        this.message = "Token is already invalidated";
    }
}
