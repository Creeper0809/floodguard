package com.javachip.floodguard.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response <T>{
    private int status;
    private T result;
    public static <T> Response<T> success(T result){
        return new Response<>(200, result);
    }
}
