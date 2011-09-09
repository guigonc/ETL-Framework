package com.br.guilherme.etlfw.mask.field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.br.guilherme.etlfw.assignment.ModificationAssignment;

abstract public class FieldMask {

    private String fieldName;
    private FieldType fieldType;
    private int size;
    private int decimals;
    private boolean hasRegistryType;
    private boolean isPrimaryKey;
    private String value;
    private List<ModificationAssignment> assignments;

	public FieldMask(String fieldName, int size, int decimals,
			FieldType fieldType, boolean hasRegistryType, boolean isPrimaryKey) {
		this.fieldName = fieldName.toUpperCase();
		this.fieldType = fieldType;
		this.size = size;
		this.decimals = decimals;
		this.hasRegistryType = hasRegistryType;
		this.isPrimaryKey = isPrimaryKey;
		this.assignments = Collections.emptyList();
	}

    public String getFieldName() { return fieldName; }
    
    public int getDecimalPlaces() { return decimals; }
    
    public FieldType getFieldType() { return fieldType; }
    
    public int size() { return size; }
    
    public boolean hasRegistryType() { return hasRegistryType; }
    
    public boolean isPrimaryKey() { return isPrimaryKey; }
    
    public String getValue() { return value; }
    
    public void setValue(String value) { this.value = value; }
    
    public List<ModificationAssignment> getAlterationAssignment() { return assignments; }
    
	public void addAssignment(final ModificationAssignment assignment) {
		if (assignments.isEmpty()) {
			assignments = new ArrayList<ModificationAssignment>();
		}
		assignments.add(assignment);
	}

	public void modifyAssignmentState(boolean state) {
		for (ModificationAssignment assginment : getAlterationAssignment()) {
			assginment.setSolved(state);
		}
	}

}
