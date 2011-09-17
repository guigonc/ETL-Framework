package com.guilherme.etlfw.mask.field;

public class DelimitedFieldMask extends FieldMask {

	private int size;
	private int position;

	public DelimitedFieldMask(String fieldName, int position, int size,
			int decimals, FieldType fieldType, boolean hasRegistryType,
			boolean isPrimaryKey) {
		
		super(fieldName, decimals, fieldType, hasRegistryType, isPrimaryKey);
		this.size = size;
		this.position = position;
	}

	final public int getPosition() {
		return position;
	}

	final public int size() {
		return size;
	}
	
}