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
	
	FieldMask mask; 
	
	@Before
	public void setUp() {
		mask = new FieldMask("Field", 1, 10, 2, FieldType.A, false, true);
	}
	
	@After
	public void tearDown() {
		mask = null;
	}
	
	@Test
	public void shouldInstance() {
		assertTrue(mask instanceof FieldMask);
		assertEquals("FIELD", mask.getTableName());
		assertEquals(1,	mask.getInitialPosition());
		assertEquals(10, mask.getFinalPosition());
		assertEquals(FieldType.A, mask.getFieldType());
		assertEquals(10, mask.size());
		assertEquals(2, mask.getDecimalPlaces());
		assertEquals(false, mask.hasRegistryType());
		assertEquals(true, mask.isPrimaryKey());
	}
	
	@Test
	public void shouldAddAlterationAssignment() {
		ModificationAssignment assignment = new ModificationAssignment(AssignmentType.ALTER, false);
		
		assertTrue(mask.getAlterationAssignment().isEmpty());

		mask.addAssignment(assignment);
		assertTrue(!mask.getAlterationAssignment().isEmpty());
		assertTrue(mask.getAlterationAssignment().get(0) instanceof ModificationAssignment);
	}
	
	@Test
	public void shouldModifyAssignmentState() {
		ModificationAssignment assignment = new ModificationAssignment(AssignmentType.ALTER, false);
		
		mask.addAssignment(assignment);

		assertTrue(!mask.getAlterationAssignment().get(0).isSolved());
	
		mask.modifyAssignmentState(true);
		assertTrue(mask.getAlterationAssignment().get(0).isSolved());
	}
	
}