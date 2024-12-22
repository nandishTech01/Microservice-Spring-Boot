package com.aa.userservice.Exception;

import com.aa.userservice.dto.response.ResponseDto;
import com.aa.userservice.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.BindException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex) {
        log.info("GlobalExceptionHandler.handleValidationException ::: Exception: ", ex);

        ResponseDto responseDto = ResponseDto.builder()
                .status(Status.FAILURE)
                .message(ex.getMessage())
                .build();

        log.info("GlobalExceptionHandler.handleValidationException ::: ResponseDto: {}", responseDto);
        return new ResponseEntity<>(createErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        log.info("GlobalExceptionHandler.handleUserNotFoundException ::: Exception: ", ex);

        ResponseDto responseDto = ResponseDto.builder()
                .status(Status.FAILURE)
                .message(ex.getMessage())
                .build();

        log.info("GlobalExceptionHandler.handleUserNotFoundException ::: ResponseDto: {}", responseDto);
        return new ResponseEntity<>(createErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        log.info("GlobalExceptionHandler.handleUserAlreadyExistsException ::: Exception: ", ex);

        ResponseDto responseDto = ResponseDto.builder()
                .status(Status.FAILURE)
                .message(ex.getMessage())
                .build();

        log.info("GlobalExceptionHandler.handleUserAlreadyExistsException ::: ResponseDto: {}", responseDto);
        return new ResponseEntity<>(createErrorResponse(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TokenAlreadyInvalidatedException.class)
    public ResponseEntity<Object> handleTokenAlreadyInvalidatedException(TokenAlreadyInvalidatedException ex) {
        log.info("GlobalExceptionHandler.handleTokenAlreadyInvalidatedException ::: Exception: ", ex);

        ResponseDto responseDto = ResponseDto.builder()
                .status(Status.FAILURE)
                .message(ex.getMessage())
                .build();

        log.info("GlobalExceptionHandler.handleTokenAlreadyInvalidatedException ::: ResponseDto: {}", responseDto);
        return new ResponseEntity<>(createErrorResponse(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidUserNamePasswordException.class)
    public ResponseEntity<Object> handleInvalidUsernamePasswordException(InvalidUserNamePasswordException ex) {
        log.info("GlobalExceptionHandler.handleInvalidUsernamePasswordException ::: Exception: ", ex);

        ResponseDto responseDto = ResponseDto.builder()
                .status(Status.FAILURE)
                .message(ex.getMessage())
                .build();

        log.info("GlobalExceptionHandler.handleInvalidUsernamePasswordException ::: ResponseDto: {}", responseDto);

        return new ResponseEntity<>(createErrorResponse(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<Object> handleInternalServerException(InternalServerException ex) {
        log.info("GlobalExceptionHandler.handleInternalServerException ::: Exception: ", ex);

        ResponseDto responseDto = ResponseDto.builder()
                .status(Status.FAILURE)
                .message(ex.getMessage())
                .build();

        log.info("GlobalExceptionHandler.handleInternalServerException ::: ResponseDto: {}", responseDto);

        return new ResponseEntity<>(createErrorResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        log.info("GlobalExceptionHandler.handleConstraintViolationException ::: Exception: ", ex);

        ResponseDto responseDto = ResponseDto.builder()
                .status(Status.FAILURE)
                .message(ex.getMessage())
                .build();

        log.info("GlobalExceptionHandler.handleConstraintViolationException ::: ResponseDto: {}", responseDto);
        return new ResponseEntity<>(createErrorResponse("Constraint violation: " + ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ResponseDto> handleRuntimeException(final RuntimeException exception) {
        log.warn("GlobalExceptionHandler.handleRuntimeException ::: Exception: {}", exception.getMessage(), exception);

        ResponseDto responseDto = ResponseDto.builder()
                .status(Status.FAILURE)
                .message(exception.getMessage())
                .build();

        log.warn("GlobalExceptionHandler.handleRuntimeException ::: ResponseDto: {}", responseDto);
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<ResponseDto> handleException(final Exception exception) {
//        log.warn("GlobalExceptionHandler.handleException ::: Exception: {}", exception.getMessage(), exception);
//
//        ResponseDto responseDto = ResponseDto.builder()
//                .status(Status.FAILURE)
//                .message(exception.getMessage())
//                .build();
//
//        log.warn("GlobalExceptionHandler.handleException ::: ResponseDto: {}", responseDto);
//        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(BindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        return new ResponseEntity<>(createErrorResponse("An unexpected error occurred: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", Status.FAILURE);
        response.put("message", message);
        return response;
    }


}
