package com.guilherme.etlfw.mask.field;

import org.junit.Assert;
import org.junit.Test;

import com.guilherme.etlfw.assignment.AssignmentType;
import com.guilherme.etlfw.assignment.ModificationAssignment;

public class TestFieldMask extends Assert {
	
	@Test
	public void shouldInstanceATextFieldMask() {
		TextFieldMask field = new TextFieldMask("Field", 1, 10, 2, FieldType.A, false, true);
		
		assertTrue(field instanceof TextFieldMask);
		assertEquals("FIELD", field.getFieldName());
		assertEquals(1,	field.getInitialPosition());
		assertEquals(10, field.getFinalPosition());
		assertEquals(FieldType.A, field.getFieldType());
		assertEquals(10, field.size());
		assertEquals(2, field.getDecimalPlaces());
		assertEquals(false, field.hasRegistryType());
		assertEquals(true, field.isPrimaryKey());
	}
	
	@Test
	public void shouldInstance() {
		XMLFieldMask field = new XMLFieldMask("Field", "field", 10, 2, FieldType.A, false, true);
		
		assertTrue(field instanceof XMLFieldMask);
		assertEquals("FIELD", field.getFieldName());
		assertEquals("field", field.getTagName());
		assertEquals(FieldType.A, field.getFieldType());
		assertEquals(10, field.size());
		assertEquals(2, field.getDecimalPlaces());
		assertEquals(false, field.hasRegistryType());
		assertEquals(true, field.isPrimaryKey());
	}
	
	@Test
	public void shouldAddAlterationAssignment() {
		XMLFieldMask field = new XMLFieldMask("Field", "field", 10, 2, FieldType.A, false, true);
		ModificationAssignment assignment = new ModificationAssignment(AssignmentType.ALTER, false);
		
		assertTrue(field.getAlterationAssignment().isEmpty());

		field.addAssignment(assignment);
		assertFalse(field.getAlterationAssignment().isEmpty());
		assertTrue(field.getAlterationAssignment().get(0) instanceof ModificationAssignment);
	}
	
	@Test
	public void shouldModifyAssignmentState() {
		XMLFieldMask field = new XMLFieldMask("Field", "field", 10, 2, FieldType.A, false, true);
		ModificationAssignment assignment = new ModificationAssignment(AssignmentType.ALTER, false);
		
		field.addAssignment(assignment);

		assertFalse(field.getAlterationAssignment().get(0).isSolved());
	
		field.modifyAssignmentState(true);
		assertTrue(field.getAlterationAssignment().get(0).isSolved());
	}
	
}