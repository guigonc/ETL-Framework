package com.guilherme.etlfw.extractor;

import com.guilherme.etlfw.exceptions.UnkownRegistryException;
import com.guilherme.etlfw.mask.file.FileMask;
import com.guilherme.etlfw.mask.file.XMLFileMask;
import com.guilherme.etlfw.mask.registry.XMLRegistryMask;
import com.guilherme.etlfw.xml.reader.XMLReader;

public abstract class Extractor {
	private XMLFileMask fileMask;
	private XMLReader reader;
	
	public Extractor(String fileName) {
		this.fileMask = (XMLFileMask) getFileMaskDesign();
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
	
	public boolean previous() {
		if(!reader.previous())
			return true;
		return false;
	}
	
	public abstract FileMask<?> getFileMaskDesign();
	
}
