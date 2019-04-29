package com.twitter.twitterapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TwitterApplicationException extends Exception {

    private static final long serialVersionUID = 4025856187797199395L;

    private HttpStatus errorCode;

    public TwitterApplicationException(String message) {
        super(message);
    }

    public TwitterApplicationException(String message, HttpStatus errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(HttpStatus errorCode) {
        this.errorCode = errorCode;
    }
}
