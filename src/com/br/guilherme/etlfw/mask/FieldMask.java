package com.br.guilherme.etlfw.mask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.br.guilherme.etlfw.assignment.ModificationAssignment;

public class FieldMask{

    private String code;
    private int inicialPosition;
    private int finalPosition;
    private FieldType fieldType;
    private int size;
    private int decimals;
    private boolean hasRegistryType;
    private boolean isPrimaryKey;
    private String value;
    private List<ModificationAssignment> assignments;

    public FieldMask(String code, int initialPosition, int finalPosition, int decimals, FieldType fieldType,
    		boolean hasRegistryType, boolean isPrimaryKey) {
    	this.code = code.toUpperCase();
    	this.inicialPosition = initialPosition;
    	this.finalPosition = finalPosition;
        this.fieldType = fieldType;
        this.size = finalPosition - initialPosition  + 1;
        this.decimals = decimals;
        this.hasRegistryType = hasRegistryType;
        this.isPrimaryKey = isPrimaryKey;
        this.assignments = Collections.emptyList();
    }

    public String getTableName() { return code; }
    
    public int getInitialPosition() { return inicialPosition; }
    
    public int getFinalPosition() { return finalPosition; }
    
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