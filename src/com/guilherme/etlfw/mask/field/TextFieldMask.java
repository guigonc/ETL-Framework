package com.guilherme.etlfw.mask.field;

public class TextFieldMask extends FieldMask{

    private int initialPosition;
    private int finalPosition;

    public TextFieldMask(String fieldName, int initialPosition, int finalPosition, int decimals, FieldType fieldType,
    		boolean hasRegistryType, boolean isPrimaryKey) {
    	super(fieldName, decimals, fieldType, hasRegistryType, isPrimaryKey);
    	this.initialPosition = initialPosition;
    	this.finalPosition = finalPosition;
    }

    public int size() { return finalPosition - initialPosition  + 1; }
    
    public int getInitialPosition() { return initialPosition; }
    
    public int getFinalPosition() { return finalPosition; }
}