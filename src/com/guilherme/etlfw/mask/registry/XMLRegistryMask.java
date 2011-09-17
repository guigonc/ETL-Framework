package com.guilherme.etlfw.mask.registry;

import com.guilherme.etlfw.mask.field.XMLFieldMask;
import com.guilherme.etlfw.xml.element.XMLElement;

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

	public XMLRegistryMask getRegistryWithValues(XMLElement element) {
		for (XMLFieldMask mask : getFields())
			mask.setValue(element.getChildValueByTagName(mask.getTagName()));
		return this;
	}

}