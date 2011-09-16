package com.guilherme.etlfw.format.database;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.guilherme.etlfw.assignment.AssignmentType;
import com.guilherme.etlfw.assignment.ModificationAssignment;
import com.guilherme.etlfw.mask.field.FieldType;
import com.guilherme.etlfw.mask.field.TextFieldMask;

public class TestDeletionFormatBuilder extends Assert {
	
	private DeletionFormatBuilder deletionFormat;

	@Before
	public void setUp() { 
		deletionFormat = new DeletionFormatBuilder();
	}
	
	@After
	public void tearDown() {
		deletionFormat = null;
	}
	
	@Test
	public void shouldInstance() {
		assertTrue(deletionFormat instanceof DeletionFormatBuilder);
		assertEquals("DELETE FROM", deletionFormat.toString());
	}
	
	@Test
	public void shouldAddTableNameOnStatement() {
		deletionFormat.addTableName("Table");
		assertEquals("DELETE FROM Table WHERE ", deletionFormat.toString());
	}
	
	@Test
	public void shouldCreateStatement() {
		deletionFormat.addTableName("Table");
		
		ModificationAssignment assignment = new ModificationAssignment(AssignmentType.ALTER, false);
		TextFieldMask mask = new TextFieldMask("Field", 1, 10, 2, FieldType.A, false, true);
		mask.addAssignment(assignment);
		deletionFormat.addField(mask).addField(mask).finish();
		
		assertEquals("DELETE FROM Table WHERE FIELD=\"null\" AND FIELD=\"null\";", deletionFormat.toString());
	}
	
}
