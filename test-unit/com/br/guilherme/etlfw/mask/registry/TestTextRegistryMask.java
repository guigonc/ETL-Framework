package com.br.guilherme.etlfw.mask.registry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.br.guilherme.etlfw.assignment.AssignmentType;
import com.br.guilherme.etlfw.assignment.ModificationAssignment;
import com.br.guilherme.etlfw.exceptions.InvalidRegistrySizeException;
import com.br.guilherme.etlfw.mask.field.FieldType;
import com.br.guilherme.etlfw.mask.field.TextFieldMask;
import com.br.guilherme.etlfw.mask.registry.TextRegistryMask;

public class TestTextRegistryMask extends Assert {
	TextRegistryMask registry;
	
	@Before
	public void setUp() {
		registry = new TextRegistryMask("Professores", "1.0", "Professor");
	}
	
	@After
	public void tearDown() {
		registry = null;
	}
	
	@Test
	public void shouldInstance() {
		assertTrue(registry instanceof TextRegistryMask);
		assertEquals("Professores", registry.getTableName());
		assertEquals("1.0", registry.getVersion());
		assertEquals("Professor", registry.getDescription());
		assertTrue(registry.getFields().isEmpty());
	}
	
	@Test
	public void shouldAddField() {
		assertTrue(registry.getFields().isEmpty());
		registry.addField(new TextFieldMask("Field", 1, 10, 2, FieldType.A, false, true));
		assertFalse(registry.getFields().isEmpty());
	}
	
	@Test
	public void shouldGetRegistryWithValues() {
		registry.addField(new TextFieldMask("Field", 1, 10, 2, FieldType.A, false, true));
		try {
			assertEquals("Field     ", registry.getRegistryWithValues("Field     ").getFields().get(0).getValue());
		} catch (InvalidRegistrySizeException e) {};
	}
	
	@Test
	public void shouldGenerateTheSQLStatements() {
		TextFieldMask field = new TextFieldMask("Field", 1, 10, 2, FieldType.A, false, true);
		field.addAssignment(new ModificationAssignment(AssignmentType.ALTER, false));
		registry.addField(field);
		
		assertEquals("CREATE TABLE IF NOT EXISTS Professores (FIELD VARCHAR(12,2))ENGINE='MYISAM';", registry.formatToCreateTable());
		assertEquals("ALTER TABLE Professores MODIFY FIELD VARCHAR(12,2);", registry.formatToAlter());
		assertEquals("DELETE FROM Professores WHERE FIELD=\"null\";", registry.formatToDelete());
		assertEquals("CREATE TABLE IF NOT EXISTS Professores (FIELD VARCHAR(12,2))ENGINE='MYISAM';", registry.formatToCreateTable());
	}
	
	@Test
	public void shouldSetIdentifierPositions() {
		registry.addField(new TextFieldMask("Field", 1, 10, 2, FieldType.A, true, true));

		assertEquals(1, registry.getInitialPosition());
		assertEquals(10, registry.getFinalPosition());
	}
	
	@Test
	public void shouldChangeTheAssignmentStatement() {
		TextFieldMask field = new TextFieldMask("Field", 1, 10, 2, FieldType.A, false, true);
		ModificationAssignment assignment = new ModificationAssignment(AssignmentType.ALTER, false);
		
		field.addAssignment(assignment);
		registry.addField(field);
		registry.changeAssignmentState(true);

		assertEquals(true, assignment.isSolved());
	}
	
}
