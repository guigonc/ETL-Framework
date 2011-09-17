package com.guilherme.etlfw.mask.registry;

import com.guilherme.etlfw.exceptions.InvalidRegistrySizeException;
import com.guilherme.etlfw.mask.field.DelimitedFieldMask;

public class DelimitedRegistryMask extends RegistryMask<DelimitedFieldMask> {

	public DelimitedRegistryMask(String tableName, String version,
			String description) {
		super(tableName, version, description);
	}

	public void setSize() {
		setSize(0);
		for (DelimitedFieldMask mask : getFields()) {
			setSize(size() + mask.size());
		}
	}

	public DelimitedRegistryMask getRegistryWithValues(String fileLine,
			char delimiter) throws InvalidRegistrySizeException {

		String line = fileLine.trim();
		String[] splittedLine = line.split("\\" + delimiter);

		if (splittedLine.length == getFields().size())
			for (DelimitedFieldMask field : getFields())
				field.setValue(splittedLine[field.getPosition()].trim());
		else
			throw new InvalidRegistrySizeException();
		return this;
	}

}