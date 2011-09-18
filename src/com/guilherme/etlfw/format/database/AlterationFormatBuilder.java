package com.guilherme.etlfw.format.database;

import com.guilherme.etlfw.assignment.AssignmentType;
import com.guilherme.etlfw.assignment.ModificationAssignment;
import com.guilherme.etlfw.mask.field.FieldMask;

public class AlterationFormatBuilder extends DataBaseFormat {

	public AlterationFormatBuilder() {
		super(new StringBuilder("ALTER TABLE"));
	}

	public AlterationFormatBuilder addTableName(String tableName) {
		if (!hasHeader) {
			this.mainClause.append(space());
			this.mainClause.append(tableName);
			this.mainClause.append(space());
			this.hasHeader = true;
		}
		return this;
	}

	public AlterationFormatBuilder addField(FieldMask field) {
		String formattedAssignment;
		formattedAssignment = formatAssignment(field);
		if (!formattedAssignment.isEmpty()) {
			this.mainClause.append(formattedAssignment);
		}
		if(field.isPrimaryKey()) {
			if(complementaryClause == null)
				complementaryClause = new StringBuilder(space() + "DROP PRIMARY KEY" + comma() + " ADD PRIMARY KEY" + open());
			else
				complementaryClause.append(comma());
			complementaryClause.append(field.getFieldName());
		}
		return this;
	}

	private String formatAssignment(FieldMask field) {
		StringBuilder result = new StringBuilder();

		for (ModificationAssignment assignment : field.getAlterationAssignment()) {
			if ((assignment.isSolved())
					|| (assignment.getAssignmentTypeCode() == AssignmentType.DELETE)) {
				continue;
			}
			if (hasField) {
				result.append(comma());
			} else {
				hasField = true;
			}
			switch (assignment.getAssignmentTypeCode()) {
			case INSERT:
				result.append("ADD COLUMN").append(space())
						.append(formatField(field));
				break;
			case ALTER:

				result.append("MODIFY").append(space())
						.append(formatField(field));
				break;
			}
		}
		return result.toString();
	}

	public AlterationFormatBuilder finish() {
		if (hasField) {
			if(complementaryClause != null) {
				complementaryClause.append(close());
				mainClause.append(comma()).append(complementaryClause);
				complementaryClause = null;
			}
			this.mainClause.append(";");
		} else {
			this.mainClause = new StringBuilder();
			this.complementaryClause = new StringBuilder();
		}
		return this;
	}
	
}
