package com.guilherme.etlfw.xml.element;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLElement {

	Element element;
	
	public XMLElement(Node item) {
		element = (Element) item;
	}

	public String getChildValueByTagName(String child) {
		return element.getElementsByTagName(child).item(0).getChildNodes().item(0).getNodeValue();
	}

}
