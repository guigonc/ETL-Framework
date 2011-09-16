package com.guilherme.etlfw.mask.field;

public class XMLFieldMask extends FieldMask{

	private String tagName;
	private int size;

	public XMLFieldMask(String fieldName, String tagName, int size,
			int decimals, FieldType fieldType, boolean hasRegistryType, boolean isPrimaryKey) {
		super(fieldName, decimals, fieldType, hasRegistryType, isPrimaryKey);
		this.tagName = tagName;
		this.size = size; 
	}
	
    public int size() { return size; }
	
	public String getTagName() { return tagName; }

}