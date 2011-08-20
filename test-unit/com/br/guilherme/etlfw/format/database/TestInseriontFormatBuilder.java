package com.br.guilherme.etlfw.format.database;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.br.guilherme.etlfw.format.database.InsertionFormatBuilder;
import com.br.guilherme.etlfw.mask.FieldMask;
import com.br.guilherme.etlfw.mask.FieldType;

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
		
		FieldMask mask = new FieldMask("Field", 1, 10, 2, FieldType.A, false, true);
		insertionFormat.addField(mask).addField(mask).finish();
		
		assertEquals("INSERT INTO Table (FIELD,FIELD) VALUES(\"null\",\"null\");", insertionFormat.toString());
	}
}
