package etlfw.assignment;

import org.junit.Assert;
import org.junit.Test;
import com.br.guilherme.etlfw.assignment.AssignmentType;
import com.br.guilherme.etlfw.assignment.ModificationAssignment;

public class TestModificationAssignment extends Assert{
	
	@Test
	public void shouldInstance() {
		ModificationAssignment assignment; 
		assignment = new ModificationAssignment(AssignmentType.ALTER, false);
		assertEquals(AssignmentType.ALTER, assignment.getAssignmentTypeCode());
		assertEquals(false, assignment.isSolved());
	}

}
