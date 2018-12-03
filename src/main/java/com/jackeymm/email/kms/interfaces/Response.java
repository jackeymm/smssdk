package com.jackeymm.email.kms.interfaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@JsonInclude(NON_NULL)
public class Response<T> {

    private Integer code;
    private String message;
    private T data;
    private Long vetsion;

    public Response(HttpStatus ok) {
    }

    static <T> Response<T> ok(T body){
        return new Response<>(OK);
    }

    public static <T> Response<T> faled(HttpStatus status,String message){
        return new Response<>(status, message);
    }

    private Response(HttpStatus status, String message) {
        this.code = status.value();
        this.message = message;
    }

}
