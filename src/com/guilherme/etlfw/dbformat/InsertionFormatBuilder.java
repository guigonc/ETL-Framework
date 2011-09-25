package com.guilherme.etlfw.dbformat;

import com.guilherme.etlfw.mask.field.FieldMask;

public class InsertionFormatBuilder extends DataBaseFormat {

	public InsertionFormatBuilder() {
		super(new StringBuilder("INSERT INTO"), new StringBuilder(" VALUES"));
	}

	public InsertionFormatBuilder addTableName(String tableName) {
		if (!hasHeader) {
			this.mainClause.append(space());
			this.mainClause.append(tableName);
			this.mainClause.append(space());
			this.mainClause.append(open());
			this.complementaryClause.append(open());
			this.hasHeader = true;
		}
		return this;
	}

	public InsertionFormatBuilder addField(FieldMask field) {
		if (hasField) {
			this.mainClause.append(comma());
			this.complementaryClause.append(comma());
		} else {
			this.hasField = true;
		}
		this.mainClause.append(field.getFieldName());
		this.complementaryClause.append(this.formatValue(field));
		return this;
	}

	public InsertionFormatBuilder finish() {
		this.mainClause.append(close());
		this.complementaryClause.append(close()).append(";");
		return this;
	}

}
