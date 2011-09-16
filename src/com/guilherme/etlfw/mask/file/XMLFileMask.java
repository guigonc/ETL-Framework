package com.guilherme.etlfw.mask.file;

import java.util.Locale;
import java.util.Set;

import com.guilherme.etlfw.mask.registry.RegistryMask;
import com.guilherme.etlfw.mask.registry.XMLRegistryMask;

public class XMLFileMask extends FileMask<RegistryMask<?>>{

	public XMLFileMask(String fileCode, String fileVersion, String fileDescription) {
		super(fileCode.toUpperCase(Locale.getDefault()), fileVersion, fileDescription);
	}

	protected XMLRegistryMask getRegistry(final String line, final Set<String> keys) {
	/*	XMLRegistryMask result = null;
		XMLRegistryMask registry;
		String type;
		int size, start, end;

		for (String key : keys) {
			registry = this.registryMask.get(key);
			if (keys.size() == 1) {
				result = registry;
				break;
			}
			size = line.length();
			start = registry.getInitialPosition() - 1;
			end = registry.getFinalPosition();
			if ((start <= end) && (end <= size)) {
				type = line.substring(start, end);
				if (registry.getTableName().equalsIgnoreCase(type)) {
					result = registry;
					break;
				}
			}
		}
		return result;*/
		return null;	
	}

	/*public final XMLRegistryMask getRegistryWithValues(final String line)
			throws UnkownRegistryException, InvalidRegistrySizeException {
		Set<String> keys;
		XMLRegistryMask result;

		keys = registryMask.keySet();

		result = getRegistry(line, keys);
		if (result == null) {
			throw new UnkownRegistryException(String.format(
					"Unknown Registry <%s>", line));
		}
		return result.getRegistryWithValues(line);
	}*/

}
