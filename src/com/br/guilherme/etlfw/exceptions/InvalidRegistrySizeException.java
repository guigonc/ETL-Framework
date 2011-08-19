package com.br.guilherme.etlfw.exceptions;

public class InvalidRegistrySizeException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidRegistrySizeException(IndexOutOfBoundsException indexOutOfBoundsException) {
		System.out.println(indexOutOfBoundsException);
	}

	public InvalidRegistrySizeException() {
		System.out.println("Invalid Registry Size!!!");
	}
}
