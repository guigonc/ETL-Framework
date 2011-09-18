package com.guilherme.etlfw.mask.registry;

import com.guilherme.etlfw.exceptions.InvalidRegistrySizeException;
import com.guilherme.etlfw.mask.field.FixedLengthFieldMask;

public class FixedLengthRegistryMask extends RegistryMask<FixedLengthFieldMask> {
	
	public FixedLengthRegistryMask(String tableName, String version,
			String description) {
		
		super(tableName, version, description);
	}
	
	public FixedLengthFieldMask getIdentifier() {
		for(FixedLengthFieldMask field : getFields())
			if(field.isRegistryType())
				return field;
		return null;
	}
	
	final public void setSize() {
		setSize(0);
		for (FixedLengthFieldMask mask : getFields()) {
			if (size() < mask.getFinalPosition()) {
				setSize(mask.getFinalPosition());
			}
		}
	}

	final public FixedLengthRegistryMask getRegistryWithValues(String fileLine)
			throws InvalidRegistrySizeException {
		int registrySize = size();
		String line = String.format("%1$-" + registrySize + "s", fileLine);
		int lineSize = line.length();

		if (lineSize == registrySize) {
			try {
				for (FixedLengthFieldMask field : getFields())
					field.setValue(line.substring(
							field.getInitialPosition() - 1,
							field.getFinalPosition()));
			} catch (IndexOutOfBoundsException indexOutOfBoundsException) {
				throw new InvalidRegistrySizeException(
						indexOutOfBoundsException);
			}
		} else {
			throw new InvalidRegistrySizeException();
		}
		return this;
	}

	final public int getIdentifierInitialPosition() {
		return getIdentifier().getInitialPosition();
	}

	public int getIdentifierFinalPosition() {
		return getIdentifier().getFinalPosition();
	}
	
}