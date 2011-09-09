package com.br.guilherme.etlfw.xml.reader;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestXMLReader  extends Assert {
	XMLReader parser;
	
	@Before
	public void setUp() {
		parser = new XMLReader("/home/guilherme/workspace/ETL-Framework/entrada.xml");
	}
	
	@After
	public void tearDown() {
		parser = null;
	}
	
	@Test
	public void shouldGetRootNodeName() {
		assertEquals("Professores", parser.getRootNodeName());
	}
	
	@Test
	public void shouldCountNodes() {
		assertEquals(3, parser.countNodes("professor"));
	}
	
	@Test
	public void shouldGetValues() {
		assertEquals("Luiz Gomes", parser.getValueForKey("professor", "Nome", 0));
		assertEquals("Claudio Fária", parser.getValueForKey("professor", "Nome", 2));
		assertEquals("555.444.555-44", parser.getValueForKey("professor", "CPF", 2));
	}
	
}
