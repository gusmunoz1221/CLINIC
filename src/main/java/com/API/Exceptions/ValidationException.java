package com.API.Exceptions;

public class ValidationException extends RuntimeException{
    public ValidationException(String message)
    {
        super(message);
    }
}