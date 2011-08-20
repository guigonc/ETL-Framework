package com.br.guilherme.etlfw.mask;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.br.guilherme.etlfw.assignment.AssignmentType;
import com.br.guilherme.etlfw.assignment.ModificationAssignment;
import com.br.guilherme.etlfw.mask.FieldMask;
import com.br.guilherme.etlfw.mask.FieldType;

public class TestFieldMask extends Assert {
	
	FieldMask field; 
	
	@Before
	public void setUp() {
		field = new FieldMask("Field", 1, 10, 2, FieldType.A, false, true);
	}
	
	@After
	public void tearDown() {
		field = null;
	}
	
	@Test
	public void shouldInstance() {
		assertTrue(field instanceof FieldMask);
		assertEquals("FIELD", field.getTableName());
		assertEquals(1,	field.getInitialPosition());
		assertEquals(10, field.getFinalPosition());
		assertEquals(FieldType.A, field.getFieldType());
		assertEquals(10, field.size());
		assertEquals(2, field.getDecimalPlaces());
		assertEquals(false, field.hasRegistryType());
		assertEquals(true, field.isPrimaryKey());
	}
	
	@Test
	public void shouldAddAlterationAssignment() {
		ModificationAssignment assignment = new ModificationAssignment(AssignmentType.ALTER, false);
		
		assertTrue(field.getAlterationAssignment().isEmpty());

		field.addAssignment(assignment);
		assertFalse(field.getAlterationAssignment().isEmpty());
		assertTrue(field.getAlterationAssignment().get(0) instanceof ModificationAssignment);
	}
	
	@Test
	public void shouldModifyAssignmentState() {
		ModificationAssignment assignment = new ModificationAssignment(AssignmentType.ALTER, false);
		
		field.addAssignment(assignment);

		assertFalse(field.getAlterationAssignment().get(0).isSolved());
	
		field.modifyAssignmentState(true);
		assertTrue(field.getAlterationAssignment().get(0).isSolved());
	}
	
}