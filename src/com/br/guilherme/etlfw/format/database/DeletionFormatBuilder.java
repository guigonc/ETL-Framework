package com.br.guilherme.etlfw.format.database;

import com.br.guilherme.etlfw.mask.FieldMask;

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

	public DeletionFormatBuilder addField(FieldMask mask) {
		if (hasField) {
			this.mainClause.append(space()).append("AND").append(space());
		}
		this.mainClause.append(formatCondition(mask, "="));
		this.hasField = true;
		return this;
	}

	public DeletionFormatBuilder finish() {
		this.mainClause.append(";");
		return this;
	}
	
}
