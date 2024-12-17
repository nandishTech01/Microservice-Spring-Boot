package com.aa.UserService.dto.response;

import com.aa.UserService.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {

    private String message;

    private Status status;

    private Object data;


    private ResponseDto(Builder builder) {
        this.message = builder.message;
        this.status = builder.status;
        this.data = builder.data;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String message;
        private Status status;
        private Object data;

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public ResponseDto build() {
            return new ResponseDto(this);
        }
    }

}
