package com.gm2.pdv.dto;

import lombok.Getter;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class ResponseDTO<T> {

    @Getter
    private List<String> message;

    @Getter
    private T data;

    public ResponseDTO(List<String> message, T data) {
        this.message = message;
        this.message = data;
    }

    public ResponseDTO(String message, T data) {
        this.message = Arrays.asList(message);
        this.message = data;
    }
}
