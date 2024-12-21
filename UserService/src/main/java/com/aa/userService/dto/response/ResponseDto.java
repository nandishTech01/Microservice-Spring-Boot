package com.aa.userservice.dto.response;

import com.aa.userservice.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {

    private String message;

    private Status status;

    private Object data;

}
