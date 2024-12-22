package com.aa.userservice.Exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper=false)
public class InternalServerException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    private final String errorMessage;

    public InternalServerException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public InternalServerException(Exception e) {
        super(e);
        this.errorMessage ="We encountered an unexpected error while processing your request. Please try again later.";
    }
}
