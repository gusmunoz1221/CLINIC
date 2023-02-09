package com.API.Exceptions;

import lombok.Data;

@Data
public class ErrorMessage {
    private String message;
    private String detail;
    private String code;
    private String path;

    public ErrorMessage(String message, String detail, String code, String path)
    {
        this.message = message;
        this.detail = detail;
        this.code = code;
        this.path = path;
    }
}
