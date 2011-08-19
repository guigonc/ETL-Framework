package etlfw.mask;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.br.guilherme.etlfw.mask.RegistryMask;

public class TestMascaraDeRegistro extends Assert {
	RegistryMask mascara;
	
	@Before
	public void setUp() {
		mascara = new RegistryMask("Professores", "1.0", "Professor");
	}
	
	@After
	public void tearDown() {
		mascara = null;
	}
	
	@Test
	public void shouldInstance() {
		assertTrue(mascara instanceof RegistryMask);
	}
}
