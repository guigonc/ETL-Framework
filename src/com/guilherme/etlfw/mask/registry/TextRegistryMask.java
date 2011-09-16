package com.guilherme.etlfw.mask.registry;

import com.guilherme.etlfw.exceptions.InvalidRegistrySizeException;
import com.guilherme.etlfw.mask.field.TextFieldMask;

public class TextRegistryMask extends RegistryMask<TextFieldMask> {

	public TextRegistryMask(String tableName, String version, String description) {
		super(tableName, version, description);
	}

	public void setSize() {
		setSize(0);
		for (TextFieldMask mask : getFields()) {
			if (size() < mask.getFinalPosition()) {
				setSize(mask.getFinalPosition());
			}
		}
	}

	public TextRegistryMask getRegistryWithValues(final String fileLine)
			throws InvalidRegistrySizeException {
		int registrySize = size();
		String line = String.format("%1$-" + registrySize + "s", fileLine);
		int lineSize = line.length();

		if (lineSize == registrySize) {
			try {
				for (TextFieldMask field : getFields())
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

	public int getInitialPosition() {
		return getIdentifier().getInitialPosition();
	}

	public int getFinalPosition() {
		return getIdentifier().getFinalPosition();
	}
}