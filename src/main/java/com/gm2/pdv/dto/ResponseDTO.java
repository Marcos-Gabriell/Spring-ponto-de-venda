package com.gm2.pdv.dto;

import lombok.Getter;

import java.util.List;

public class ResponseDTO<T> {

    @Getter
    private List<String> message;

    @Getter
    private T data;
}
