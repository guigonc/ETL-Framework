package com.guilherme.etlfw.dbformat;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.guilherme.etlfw.dbformat.InsertionFormatBuilder;
import com.guilherme.etlfw.mask.field.FieldType;
import com.guilherme.etlfw.mask.field.FixedLengthFieldMask;

public class TestInseriontFormatBuilder extends Assert { 
	
	private InsertionFormatBuilder insertionFormat;

	@Before
	public void setUp() { 
		insertionFormat = new InsertionFormatBuilder();
	}
	
	@After
	public void tearDown() {
		insertionFormat = null;
	}
	
	@Test
	public void shouldInstance() {
		assertTrue(insertionFormat instanceof InsertionFormatBuilder);
		assertEquals("INSERT INTO VALUES", insertionFormat.toString());
	}
	
	@Test
	public void shouldAddTableNameOnStatement() {
		insertionFormat.addTableName("Table");
		assertEquals("INSERT INTO Table ( VALUES(", insertionFormat.toString());
	}
	
	@Test
	public void shouldCreateStatement() {
		insertionFormat.addTableName("Table");
		
		FixedLengthFieldMask field = new FixedLengthFieldMask("Field", 1, 10, 2, FieldType.A, false, true);

		insertionFormat.addField(field).addField(field).finish();
		
		assertEquals("INSERT INTO Table (FIELD,FIELD) VALUES(\"null\",\"null\");", insertionFormat.toString());
	}
}
