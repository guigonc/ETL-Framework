package com.guilherme.etlfw.mask.file;

import java.util.Locale;
import java.util.Set;

import com.guilherme.etlfw.exceptions.UnkownRegistryException;
import com.guilherme.etlfw.mask.registry.RegistryMask;
import com.guilherme.etlfw.mask.registry.XMLRegistryMask;
import com.guilherme.etlfw.xml.element.XMLElement;

public class XMLFileMask extends FileMask<RegistryMask<?>> {

	public XMLFileMask(String fileCode, String fileVersion,
			String fileDescription) {
		
		super(fileCode.toUpperCase(Locale.getDefault()), fileVersion,
				fileDescription);
	}

	final protected XMLRegistryMask getRegistry(XMLElement element,
			final Set<String> keys) {
		XMLRegistryMask result = null;
		XMLRegistryMask registry;

		for (String key : keys) {
			registry = (XMLRegistryMask) getRegistryMasks().get(key);
			if (keys.size() == 1) {
				result = registry;
				break;
			}
			if (registry.getTagName().equalsIgnoreCase(element.getTagName())) {
				result = registry;
				break;
			}
		}
		return result;
	}

	final public XMLRegistryMask getRegistryWithValues(XMLElement element)
			throws UnkownRegistryException {
		Set<String> keys;
		XMLRegistryMask result;

		keys = getRegistryMasks().keySet();

		result = getRegistry(element, keys);
		if (result == null) {
			throw new UnkownRegistryException(String.format(
					"Unknown Registry <%s>", element.getTagName()));
		}
		return result.getRegistryWithValues(element);
	}

}