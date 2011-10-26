package com.guilherme.etlfw.dbformat;

import com.guilherme.etlfw.mask.field.FieldMask;
import com.guilherme.etlfw.mask.field.FieldType;

public abstract class DataBaseFormat {

	protected StringBuilder mainClause;
	protected StringBuilder complementaryClause;
	protected boolean hasHeader;
	protected boolean hasField;

	protected DataBaseFormat(StringBuilder mainClause) {
		this.mainClause = mainClause;
		complementaryClause = null;
		hasHeader = false;
		hasField = false;
	}

	protected DataBaseFormat(StringBuilder mainClause,
			StringBuilder complementaryClause) {
		this.mainClause = mainClause;
		this.complementaryClause = complementaryClause;
		hasHeader = false;
		hasField = false;

	}

	protected final String formatField(FieldMask field) {
		StringBuilder result = new StringBuilder();

		result.append(field.getFieldName()).append(space())
				.append(FieldType.getDataBaseFieldFormat(field.getFieldType()));

		if (field.getDecimalPlaces() > 0) {
			result.append(open())
					.append(field.size() + field.getDecimalPlaces())
					.append(comma()).append(field.getDecimalPlaces())
					.append(close());
		} else {
			switch (field.getFieldType()) {
			case DATE2:
			case HOUR:
				break;
			default:
				result.append(open()).append(field.size()).append(close());
			}
		}
		return result.toString();
	}

	protected final String formatValue(FieldMask field) {
		StringBuilder result = new StringBuilder();
		switch (field.getFieldType()) {
		case N:
			result.append(formatDecimalValue(field.getValue(),
					field.getDecimalPlaces()));
			break;
		case MM:
		case DD:
		case YYYY:
		case HH:
		case MI:
		case SS:
			result.append(field.getValue());
			break;
		default:
			result.append("\"").append(field.getValue()).append("\"");
		}

		return result.toString();
	}

	protected final String formatDecimalValue(String value, int decimalPlaces) {
		StringBuilder result = new StringBuilder();

		if (decimalPlaces == 0) {
			result.append(value);
		} else {
			double divider = Math.pow(10, decimalPlaces);
			double valueWithDecimalPlaces = Double.valueOf(value.trim())
					.doubleValue() / divider;
			result.append(valueWithDecimalPlaces);
		}
		return result.toString();
	}

	protected final String formatCondition(FieldMask mask, String operator) {
		StringBuilder resultado = new StringBuilder();

		resultado.append(mask.getFieldName()).append(operator)
				.append(formatValue(mask));

		return resultado.toString();
	}

	protected final char open() {
		return '(';
	}

	protected final char space() {
		return ' ';
	}

	protected final char close() {
		return ')';
	}

	protected final char comma() {
		return ',';
	}

	@Override
	public String toString() {
		if (complementaryClause != null) {
			mainClause.append(complementaryClause);
		}
		return mainClause.toString();
	}

}
