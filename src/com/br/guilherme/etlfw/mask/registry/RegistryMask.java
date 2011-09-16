package com.br.guilherme.etlfw.mask.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.br.guilherme.etlfw.format.database.AlterationFormatBuilder;
import com.br.guilherme.etlfw.format.database.DeletionFormatBuilder;
import com.br.guilherme.etlfw.format.database.InsertionFormatBuilder;
import com.br.guilherme.etlfw.format.database.TableCreationFormatBuilder;
import com.br.guilherme.etlfw.mask.field.FieldMask;

public abstract class RegistryMask<T extends FieldMask> {
	private String tableName;
	private String version;
	private String description;
	private List<T> fields;
	private T identifier;
	private int size;
	
	public RegistryMask(String tableName, String version, String description) {
		this.tableName = tableName;
		this.version = version;
		this.description = description;
		size = 0;
		fields = Collections.emptyList();
	}

	public String getTableName() {
		return tableName;
	}

	public String getVersion() {
		return version;
	}

	public String getDescription() {
		return description;
	}

	public List<T> getFields() {
		return fields;
	}

	public void setSize(int size) {
		this.size = size;
	}
	public int size() {
		return size;
	}

	public T getIdentifier() {
		return identifier;
	}

	public abstract void setSize();

	public void addField(final T field) {
		if (this.fields.isEmpty()) {
			this.fields = new ArrayList<T>();
		}
		this.fields.add(field);

		setSize();
		identifyRegistry(field);
	}

	private void identifyRegistry(final T field) {
		if (field.hasRegistryType() == true) {
			this.identifier = field;
		}
	}

	public String formatToInsert() {
		InsertionFormatBuilder insertionFormat = new InsertionFormatBuilder();

		insertionFormat.addTableName(getTableName());
		for (T field : fields) {
			insertionFormat.addField(field);
		}
		insertionFormat.finish();
		return insertionFormat.toString();
	}

	public String formatToCreateTable() {
		TableCreationFormatBuilder tableCreationFormat = new TableCreationFormatBuilder();

		tableCreationFormat.addTableName(getTableName());

		for (T field : fields) {
			tableCreationFormat.addField(field);
		}
		tableCreationFormat.finish();

		return tableCreationFormat.toString();
	}

	public String formatToDelete() {
		DeletionFormatBuilder deletionformat = new DeletionFormatBuilder();

		deletionformat.addTableName(getTableName());
		for (T field : fields) {
			deletionformat.addField(field);
		}
		deletionformat.finish();
		return deletionformat.toString();
	}

	public String formatToAlter() {
		AlterationFormatBuilder alterationFormat = new AlterationFormatBuilder();

		alterationFormat.addTableName(getTableName());
		for (T field : fields) {
			alterationFormat.addField(field);
		}
		alterationFormat.finish();
		return alterationFormat.toString();
	}

	public void changeAssignmentState(boolean state) {
		for (T field : fields) {
			field.modifyAssignmentState(state);
		}
	}

}
