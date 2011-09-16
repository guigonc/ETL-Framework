package com.br.guilherme.etlfw.mask.file;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.br.guilherme.etlfw.mask.registry.RegistryMask;

public abstract class FileMask<T extends RegistryMask<?>> {

	private String code;
	private String version;
	private String description;
	private Map<String, T> registryMask;

	public FileMask(String fileCode, String fileVersion, String fileDescription) {
		code = fileCode.toUpperCase(Locale.getDefault());
		version = fileVersion;
		description = fileDescription;
		registryMask = Collections.emptyMap();
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
		return registryMask;
	}

	public final void addRegistryMask(final T registry) {
		if (this.registryMask.isEmpty()) {
			this.registryMask = new HashMap<String, T>();
		}
		this.registryMask.put(registry.getTableName() + registry.getVersion(), registry);
	}
	
	protected abstract T getRegistry(final String line, final Set<String> keys);

}
