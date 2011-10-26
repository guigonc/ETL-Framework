package com.guilherme.etlfw.extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.guilherme.etlfw.exceptions.InvalidRegistrySizeException;
import com.guilherme.etlfw.exceptions.UnkownRegistryException;
import com.guilherme.etlfw.mask.file.DelimitedFileMask;
import com.guilherme.etlfw.mask.registry.DelimitedRegistryMask;

public class DelimitedFileExtractor extends FileExtractor<DelimitedFileMask>{
	
	private BufferedReader reader;
	private FileReader readerTool;
	private String line;
	
	public DelimitedFileExtractor(String fileName, DelimitedFileMask fileMask) throws FileNotFoundException {
		super(fileMask);
		readerTool = new FileReader(new File(fileName));
		reader = new BufferedReader(readerTool);
	}
	
	public DelimitedRegistryMask extractOne() throws UnkownRegistryException, InvalidRegistrySizeException {
		return fileMask.getRegistryWithValues(line);
	}
	
	@Override
	public boolean next() {
		try {
			if((line = reader.readLine()) != null)
				return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
