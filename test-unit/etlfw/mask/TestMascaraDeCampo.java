package etlfw.mask;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.br.guilherme.etlfw.assignment.AssignmentType;
import com.br.guilherme.etlfw.assignment.ModificationAssignment;
import com.br.guilherme.etlfw.mask.FieldMask;
import com.br.guilherme.etlfw.mask.FieldType;

public class TestMascaraDeCampo extends Assert {
	
	FieldMask mascaraDeCampo;
	
	@Before
	public void setUp() {
		mascaraDeCampo = new FieldMask("Campo", 1, 10, 2, FieldType.A, false, true, false);
	}
	
	@After
	public void tearDown() {
		mascaraDeCampo = null;
	}
	
	@Test
	public void shouldInstance() {
		assertTrue(mascaraDeCampo instanceof FieldMask);
		assertEquals("CAMPO", mascaraDeCampo.getTableName());
		assertEquals(1,	mascaraDeCampo.getPosicaoInicial());
		assertEquals(10, mascaraDeCampo.getPosicaoFinal());
		assertEquals(FieldType.A, mascaraDeCampo.getFieldType());
		assertEquals(10, mascaraDeCampo.size());
		assertEquals(2, mascaraDeCampo.getDecimalPlaces());
		assertEquals(false, mascaraDeCampo.contemTipoRegistro());
		assertEquals(true, mascaraDeCampo.contemChavePrimaria());
	}
	
	@Test
	public void shouldAdicionaPendenciasDeAlteracao() {
		ModificationAssignment pendencia = new ModificationAssignment(AssignmentType.ALTER, false);
		
		assertTrue(mascaraDeCampo.getAlterationAssignment().isEmpty());

		mascaraDeCampo.addAssignment(pendencia);
		assertTrue(!mascaraDeCampo.getAlterationAssignment().isEmpty());
		assertTrue(mascaraDeCampo.getAlterationAssignment().get(0) instanceof ModificationAssignment);
	}
	
	@Test
	public void shouldModificaEstadoDaPendencia() {
		ModificationAssignment pendencia = new ModificationAssignment(AssignmentType.ALTER, false);
		
		mascaraDeCampo.addAssignment(pendencia);

		assertTrue(!mascaraDeCampo.getAlterationAssignment().get(0).isSolved());
	
		mascaraDeCampo.modificaEstadoDasPendencias(true);
		assertTrue(mascaraDeCampo.getAlterationAssignment().get(0).isSolved());
	}
	
}