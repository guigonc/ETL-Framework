package com.br.guilherme.etlfw.mask;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.br.guilherme.etlfw.mask.RegistryMask;

public class TestRegistryMask extends Assert {
	RegistryMask mask;
	
	@Before
	public void setUp() {
		mask = new RegistryMask("Professores", "1.0", "Professor");
	}
	
	@After
	public void tearDown() {
		mask = null;
	}
	
	@Test
	public void shouldInstance() {
		assertTrue(mask instanceof RegistryMask);
	}
}
