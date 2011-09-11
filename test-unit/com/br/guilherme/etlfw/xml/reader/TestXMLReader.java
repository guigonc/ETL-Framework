package com.br.guilherme.etlfw.xml.reader;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.NodeList;

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
	
//	@Test
//	public void shouldGetRootNodeName() {
//		assertEquals("Professores", reader.getRootNodeName());
//	}
//	
//	@Test
//	public void shouldCountNodes() {
//		assertEquals(3, reader.countNodes("professor"));
//	}
//	
//	@Test
//	public void shouldGetValues() {
//		assertEquals("Luiz Gomes", reader.getValueForKey("professor", "Nome", 0));
//		assertEquals("Claudio Faria", reader.getValueForKey("professor", "Nome", 2));
//		assertEquals("555.444.555-44", reader.getValueForKey("professor", "CPF", 2));
//	}
	
	@Test
	public void shouldReadElements() {
		NodeList list = reader.getElementList();
		for(int s=0; s<list.getLength() ; s++)
            System.out.println(s + " - " + list.item(s).toString());
		assertEquals(3, list.getLength());
	}
	
}
