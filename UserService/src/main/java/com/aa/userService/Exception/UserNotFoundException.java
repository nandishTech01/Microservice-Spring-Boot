package com.aa.userservice.Exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3952215105519401565L;

    private static final String MESSAGE = "User not found";

    public UserNotFoundException() {
        super(MESSAGE);
    }

    public UserNotFoundException(String message) {
        super(MESSAGE + " " + message);
    }

}
