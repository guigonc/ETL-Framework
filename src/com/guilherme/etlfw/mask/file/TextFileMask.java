package com.guilherme.etlfw.mask.file;

import java.util.Locale;
import java.util.Set;

import com.guilherme.etlfw.exceptions.InvalidRegistrySizeException;
import com.guilherme.etlfw.exceptions.UnkownRegistryException;
import com.guilherme.etlfw.mask.registry.TextRegistryMask;

public class TextFileMask extends FileMask<TextRegistryMask>{

	public TextFileMask(String fileCode, String fileVersion, String fileDescription) {
		super(fileCode.toUpperCase(Locale.getDefault()), fileVersion, fileDescription);
	}

	protected TextRegistryMask getRegistry(final String line, final Set<String> keys) {
		TextRegistryMask result = null;
		TextRegistryMask registry;
		String type;
		int size, start, end;

		for (String key : keys) {
			registry = getRegistryMasks().get(key);
			if (keys.size() == 1) {
				result = registry;
				break;
			}
			size = line.length();
			start = registry.getIdentifierInitialPosition() - 1;
			end = registry.getIdentifierFinalPosition();
			if ((start <= end) && (end <= size)) {
				type = line.substring(start, end);
				if (registry.getTableName().equalsIgnoreCase(type)) {
					result = registry;
					break;
				}
			}
		}
		return result;
	}

	public final TextRegistryMask getRegistryWithValues(final String line)
			throws UnkownRegistryException, InvalidRegistrySizeException {
		Set<String> keys;
		TextRegistryMask result;

		keys = getRegistryMasks().keySet();

		result = getRegistry(line, keys);
		if (result == null) {
			throw new UnkownRegistryException(String.format(
					"Unknown Registry <%s>", line));
		}
		return result.getRegistryWithValues(line);
	}

}