package com.example.crudapp.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

// Veri döndürmeyen işlemler (örneğin update, delete) için
@Data
@NoArgsConstructor
public class ServiceResultVoid {

    private List<String> errorMessage;

    @JsonIgnore
    private HttpStatus status;

    @JsonIgnore
    public boolean isSuccess() {
        return errorMessage == null || errorMessage.isEmpty();
    }

    @JsonIgnore
    public boolean isFail() {
        return !isSuccess();
    }

    public static ServiceResultVoid success(HttpStatus status) {
        ServiceResultVoid result = new ServiceResultVoid();
        result.status = status;
        return result;
    }

    public static ServiceResultVoid success() {
        return success(HttpStatus.OK);
    }

    public static ServiceResultVoid fail(List<String> errorMessage, HttpStatus status) {
        ServiceResultVoid result = new ServiceResultVoid();
        result.errorMessage = errorMessage;
        result.status = status;
        return result;
    }

    public static ServiceResultVoid fail(String errorMessage, HttpStatus status) {
        return fail(Collections.singletonList(errorMessage), status);
    }

    public static ServiceResultVoid fail(String errorMessage) {
        return fail(errorMessage, HttpStatus.BAD_REQUEST);
    }
}