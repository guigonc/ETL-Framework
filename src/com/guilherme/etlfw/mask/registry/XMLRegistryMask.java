package com.guilherme.etlfw.mask.registry;

import com.guilherme.etlfw.mask.field.XMLFieldMask;

public class XMLRegistryMask extends RegistryMask<XMLFieldMask> {

	private String tagName;

	public XMLRegistryMask(String tableName, String tagName, String version,
			String description) {
		super(tableName, version, description);
		this.setTagName(tagName);
	}

	public void setSize() {
		setSize(0);
		for (XMLFieldMask mask : getFields()) {
			setSize(size() + mask.size());
		}
	}

	private void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagName() {
		return tagName;
	}

	/*
	 * public XMLRegistryMask getRegistryWithValues(final NodeList fileElement)
	 * throws InvalidRegistrySizeException { int registrySize = size(); String
	 * line = String.format("%1$-" + registrySize + "s", fileElement); int
	 * lineSize = line.length();
	 * 
	 * if (lineSize == registrySize) { try { for (XMLFieldMask mask : fields)
	 * mask.setValue(line.substring(mask.getInitialPosition() - 1,
	 * mask.getFinalPosition())); } catch (IndexOutOfBoundsException
	 * indexOutOfBoundsException) { throw new
	 * InvalidRegistrySizeException(indexOutOfBoundsException); } } else { throw
	 * new InvalidRegistrySizeException(); } return this; }
	 */

}