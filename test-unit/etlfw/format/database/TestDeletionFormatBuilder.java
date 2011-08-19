package etlfw.format.database;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.br.guilherme.etlfw.assignment.AssignmentType;
import com.br.guilherme.etlfw.assignment.ModificationAssignment;
import com.br.guilherme.etlfw.format.database.DeletionFormatBuilder;
import com.br.guilherme.etlfw.mask.FieldMask;
import com.br.guilherme.etlfw.mask.FieldType;

public class TestDeletionFormatBuilder extends Assert {
	
	private DeletionFormatBuilder deletionFormat;

	@Before
	public void setUp() { 
		deletionFormat = new DeletionFormatBuilder();
	}
	
	@After
	public void tearDown() {
		deletionFormat = null;
	}
	
	@Test
	public void shouldInstance() {
		assertTrue(deletionFormat instanceof DeletionFormatBuilder);
		assertEquals("DELETE FROM", deletionFormat.toString());
	}
	
	@Test
	public void shouldAddTableNameOnStatement() {
		deletionFormat.addTableName("Table");
		assertEquals("DELETE FROM Table WHERE ", deletionFormat.toString());
	}
	
	@Test
	public void shouldCreateStatement() {
		deletionFormat.addTableName("Table");
		
		ModificationAssignment assignment = new ModificationAssignment(AssignmentType.ALTER, false);
		FieldMask mask = new FieldMask("Field", 1, 10, 2, FieldType.A, false, true, false);
		mask.addAssignment(assignment);
		deletionFormat.addField(mask).addField(mask).finish();
		
		assertEquals("DELETE FROM Table WHERE FIELD=\"null\" AND FIELD=\"null\";", deletionFormat.toString());
	}
	
}
