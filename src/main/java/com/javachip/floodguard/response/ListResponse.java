package com.javachip.floodguard.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ListResponse<T>{
    private int status;
    private List<T> result;
    public static <T> ListResponse<T> success(List<T> result){
        return new ListResponse<>(200, result);
    }
}
