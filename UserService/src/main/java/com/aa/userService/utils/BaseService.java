package com.aa.userservice.utils;

import com.aa.userservice.dto.response.ResponseDto;
import com.aa.userservice.enums.Status;

import java.util.Objects;

public class BaseService {

    public static ResponseDto success() {
        return ResponseDto.builder()
                .status(Status.SUCCESS)
                .build();
    }

    public static ResponseDto success(String message) {
        return ResponseDto.builder()
                .status(Status.SUCCESS)
                .message(Objects.nonNull(message) && !message.isEmpty() ? message : null)
                .build();
    }

    public static ResponseDto success(Object data) {
        return ResponseDto.builder()
                .status(Status.SUCCESS)
                .data(data)
                .build();
    }

//    public static ResponseDto paginationSuccess(Long totalElements, Integer totalPages, Object data, Pageable pageable) {
//        PaginationResponseDto paginationResponseDto = PaginationResponseDto.builder()
//                .pageNumber(pageable.getPageNumber())
//                .pageSize(pageable.getPageSize())
//                .offset(pageable.getOffset())
//                .totalElements(totalElements)
//                .totalPages(totalPages)
//                .build();
//        return ResponseDto.builder()
//                .status(Status.SUCCESS)
//                .data(data)
//                .pagination(paginationResponseDto)
//                .build();
//    }

    public static ResponseDto success(String message, Object data) {
        return ResponseDto.builder()
                .status(Status.SUCCESS)
                .message(Objects.nonNull(message) && !message.isEmpty() ? message : null)
                .data(data)
                .build();
    }

    public static ResponseDto warning() {
        return ResponseDto.builder()
                .status(Status.WARNING)
                .build();
    }

    public static ResponseDto warning(String message) {
        return ResponseDto.builder()
                .status(Status.WARNING)
                .message(Objects.nonNull(message) && !message.isEmpty() ? message : null)
                .build();
    }

    public static ResponseDto warning(Object data) {
        return ResponseDto.builder()
                .status(Status.WARNING)
                .data(data)
                .build();
    }

    public static ResponseDto warning(String message, Object data) {
        return ResponseDto.builder()
                .status(Status.WARNING)
                .message(Objects.nonNull(message) && !message.isEmpty() ? message : null)
                .data(data)
                .build();
    }

    public static ResponseDto failure() {
        return ResponseDto.builder()
                .status(Status.FAILURE)
                .build();
    }

    public static ResponseDto failure(String message) {
        return ResponseDto.builder()
                .status(Status.FAILURE)
                .message(Objects.nonNull(message) && !message.isEmpty() ? message : null)
                .build();
    }

    public static ResponseDto failure(Object data) {
        return ResponseDto.builder()
                .status(Status.FAILURE)
                .data(data)
                .build();
    }

    public static ResponseDto failure(String message, Object data) {
        return ResponseDto.builder()
                .status(Status.FAILURE)
                .message(Objects.nonNull(message) && !message.isEmpty() ? message : null)
                .data(data)
                .build();
    }

}
