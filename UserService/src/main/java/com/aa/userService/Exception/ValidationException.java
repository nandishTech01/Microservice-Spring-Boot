package com.aa.userservice.Exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper=false)
public class ValidationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String errorMessage;

    public ValidationException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public ValidationException(Exception e) {
        super(e);
        this.errorMessage ="Your request contains invalid or missing data. Please review your input and try again.";
    }
}
