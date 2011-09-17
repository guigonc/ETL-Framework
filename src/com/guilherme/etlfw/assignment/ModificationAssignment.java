package com.guilherme.etlfw.assignment;

public class ModificationAssignment {
	private AssignmentType assignmentTypeCode;
	private boolean solved;

	public ModificationAssignment(AssignmentType modificationType,
			boolean assignmentSolved) {
		this.assignmentTypeCode = modificationType;
		this.solved = assignmentSolved;
	}

	public AssignmentType getAssignmentTypeCode() {
		return assignmentTypeCode;
	}

	public void setAssignmentTypeCode(int assignmentCode) {
		this.assignmentTypeCode = AssignmentType
				.getAssignmentType(assignmentCode);
	}

	public boolean isSolved() {
		return solved;
	}

	public void setSolved(boolean status) {
		this.solved = status;
	}
}
