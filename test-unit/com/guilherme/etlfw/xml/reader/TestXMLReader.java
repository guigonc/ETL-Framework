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
		assertEquals(4, reader.countNodes());
	}
	
	@Test
	public void shouldGetValues() {
		assertEquals("Luiz Gomes", reader.getValueForKey("Nome"));
		reader.next();
		reader.next();
		assertEquals("Gabriel Vasques", reader.getValueForKey( "Nome"));
		assertEquals("024.024.024-24", reader.getValueForKey("CPF"));
	}
	
	@Test
	public void shouldVerifyIfHasNext() {
		assertTrue(reader.next());
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
		assertTrue(reader.previous());
		assertFalse(reader.previous());
	}
	
}
