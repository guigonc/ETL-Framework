package com.guilherme.client.aplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.guilherme.etlfw.assignment.AssignmentType;
import com.guilherme.etlfw.assignment.ModificationAssignment;
import com.guilherme.etlfw.exceptions.InvalidRegistrySizeException;
import com.guilherme.etlfw.exceptions.UnkownRegistryException;
import com.guilherme.etlfw.mask.field.FieldType;
import com.guilherme.etlfw.mask.field.TextFieldMask;
import com.guilherme.etlfw.mask.field.XMLFieldMask;
import com.guilherme.etlfw.mask.file.TextFileMask;
import com.guilherme.etlfw.mask.file.XMLFileMask;
import com.guilherme.etlfw.mask.registry.TextRegistryMask;
import com.guilherme.etlfw.mask.registry.XMLRegistryMask;
import com.guilherme.etlfw.xml.element.XMLElement;
import com.guilherme.etlfw.xml.reader.XMLReader;

public class ControladorDeImportacao {
	public ControladorDeImportacao() {
		//execucaoTexto();
		execucaoXML();
	}
	
	private void execucaoXML() {
		XMLFileMask mascaraDeArquivo;
		XMLRegistryMask mascaraDeRegistro;
		XMLFieldMask mascaraDoNome;
		XMLFieldMask mascaraDoCPF;
		
		mascaraDeArquivo = new XMLFileMask("PROF", "1.0", "Arquivo de Professores");
		mascaraDeRegistro = new XMLRegistryMask("Professores", "professor", "1.0", "Professor");
		mascaraDoNome = new XMLFieldMask("Nome", "Nome", 25, 0, FieldType.A, false, false);
		mascaraDoCPF = new XMLFieldMask("CPF", "CPF", 14, 0, FieldType.A, false, true);
		
		mascaraDeRegistro.addField(mascaraDoNome);
		mascaraDeRegistro.addField(mascaraDoCPF);
		mascaraDeArquivo.addRegistryMask(mascaraDeRegistro);
		
		XMLReader reader = new XMLReader("/home/guilherme/workspace/ETL-Framework/entrada.xml");
		XMLRegistryMask registro;
		do {
			try {
				XMLElement element = reader.getElement();
				registro = mascaraDeArquivo.getRegistryWithValues(element);
				System.out.println(registro.formatToInsert());
			} 
			catch (UnkownRegistryException e) {e.printStackTrace();} 
		} while(reader.next());
		
	}

	private void execucaoTexto() {
		TextFileMask mascaraDeArquivo;
		TextRegistryMask mascaraDeRegistro;
		TextFieldMask mascaraDoNome;
		TextFieldMask mascaraDoCPF;
		ModificationAssignment pendencia;
		
		mascaraDeArquivo = new TextFileMask("PROF", "1.0", "Arquivo de Professores");
		mascaraDeRegistro = new TextRegistryMask("Professores", "1.0", "Professor");
		mascaraDoNome = new TextFieldMask("Nome", 1, 25, 0, FieldType.A, false, false);
		mascaraDoCPF = new TextFieldMask("CPF", 26, 39, 0, FieldType.A, false, true);
		pendencia = new ModificationAssignment(AssignmentType.ALTER, false);
		mascaraDoNome.addAssignment(pendencia);
		mascaraDeRegistro.addField(mascaraDoNome);
		mascaraDeRegistro.addField(mascaraDoCPF);
		mascaraDeArquivo.addRegistryMask(mascaraDeRegistro);
				
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			FileReader leitorTool;
			File arquivo;
			BufferedReader leitor;
			TextRegistryMask registro;
			String linha;
			arquivo = new File("/home/guilherme/workspace/ETL-Framework/Professores.txt");
			leitorTool = new FileReader(arquivo);
			leitor = new BufferedReader(leitorTool);
			
			while ((linha =leitor.readLine()) != null) {
				try {
					registro = mascaraDeArquivo.getRegistryWithValues(linha);
					
					insereRegistro(registro);
				} 
				catch (UnkownRegistryException e) {e.printStackTrace();} 
				catch (InvalidRegistrySizeException e) {e.printStackTrace();}
			}
		} 
		catch (Exception e1) {e1.printStackTrace();}
	}
	
	private void insereRegistro(TextRegistryMask registro) {
		criaTabela(registro);
		aplicaAlteracoes(registro);
		Connection conector;
		
		try {
			conector = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/estagio2", "root", "");
			conector.createStatement().executeUpdate(registro.formatToInsert());
			conector.close();
		} catch (SQLException e) { e.printStackTrace();}
	}
	
	private void criaTabela(TextRegistryMask registro) {
		String tabela = registro.formatToCreateTable();
		Connection conector;
		
		try {
			conector = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/estagio2", "root", "");
			conector.createStatement().executeUpdate(tabela);
			conector.close();
		} catch (SQLException e) { e.printStackTrace();}
	}

	private void aplicaAlteracoes(TextRegistryMask registro) {
		Connection conector;
		
		String pendencias = registro.formatToAlter();
		if (pendencias.isEmpty()) {
            return;
        }
		
		try {
			conector = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/estagio2", "root", "");
			conector.createStatement().executeUpdate(pendencias);
			registro.changeAssignmentState(true);
			conector.close();
		} catch (SQLException e) { e.printStackTrace();}
	}

	public static void main(String args[]) {
		ControladorDeImportacao c;
		c = new ControladorDeImportacao();
	}
}
