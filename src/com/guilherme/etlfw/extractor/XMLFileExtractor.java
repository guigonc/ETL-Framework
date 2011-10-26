package com.guilherme.etlfw.extractor;

import com.guilherme.etlfw.exceptions.UnkownRegistryException;
import com.guilherme.etlfw.mask.file.XMLFileMask;
import com.guilherme.etlfw.mask.registry.XMLRegistryMask;
import com.guilherme.etlfw.xml.reader.XMLReader;

public class XMLFileExtractor extends FileExtractor<XMLFileMask>{
	
	private XMLReader reader;
	
	public XMLFileExtractor(String fileName, XMLFileMask fileMask) {
		super(fileMask);
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
