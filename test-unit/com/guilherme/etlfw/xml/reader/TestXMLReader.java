package com.guilherme.etlfw.xml.reader;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.guilherme.etlfw.xml.reader.XMLReader;

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
		assertEquals(3, reader.countNodes("professor"));
	}
	
	@Test
	public void shouldGetValues() {
		assertEquals("Luiz Gomes", reader.getValueForKey("Nome"));
		reader.next();
		reader.next();
		assertEquals("Claudio Fária", reader.getValueForKey( "Nome"));
		assertEquals("555.444.555-44", reader.getValueForKey("CPF"));
	}
	
}
