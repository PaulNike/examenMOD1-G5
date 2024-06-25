package com.codigo.ms_rodriguez_mijahuanga.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class ResponseBase {

    private int code;
    private String message;
    private Optional data;
}
