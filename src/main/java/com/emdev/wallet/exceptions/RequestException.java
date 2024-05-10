package com.emdev.wallet.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RequestException extends RuntimeException {
	private String code;
	private HttpStatus httpStatus;

	public RequestException(String message, String code, HttpStatus httpStatus) {
		super(message);
		this.code = code;
		this.httpStatus = httpStatus;
	}

}
