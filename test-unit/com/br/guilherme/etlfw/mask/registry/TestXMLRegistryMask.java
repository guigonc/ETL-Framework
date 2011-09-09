package com.br.guilherme.etlfw.mask.registry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.br.guilherme.etlfw.assignment.AssignmentType;
import com.br.guilherme.etlfw.assignment.ModificationAssignment;
import com.br.guilherme.etlfw.mask.field.FieldType;
import com.br.guilherme.etlfw.mask.field.XMLFieldMask;

public class TestXMLRegistryMask extends Assert {
	XMLRegistryMask registry;
	
	@Before
	public void setUp() {
		registry = new XMLRegistryMask("Professores", "professor", "1.0", "Professor");
	}
	
	@After
	public void tearDown() {
		registry = null;
	}
	
	@Test
	public void shouldInstance() {
		assertTrue(registry instanceof XMLRegistryMask);
		assertEquals("Professores", registry.getTableName());
		assertEquals("professor", registry.getTagName());
		assertEquals("1.0", registry.getVersion());
		assertEquals("Professor", registry.getDescription());
		assertTrue(registry.getFields().isEmpty());
	}
	
	@Test
	public void shouldAddField() {
		assertTrue(registry.getFields().isEmpty());
		registry.addField(new XMLFieldMask("Field", "field", 10, 2, FieldType.A, false, true));
		assertFalse(registry.getFields().isEmpty());
	}
	
	/*@Test
	public void shouldGetRegistryWithValues() {
		registry.addField(new TextFieldMask("Field", 1, 10, 2, FieldType.A, false, true));
		try {
			assertEquals("Field     ", registry.getRegistryWithValues("Field     ").getFields().get(0).getValue());
		} catch (InvalidRegistrySizeException e) {};
	}*/
	
	@Test
	public void shouldGenerateTheSQLStatements() {
		XMLFieldMask field = new XMLFieldMask("Field", "field", 10, 2, FieldType.A, false, true);
		field.addAssignment(new ModificationAssignment(AssignmentType.ALTER, false));
		registry.addField(field);
		
		assertEquals("CREATE TABLE IF NOT EXISTS Professores (FIELD VARCHAR(12,2))ENGINE='MYISAM';", registry.formatToCreateTable());
		assertEquals("ALTER TABLE Professores MODIFY FIELD VARCHAR(12,2);", registry.formatToAlter());
		assertEquals("DELETE FROM Professores WHERE FIELD=\"null\";", registry.formatToDelete());
		assertEquals("CREATE TABLE IF NOT EXISTS Professores (FIELD VARCHAR(12,2))ENGINE='MYISAM';", registry.formatToCreateTable());
	}
	
	@Test
	public void shouldChangeAssignmentStatement() {
		XMLFieldMask field = new XMLFieldMask("Field", "field", 10, 2, FieldType.A, false, true);
		ModificationAssignment assignment = new ModificationAssignment(AssignmentType.ALTER, false);
		
		field.addAssignment(assignment);
		registry.addField(field);
		registry.changeAssignmentState(true);

		assertEquals(true, assignment.isSolved());
	}
	
}
