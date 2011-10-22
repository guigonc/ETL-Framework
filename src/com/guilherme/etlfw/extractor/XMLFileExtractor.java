package com.guilherme.etlfw.extractor;

import com.guilherme.etlfw.exceptions.UnkownRegistryException;
import com.guilherme.etlfw.mask.file.XMLFileMask;
import com.guilherme.etlfw.mask.registry.XMLRegistryMask;
import com.guilherme.etlfw.xml.reader.XMLReader;

public abstract class XMLFileExtractor extends FileExtractor<XMLFileMask>{
	
	private XMLReader reader;
	
	public XMLFileExtractor(String fileName) {
		fileMask = getFileMaskDesign();
		reader = new XMLReader(fileName);
	}
	
	public final XMLRegistryMask extractOne() throws UnkownRegistryException {
		return fileMask.getRegistryWithValues(reader.getElement());
	}
	
	@Override
	public final boolean next() {
		if(!reader.next())
			return true;
		return false;
	}

}
