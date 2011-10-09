package com.guilherme.etlfw.extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.guilherme.etlfw.exceptions.InvalidRegistrySizeException;
import com.guilherme.etlfw.exceptions.UnkownRegistryException;
import com.guilherme.etlfw.mask.file.FixedLengthFileMask;
import com.guilherme.etlfw.mask.registry.FixedLengthRegistryMask;

public abstract class FixedLengthFileExtractor {

	private FixedLengthFileMask fileMask;
	private BufferedReader reader;
	private FileReader readerTool;
	private String line;
	
	public FixedLengthFileExtractor(String fileName) throws FileNotFoundException {
		this.fileMask = getFileMaskDesign();

		readerTool = new FileReader(new File(fileName));
		reader = new BufferedReader(readerTool);
	}
	
	public FixedLengthRegistryMask extractOne() throws UnkownRegistryException, InvalidRegistrySizeException {
		return fileMask.getRegistryWithValues(line);
	}
	
	public boolean next() throws IOException {
		if((line = reader.readLine()) != null)
			return true;
		return false;
	}
	
	protected abstract FixedLengthFileMask getFileMaskDesign();


}
