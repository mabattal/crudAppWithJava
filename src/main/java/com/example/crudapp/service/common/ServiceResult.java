package com.example.crudapp.service.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

// Generic sonuç sınıfı (T tipinde veri döndürür)
@Data // Getter, Setter, toString, equals, hashCode hepsini oluşturur
@NoArgsConstructor // Parametresiz constructor
public class ServiceResult<T> {

    private T data;
    private List<String> errorMessage;

    @JsonIgnore
    private HttpStatus status;

    // Hata yoksa başarılıdır
    @JsonIgnore
    public boolean isSuccess() {
        return errorMessage == null || errorMessage.isEmpty();
    }

    // Başarısız ise
    @JsonIgnore
    public boolean isFail() {
        return !isSuccess();
    }

    // Başarılı dönüş
    public static <T> ServiceResult<T> success(T data, HttpStatus status) {
        ServiceResult<T> result = new ServiceResult<>();
        result.data = data;
        result.status = status;
        return result;
    }

    public static <T> ServiceResult<T> success(T data) {
        return success(data, HttpStatus.OK);
    }

    // Hatalı dönüş (liste halinde)
    public static <T> ServiceResult<T> fail(List<String> errorMessage, HttpStatus status) {
        ServiceResult<T> result = new ServiceResult<>();
        result.errorMessage = errorMessage;
        result.status = status;
        return result;
    }

    // Hatalı dönüş (tek hata mesajı)
    public static <T> ServiceResult<T> fail(String errorMessage, HttpStatus status) {
        return fail(Collections.singletonList(errorMessage), status);
    }

    public static <T> ServiceResult<T> fail(String errorMessage) {
        return fail(errorMessage, HttpStatus.BAD_REQUEST);
    }
}