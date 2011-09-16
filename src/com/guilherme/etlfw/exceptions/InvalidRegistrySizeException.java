package com.guilherme.etlfw.exceptions;

public class InvalidRegistrySizeException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidRegistrySizeException(IndexOutOfBoundsException indexOutOfBoundsException) {
		super();
	}

	public InvalidRegistrySizeException() {
		super();
	}
}
