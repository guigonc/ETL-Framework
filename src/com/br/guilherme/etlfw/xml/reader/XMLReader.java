package com.br.guilherme.etlfw.xml.reader;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLReader {
	Document document;
	
	public XMLReader(String fileName)  {
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			document = documentBuilder.parse(new File(fileName));
		} 
		catch (SAXException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();} 
		catch (ParserConfigurationException e) {e.printStackTrace();}
	}

	public String getRootNodeName() {
		return document.getDocumentElement().getNodeName();
	}

	public int countNodes(String key) {
		return document.getElementsByTagName(key).getLength();
	}

	public String getValueForKey(String parent, String field, int position) {
		NodeList list= document.getElementsByTagName(parent);
		Node item = list.item(position);

		Element element = (Element)item;

        NodeList firstNameList = element.getElementsByTagName(field);
        Element firstNameElement = (Element)firstNameList.item(0);

        NodeList textFNList = firstNameElement.getChildNodes();
        return ((Node)textFNList.item(0)).getNodeValue().trim();
	}

	public NodeList getElementList() {
		return document.getChildNodes().item(0).getChildNodes();
	}

}
