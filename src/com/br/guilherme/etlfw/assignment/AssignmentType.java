package com.br.guilherme.etlfw.assignment;

public enum AssignmentType {

	INSERT, ALTER, DELETE;

	public static AssignmentType getAssignmentType(int number) {

		switch (number) {
		case 1:
			return AssignmentType.INSERT;
		case 2:
			return AssignmentType.ALTER;
		case 3:
			return AssignmentType.DELETE;
		}
		return null;
	}
}