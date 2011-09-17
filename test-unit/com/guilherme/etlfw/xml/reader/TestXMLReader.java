package com.guilherme.etlfw.xml.reader;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestXMLReader  extends Assert {
	XMLReader reader;
	
	@Before
	public void setUp() {
		reader = new XMLReader("/home/guilherme/workspace/ETL-Framework/entrada.xml");
	}
	
	@After
	public void tearDown() {
		reader = null;
	}
	
	@Test
	public void shouldGetRootNodeName() {
		assertEquals("Professores", reader.getRootNodeName());
	}
	
	@Test
	public void shouldCountNodes() {
		assertEquals(3, reader.countNodes());
	}
	
	@Test
	public void shouldGetValues() {
		assertEquals("Luiz Gomes", reader.getValueForKey("Nome"));
		reader.next();
		reader.next();
		assertEquals("Claudio Fária", reader.getValueForKey( "Nome"));
		assertEquals("555.444.555-44", reader.getValueForKey("CPF"));
	}
	
	@Test
	public void shouldVerifyIfHasNext() {
		assertTrue(reader.next());
		assertTrue(reader.next());
		assertFalse(reader.next());
	}
	
	@Test
	public void shouldVerifyIfHasPrevious() {
		assertFalse(reader.previous());
		reader.last();
		assertTrue(reader.previous());
		assertTrue(reader.previous());
		assertFalse(reader.previous());
	}
	
}
