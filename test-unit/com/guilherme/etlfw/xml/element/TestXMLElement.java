package com.guilherme.etlfw.xml.element;

import org.junit.Assert;
import org.junit.Test;

import com.guilherme.etlfw.xml.reader.XMLReader;

public class TestXMLElement extends Assert{
	
	@Test
	public void shouldReadElement() {
		XMLReader reader = new XMLReader("/home/guilherme/workspace/ETL-Framework/entrada.xml");

		XMLElement value = reader.getElement();

		assertEquals("Luiz Gomes", value.getChildValueByTagName("Nome"));
		assertEquals("211.111.111-12", value.getChildValueByTagName("CPF"));
	}


}
