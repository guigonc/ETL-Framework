package com.guilherme.etlfw.mask.registry;

import org.junit.Assert;
import org.junit.Test;

import com.guilherme.etlfw.assignment.AssignmentType;
import com.guilherme.etlfw.assignment.ModificationAssignment;
import com.guilherme.etlfw.exceptions.InvalidRegistrySizeException;
import com.guilherme.etlfw.mask.field.DelimitedFieldMask;
import com.guilherme.etlfw.mask.field.FieldType;
import com.guilherme.etlfw.mask.field.FixedLengthFieldMask;
import com.guilherme.etlfw.mask.field.XMLFieldMask;
import com.guilherme.etlfw.xml.reader.XMLReader;

public class TestRegistryMask extends Assert {
	
	@Test
	public void shouldInstanceFixedLengthRegistryMask() {
		FixedLengthRegistryMask registry = new FixedLengthRegistryMask("Professores", "1.0", "Professor");

		assertTrue(registry instanceof FixedLengthRegistryMask);
		assertEquals("Professores", registry.getTableName());
		assertEquals("1.0", registry.getVersion());
		assertEquals("Professor", registry.getDescription());
		assertTrue(registry.getFields().isEmpty());
	}
	
	@Test
	public void shouldInstanceDelimitedRegistryMask() {
		DelimitedRegistryMask registry = new DelimitedRegistryMask("Professores", "1.0", "Professor");
		
		assertTrue(registry instanceof DelimitedRegistryMask);
		assertEquals("Professores", registry.getTableName());
		assertEquals("1.0", registry.getVersion());
		assertEquals("Professor", registry.getDescription());
		assertTrue(registry.getFields().isEmpty());
	}

	@Test
	public void shouldInstanceXMLRegistryMask() {
		XMLRegistryMask registry = new XMLRegistryMask("Professores", "professor", "1.0", "Professor");

		assertTrue(registry instanceof XMLRegistryMask);
		assertEquals("Professores", registry.getTableName());
		assertEquals("professor", registry.getTagName());
		assertEquals("1.0", registry.getVersion());
		assertEquals("Professor", registry.getDescription());
		assertTrue(registry.getFields().isEmpty());
	}
	
	@Test
	public void shouldAddFixedLengthField() {
		FixedLengthRegistryMask registry = new FixedLengthRegistryMask("Professores", "1.0", "Professor");
		assertTrue(registry.getFields().isEmpty());
		registry.addField(new FixedLengthFieldMask("Field", 1, 10, 2, FieldType.A, false, true));
		assertFalse(registry.getFields().isEmpty());
	}

	@Test
	public void shouldAddDelimitedField() {
		DelimitedRegistryMask registry = new DelimitedRegistryMask("Professores", "1.0", "Professor");
		assertTrue(registry.getFields().isEmpty());
		registry.addField(new DelimitedFieldMask("Field", 1, 10, 2, FieldType.A, false, true));
		assertFalse(registry.getFields().isEmpty());
	}
	
	@Test
	public void shouldAddXMLField() {
		XMLRegistryMask registry = new XMLRegistryMask("Professores", "professor", "1.0", "Professor");
		
		assertTrue(registry.getFields().isEmpty());
		registry.addField(new XMLFieldMask("Field", "field", 10, 2, FieldType.A, false, true));
		assertFalse(registry.getFields().isEmpty());
	}
	
	@Test
	public void shouldGetFixedLengthRegistryWithValues() {
		FixedLengthRegistryMask registry = new FixedLengthRegistryMask("Professores", "1.0", "Professor");
		registry.addField(new FixedLengthFieldMask("Field", 1, 10, 2, FieldType.A, false, true));
		try {
			assertEquals("Field     ", registry.getRegistryWithValues("Field     ").getFields().get(0).getValue());
		} catch (InvalidRegistrySizeException e) {};
	}

	@Test
	public void shouldGedDelimitedRegistryWithValues() {
		DelimitedRegistryMask registry = new DelimitedRegistryMask("Professores", "1.0", "Professor");
		
		registry.addField(new DelimitedFieldMask("Nome" ,0, 25, 2, FieldType.A, false, true));
		registry.addField(new DelimitedFieldMask("CPF", 1, 14, 2, FieldType.A, false, true));
		try {
			assertEquals("Luiz Gomes", registry.getRegistryWithValues("Luiz Gomes;211.111.111-12;",';').getFields().get(0).getValue());
			assertEquals("211.111.111-12", registry.getRegistryWithValues("Luiz Gomes;211.111.111-12;",';').getFields().get(1).getValue());
		} catch (InvalidRegistrySizeException e) {e.printStackTrace();}
	}
	
	@Test
	public void shouldGetXMLRegistryWithValues() {
		XMLRegistryMask registry = new XMLRegistryMask("Professores", "professor", "1.0", "Professor");
		XMLReader reader = new XMLReader("/home/guilherme/workspace/ETL-Framework/entrada.xml");

		registry.addField(new XMLFieldMask("Nome", "Nome", 10, 2, FieldType.A, false, true));
		assertEquals("Luiz Gomes", registry.getRegistryWithValues(reader.getElement()).getFields().get(0).getValue());
	}
	
	@Test
	public void shouldGenerateTheSQLStatements() {
		XMLRegistryMask registry = new XMLRegistryMask("Professores", "professor", "1.0", "Professor");
		XMLFieldMask field = new XMLFieldMask("Field", "field", 10, 2, FieldType.A, false, true);
		
		field.addAssignment(new ModificationAssignment(AssignmentType.ALTER, false));
		registry.addField(field);
		
		assertEquals("CREATE TABLE IF NOT EXISTS Professores (FIELD VARCHAR(12,2), PRIMARY KEY(FIELD))ENGINE='MYISAM';", registry.formatToCreateTable());
		assertEquals("ALTER TABLE Professores MODIFY FIELD VARCHAR(12,2), DROP PRIMARY KEY, ADD PRIMARY KEY(FIELD);", registry.formatToAlter());
		assertEquals("DELETE FROM Professores WHERE FIELD=\"null\";", registry.formatToDelete());
	}
	
	@Test
	public void shouldChangeAssignmentStatement() {
		XMLRegistryMask registry = new XMLRegistryMask("Professores", "professor", "1.0", "Professor");
		XMLFieldMask field = new XMLFieldMask("Field", "field", 10, 2, FieldType.A, false, true);
		ModificationAssignment assignment = new ModificationAssignment(AssignmentType.ALTER, false);
		
		field.addAssignment(assignment);
		registry.addField(field);
		registry.changeAssignmentState(true);

		assertEquals(true, assignment.isSolved());
	}
	
}