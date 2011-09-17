package com.guilherme.etlfw.mask.file;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.guilherme.etlfw.mask.registry.RegistryMask;

public abstract class FileMask<T extends RegistryMask<?>> {

	private String code;
	private String version;
	private String description;
	private Map<String, T> registryMasks;

	public FileMask(String fileCode, String fileVersion, String fileDescription) {
		code = fileCode.toUpperCase(Locale.getDefault());
		version = fileVersion;
		description = fileDescription;
		registryMasks = Collections.emptyMap();
	}

	public final String getCode() {
		return code;
	}

	public String getVersion() {
		return version;
	}

	public final String getDescription() {
		return description;
	}

	public final Map<String, T> getRegistryMasks() {
		return registryMasks;
	}

	public final void addRegistryMask(final T registry) {
		if (this.registryMasks.isEmpty()) {
			this.registryMasks = new HashMap<String, T>();
		}
		this.registryMasks.put(registry.getTableName() + registry.getVersion(), registry);
	}
	
}
