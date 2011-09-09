package com.br.guilherme.etlfw.mask.field;



public class XMLFieldMask extends FieldMask{

	private String tagName;

	public XMLFieldMask(String fieldName, String tagName, int size,
			int decimals, FieldType fieldType, boolean hasRegistryType, boolean isPrimaryKey) {
		super(fieldName, size, decimals, fieldType, hasRegistryType, isPrimaryKey);
		this.tagName = tagName;
	}
	
	public String getTagName() { return tagName; }

}