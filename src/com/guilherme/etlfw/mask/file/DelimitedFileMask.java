package com.guilherme.etlfw.mask.file;

import java.util.Locale;
import java.util.Set;

import com.guilherme.etlfw.exceptions.InvalidRegistrySizeException;
import com.guilherme.etlfw.exceptions.UnkownRegistryException;
import com.guilherme.etlfw.mask.registry.DelimitedRegistryMask;

public class DelimitedFileMask extends FileMask<DelimitedRegistryMask> {

	private char delimiter;

	public DelimitedFileMask(String fileCode, char delimiterChar,
			String fileVersion, String fileDescription) {
		super(fileCode.toUpperCase(Locale.getDefault()), fileVersion,
				fileDescription);
		delimiter = delimiterChar;
	}

	final protected DelimitedRegistryMask getRegistry(String line, Set<String> keys) {
		DelimitedRegistryMask result = null;
		DelimitedRegistryMask registry;
		String[] splittedLine = line.split("\\" + delimiter);

		for (String key : keys) {
			registry = getRegistryMasks().get(key);
			if (keys.size() == 1) {
				result = registry;
				break;
			}
			if (registry.getTableName().equalsIgnoreCase(
					splittedLine[registry.getIdentifier().getPosition()])) {
				result = registry;
				break;
			}
		}
		return result;
	}

	final public DelimitedRegistryMask getRegistryWithValues(String line)
			throws UnkownRegistryException, InvalidRegistrySizeException {
		Set<String> keys;
		DelimitedRegistryMask result;

		keys = getRegistryMasks().keySet();

		result = getRegistry(line, keys);
		if (result == null) {
			throw new UnkownRegistryException(String.format(
					"Unknown Registry <%s>", line));
		}
		return result.getRegistryWithValues(line, delimiter);
	}

}