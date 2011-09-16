package com.guilherme.etlfw.format.database;

import com.guilherme.etlfw.mask.field.FieldMask;

public class DeletionFormatBuilder extends DataBaseFormat {

	public DeletionFormatBuilder() {
		super(new StringBuilder("DELETE FROM"));
	}

	public DeletionFormatBuilder addTableName(String tableName) {
		if (!hasHeader) {
			this.mainClause.append(space());
			this.mainClause.append(tableName);
			this.mainClause.append(space());
			this.mainClause.append("WHERE");
			this.mainClause.append(space());
			this.hasHeader = true;
		}
		return this;
	}

	public DeletionFormatBuilder addField(FieldMask field) {
		if (hasField) {
			this.mainClause.append(space()).append("AND").append(space());
		}
		this.mainClause.append(formatCondition(field, "="));
		this.hasField = true;
		return this;
	}

	public DeletionFormatBuilder finish() {
		if (hasField) {
			this.mainClause.append(";");
		} else {
			this.mainClause = new StringBuilder();
		}
		return this;
	}
	
}
