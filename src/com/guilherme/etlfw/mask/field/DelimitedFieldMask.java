package com.guilherme.etlfw.mask.field;

import com.guilherme.etlfw.assignment.ModificationAssignment;

public class DelimitedFieldMask extends FieldMask {

	private int size;
	private int position;

	public DelimitedFieldMask(String fieldName, int position, int size,
			int decimals, FieldType fieldType, boolean isRegistryType,
			boolean isPrimaryKey) {
		
		super(fieldName, decimals, fieldType, isRegistryType, isPrimaryKey);
		this.size = size;
		this.position = position;
	}

	final public int getPosition() {
		return position;
	}

	final public int size() {
		return size;
	}
	
	public FieldMask getClone() {
		DelimitedFieldMask clone = new DelimitedFieldMask(getFieldName(), position, size, getDecimalPlaces(), getFieldType(), isRegistryType(), isPrimaryKey());
		for(ModificationAssignment modification : getAlterationAssignment())
			clone.addAssignment(modification.getClone());
		return clone;
	}
	
}