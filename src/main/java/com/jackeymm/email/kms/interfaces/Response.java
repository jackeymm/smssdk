package com.jackeymm.email.kms.interfaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static org.springframework.http.HttpStatus.OK;

@JsonInclude(NON_NULL)
public class Response<T> {

    private Integer code;
    private String message;
    private T data;
    private Long vetsion;

    private Response() {
    }
    private Response(HttpStatus status) {
        this.code = status.value();
    }

    private Response(HttpStatus status, String message) {
        this.code = status.value();
        this.message = message;
    }

    private Response(HttpStatus status, String message, T data) {
        this.code = status.value();
        this.message = message;
        this.data = data;
    }

    private Response(HttpStatus status, String message, T data, Long version) {
        this.code = status.value();
        this.message = message;
        this.data = data;
        this.vetsion = version;
    }

    static <T> Response<T> ok(HttpStatus status, T body) {
        return new Response<>(status, null, body);
    }

    static <T> Response<T> ok(T body){
        return new Response<>(OK);
    }

    public static <T> Response<T> failed(HttpStatus status,String message,T body){
        return new Response<>(status, message, body);
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public Long getVetsion() {
        return vetsion;
    }

}
