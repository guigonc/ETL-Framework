package com.guilherme.etlfw.format.database;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.guilherme.etlfw.mask.field.FieldType;
import com.guilherme.etlfw.mask.field.FixedLengthFieldMask;

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
		
		FixedLengthFieldMask field = new FixedLengthFieldMask("Field", 1, 10, 2, FieldType.A, false, true);

		tableCreationFormat.addField(field).finish();
		
		assertEquals("CREATE TABLE IF NOT EXISTS Table (FIELD VARCHAR(12,2), PRIMARY KEY(FIELD))ENGINE='MYISAM';", tableCreationFormat.toString());
	}
	
}
