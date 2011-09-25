package com.guilherme.etlfw.dbformat;

import com.guilherme.etlfw.mask.field.FieldMask;

public class TableCreationFormatBuilder extends DataBaseFormat {

	public TableCreationFormatBuilder() {
		super(new StringBuilder("CREATE TABLE IF NOT EXISTS"));
	}

	public TableCreationFormatBuilder addTableName(String tableName) {
		if (!hasHeader) {
			mainClause.append(space());
			mainClause.append(tableName);
			mainClause.append(space());
			mainClause.append(open());
			hasHeader = true;
		}
		return this;
	}

	public TableCreationFormatBuilder addField(FieldMask mask) {
		if (hasField) {
			mainClause.append(comma());
		} else {
			hasField = true;
		}
		mainClause.append(formatField(mask));
		if (mask.isPrimaryKey()) {
			if(complementaryClause == null)
				complementaryClause = new StringBuilder(space() + "PRIMARY KEY" + open());
			else
				complementaryClause.append(comma());
			complementaryClause.append(mask.getFieldName());
		}
		return this;
	}

	public TableCreationFormatBuilder finish() {
		if(complementaryClause != null) {
			complementaryClause.append(close());
			mainClause.append(comma()).append(complementaryClause);
			complementaryClause = null;
		}
		mainClause.append(close());
		mainClause.append("ENGINE='MYISAM';");
		return this;
	}

}
