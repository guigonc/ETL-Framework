package com.guilherme.etlfw.mask.field;

public class FixedLengthFieldMask extends FieldMask {

	private int initialPosition;
	private int finalPosition;

	public FixedLengthFieldMask(String fieldName, int initialPosition,
			int finalPosition, int decimals, FieldType fieldType,
			boolean hasRegistryType, boolean isPrimaryKey) {

		super(fieldName, decimals, fieldType, hasRegistryType, isPrimaryKey);
		this.initialPosition = initialPosition;
		this.finalPosition = finalPosition;
	}

	final public int size() {
		return finalPosition - initialPosition + 1;
	}

	final public int getInitialPosition() {
		return initialPosition;
	}

	final public int getFinalPosition() {
		return finalPosition;
	}
}