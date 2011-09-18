package com.guilherme.etlfw.mask.field;

import com.guilherme.etlfw.assignment.ModificationAssignment;


public class XMLFieldMask extends FieldMask {

	private String tagName;
	private int size;

	public XMLFieldMask(String fieldName, String tagName, int size,
			int decimals, FieldType fieldType, boolean isPrimaryKey) {
		
		super(fieldName, decimals, fieldType, false, isPrimaryKey);
		this.tagName = tagName;
		this.size = size;
	}

	final public int size() {
		return size;
	}
	
	final public String getTagName() {
		return tagName;
	}
	
	public FieldMask getClone() {
		XMLFieldMask clone = new XMLFieldMask(getFieldName(), tagName, size, getDecimalPlaces(), getFieldType(), isPrimaryKey());
		for(ModificationAssignment modification : getAlterationAssignment())
			clone.addAssignment(modification.getClone());
		return clone;
	}
	
}