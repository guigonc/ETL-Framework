package com.br.guilherme.etlfw.format.database;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.br.guilherme.etlfw.assignment.AssignmentType;
import com.br.guilherme.etlfw.assignment.ModificationAssignment;
import com.br.guilherme.etlfw.format.database.TableCreationFormatBuilder;
import com.br.guilherme.etlfw.mask.FieldMask;
import com.br.guilherme.etlfw.mask.FieldType;

public class TestTableCreationFormatBuilder extends Assert {
	
	private TableCreationFormatBuilder tableCreationFormat;

	@Before
	public void setUp() { 
		tableCreationFormat = new TableCreationFormatBuilder();
	}
	
	@After
	public void tearDown() {
		tableCreationFormat = null;
	}
	
	@Test
	public void shouldInstance() {
		assertTrue(tableCreationFormat instanceof TableCreationFormatBuilder);
		assertEquals("CREATE TABLE IF NOT EXISTS", tableCreationFormat.toString());
	}
	
	@Test
	public void shouldAddTableNameOnStatement() {
		tableCreationFormat.addTableName("Table");
		assertEquals("CREATE TABLE IF NOT EXISTS Table (", tableCreationFormat.toString());
	}
	
	@Test
	public void shouldCreateStatement() {
		tableCreationFormat.addTableName("Table");
		
		ModificationAssignment assignment = new ModificationAssignment(AssignmentType.ALTER, false);
		FieldMask mask = new FieldMask("Field", 1, 10, 2, FieldType.A, false, true);
		mask.addAssignment(assignment);
		tableCreationFormat.addField(mask).addField(mask).finish();
		
		assertEquals("CREATE TABLE IF NOT EXISTS Table (FIELD VARCHAR(12,2),FIELD VARCHAR(12,2))ENGINE='MYISAM';", tableCreationFormat.toString());
	}
	
}
