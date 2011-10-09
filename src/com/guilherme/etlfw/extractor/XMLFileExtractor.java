package com.guilherme.etlfw.extractor;

import com.guilherme.etlfw.exceptions.UnkownRegistryException;
import com.guilherme.etlfw.mask.file.XMLFileMask;
import com.guilherme.etlfw.mask.registry.XMLRegistryMask;
import com.guilherme.etlfw.xml.reader.XMLReader;

public abstract class XMLFileExtractor {
	
	private XMLFileMask fileMask;
	private XMLReader reader;
	
	public XMLFileExtractor(String fileName) {
		this.fileMask = getFileMaskDesign();
		reader = new XMLReader(fileName);
	}
	
	public XMLRegistryMask extractOne() throws UnkownRegistryException {
		return fileMask.getRegistryWithValues(reader.getElement());
	}
	
	public boolean next() {
		if(!reader.next())
			return true;
		return false;
	}
	
	protected abstract XMLFileMask getFileMaskDesign();
	
}
