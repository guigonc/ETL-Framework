package com.br.guilherme.etlfw.format.database;

import com.br.guilherme.etlfw.mask.field.FieldMask;

public class TableCreationFormatBuilder extends DataBaseFormat {
	
	public TableCreationFormatBuilder() {
		super(new StringBuilder("CREATE TABLE IF NOT EXISTS"));
	}

	public TableCreationFormatBuilder addTableName(String tableName) {
		if (!hasHeader) {
			this.mainClause.append(space());
			this.mainClause.append(tableName);
			this.mainClause.append(space());
			this.mainClause.append(open());
			this.hasHeader = true;
		}
		return this;
	}

	public TableCreationFormatBuilder addField(FieldMask mask) {
		if (hasField) {
			this.mainClause.append(comma());
		} else {
			this.hasField = true;
		}
		this.mainClause.append(formatField(mask));
		return this;
	}

	public TableCreationFormatBuilder finish() {
		this.mainClause.append(close());
		this.mainClause.append("ENGINE='MYISAM';");
		return this;
	}
	
}
