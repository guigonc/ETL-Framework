package com.br.guilherme.etlfw.mask.field;



public class TextFieldMask extends FieldMask{

    private int inicialPosition;
    private int finalPosition;

    public TextFieldMask(String fieldName, int initialPosition, int finalPosition, int decimals, FieldType fieldType,
    		boolean hasRegistryType, boolean isPrimaryKey) {
    	super(fieldName, finalPosition - initialPosition  + 1, decimals, fieldType, hasRegistryType, isPrimaryKey);
    	this.inicialPosition = initialPosition;
    	this.finalPosition = finalPosition;
    }

    public int getInitialPosition() { return inicialPosition; }
    
    public int getFinalPosition() { return finalPosition; }
}