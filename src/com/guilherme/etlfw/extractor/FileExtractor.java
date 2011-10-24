package com.guilherme.etlfw.extractor;

import com.guilherme.etlfw.mask.file.FileMask;

public abstract class FileExtractor<T extends FileMask<?>> {
	protected T fileMask;
	
	public abstract boolean next();
	protected abstract T getFileMaskDesign();
}
