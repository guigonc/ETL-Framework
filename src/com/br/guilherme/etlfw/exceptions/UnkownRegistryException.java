package com.br.guilherme.etlfw.exceptions;

public class UnkownRegistryException extends Exception {
	private static final long serialVersionUID = 1L;

	public UnkownRegistryException(String format) {
		System.out.println(format);
	}
}