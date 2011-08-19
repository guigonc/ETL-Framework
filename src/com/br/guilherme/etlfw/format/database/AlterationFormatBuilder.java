package com.br.guilherme.etlfw.format.database;

import com.br.guilherme.etlfw.assignment.ModificationAssignment;
import com.br.guilherme.etlfw.assignment.AssignmentType;
import com.br.guilherme.etlfw.mask.FieldMask;

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

	public AlterationFormatBuilder addField(FieldMask mask) {
		String formattedAssignment;
		formattedAssignment = formatAssignment(mask);
		if (!formattedAssignment.isEmpty()) {
			this.mainClause.append(formattedAssignment);
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
			this.mainClause.append(";");
		} else {
			this.mainClause = new StringBuilder();
		}
		return this;
	}
}
