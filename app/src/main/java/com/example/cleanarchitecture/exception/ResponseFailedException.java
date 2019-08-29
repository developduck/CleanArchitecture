package com.example.cleanarchitecture.exception;

/**
 * Created by Hwang on 2018-04-16.
 *
 * Description :
 */
public class ResponseFailedException extends RuntimeException {
    private int code;
    private String message;

    public ResponseFailedException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    @Override
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
