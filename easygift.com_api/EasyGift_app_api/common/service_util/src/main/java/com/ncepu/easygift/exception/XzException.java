package com.ncepu.easygift.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class XzException extends RuntimeException{

    private Integer code;

    private String message;

}
