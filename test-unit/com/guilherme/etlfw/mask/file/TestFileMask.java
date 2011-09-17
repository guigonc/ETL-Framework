package com.guilherme.etlfw.mask.file;

import org.junit.Assert;
import org.junit.Test;

import com.guilherme.etlfw.exceptions.InvalidRegistrySizeException;
import com.guilherme.etlfw.exceptions.UnkownRegistryException;
import com.guilherme.etlfw.mask.field.FieldType;
import com.guilherme.etlfw.mask.field.FixedLengthFieldMask;
import com.guilherme.etlfw.mask.registry.FixedLengthRegistryMask;

public class TestFileMask extends Assert {
	
	@Test
	public void shouldInstanceFixedLenghtFileMask() {
		FixedLengthFileMask file = new FixedLengthFileMask("Prof","1.0","Arquivo de Professores");

		assertTrue(file instanceof FixedLengthFileMask);
		assertEquals("PROF", file.getCode());
		assertEquals("1.0", file.getVersion());
		assertEquals("Arquivo de Professores", file.getDescription());
		assertTrue(file.getRegistryMasks().isEmpty());
	}

	@Test
	public void shouldInstanceDelimitedFileMask() {
		DelimitedFileMask file = new DelimitedFileMask("Prof", ';' ,"1.0","Arquivo de Professores");
		
		assertTrue(file instanceof DelimitedFileMask);
		assertEquals("PROF", file.getCode());
		assertEquals("1.0", file.getVersion());
		assertEquals("Arquivo de Professores", file.getDescription());
		assertTrue(file.getRegistryMasks().isEmpty());
	}
	
	@Test
	public void shouldAddRegistryMask() {
		FixedLengthFileMask file = new FixedLengthFileMask("Prof","1.0","Arquivo de Professores");
		FixedLengthRegistryMask registryMask = new FixedLengthRegistryMask("Professores", "1.0", "Professor");

		assertTrue(file.getRegistryMasks().isEmpty());
		file.addRegistryMask(registryMask);
		assertFalse(file.getRegistryMasks().isEmpty());
	}
	
	@Test
	public void shouldGetRegistryMaskWithValues() {
		FixedLengthFileMask file = new FixedLengthFileMask("Prof","1.0","Arquivo de Professores");
		String line = "Luiz Gomes               211.111.111-12";

		FixedLengthFieldMask nameMask = new FixedLengthFieldMask("Nome", 1, 25, 0, FieldType.A, false, false);
		FixedLengthFieldMask documentMask = new FixedLengthFieldMask("CPF", 26, 39, 0, FieldType.A, false, true);
		
		FixedLengthRegistryMask registryMask = new FixedLengthRegistryMask("Professores", "1.0", "Professor");

		registryMask.addField(nameMask);
		registryMask.addField(documentMask);
		file.addRegistryMask(registryMask);
		
		try {
			FixedLengthRegistryMask registry = file.getRegistryWithValues(line);
			assertEquals("Luiz Gomes               ", registry.getFields().get(0).getValue());
			assertEquals("211.111.111-12", registry.getFields().get(1).getValue());
		} 
		catch (UnkownRegistryException e) {e.printStackTrace();} 
		catch (InvalidRegistrySizeException e) {e.printStackTrace();}
	}
	
	@Test
	public void shouldCaughtExceptionWithInvalidMask() {
		FixedLengthFileMask file = new FixedLengthFileMask("Prof","1.0","Arquivo de Professores");
		try {
			file.getRegistryWithValues(new String("Luiz Gomes111.111.111-11"));
			fail();
		} 
		catch (UnkownRegistryException e) {} 
		catch (InvalidRegistrySizeException e) {}
	}
	
}
