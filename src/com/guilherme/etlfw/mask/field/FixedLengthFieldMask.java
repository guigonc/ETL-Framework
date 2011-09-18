package com.guilherme.etlfw.mask.field;

import com.guilherme.etlfw.assignment.ModificationAssignment;

public class FixedLengthFieldMask extends FieldMask {

	private int initialPosition;
	private int finalPosition;
	
	public FixedLengthFieldMask(String fieldName, int initialPosition,
			int finalPosition, int decimals, FieldType fieldType,
			boolean isregistryType, boolean isPrimaryKey) {

		super(fieldName, decimals, fieldType, isregistryType, isPrimaryKey);
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
	
	public FieldMask getClone() {
		FixedLengthFieldMask clone = new FixedLengthFieldMask(getFieldName(), initialPosition, finalPosition, getDecimalPlaces(), getFieldType(), isRegistryType(), isPrimaryKey());
		for(ModificationAssignment modification : getAlterationAssignment())
			clone.addAssignment(modification.getClone());
		return clone;
	}
	
}