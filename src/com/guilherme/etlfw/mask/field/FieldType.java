package com.guilherme.etlfw.mask.field;

public enum FieldType {

	A, N, MM, DD, YYYY, HH, MI, SS, DATE1, DATE2, HOUR, UF;

	public static String getIndexFieldType(FieldType type) {

		if (type.equals(FieldType.A)) {
			return "0";
		}

		if (type.equals(FieldType.N)) {
			return "1";
		}


		if (type.equals(FieldType.MM)) {
			return "2";
		}

		if (type.equals(FieldType.DD)) {
			return "3";
		}

		if (type.equals(FieldType.YYYY)) {
			return "4";
		}

		if (type.equals(FieldType.HH)) {
			return "5";
		}

		if (type.equals(FieldType.MI)) {
			return "6";
		}

		if (type.equals(FieldType.SS)) {
			return "7";
		}

		if (type.equals(FieldType.DATE1)) {
			return "8";
		}

		if (type.equals(FieldType.DATE2)) {
			return "9";
		}

		if (type.equals(FieldType.HOUR)) {
			return "10";
		}

		if (type.equals(FieldType.UF)) {
			return "11";
		}

		return "0";

	}

	public static FieldType getFieldType(int number) {
		switch (number) {
		case 0:
			return FieldType.A;
		case 1:
			return FieldType.N;
		case 2:
			return FieldType.MM;
		case 3:
			return FieldType.DD;
		case 4:
			return FieldType.YYYY;
		case 5:
			return FieldType.HH;
		case 6:
			return FieldType.MI;
		case 7:
			return FieldType.SS;
		case 8:
			return FieldType.DATE1;
		case 9:
			return FieldType.DATE2;
		case 10:
			return FieldType.HOUR;
		case 11:
			return FieldType.UF;
		}
		return FieldType.A;
	}

	public static String getDataBaseFieldFormat(FieldType type) {

		if (type.equals(FieldType.A)) {
			return "VARCHAR";
		}

		if (type.equals(FieldType.N)) {
			return "DECIMAL";
		}

		if (type.equals(FieldType.MM)) {
			return "DECIMAL";
		}

		if (type.equals(FieldType.DD)) {
			return "DECIMAL";
		}

		if (type.equals(FieldType.YYYY)) {
			return "DECIMAL";
		}

		if (type.equals(FieldType.HH)) {
			return "DECIMAL";
		}

		if (type.equals(FieldType.MI)) {
			return "DECIMAL";
		}

		if (type.equals(FieldType.SS)) {
			return "DECIMAL";
		}

		if (type.equals(FieldType.DATE1)) {
			return "VARCHAR";
		}

		if (type.equals(FieldType.DATE2)) {
			return "DATE";
		}

		if (type.equals(FieldType.HOUR)) {
			return "TIME";
		}

		if (type.equals(FieldType.UF)) {
			return "CHAR";
		}

		return "VARCHAR";
	}
}