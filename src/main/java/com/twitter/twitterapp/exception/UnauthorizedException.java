package com.twitter.twitterapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = 4025856187797199395L;

	public UnauthorizedException(String message) {
		super(message);
	}
	
	public UnauthorizedException(String message, Throwable t){
		super(message, t);
	}
}
