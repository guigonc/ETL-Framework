package com.guilherme.client.aplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import com.guilherme.etlfw.assignment.AssignmentType;
import com.guilherme.etlfw.assignment.ModificationAssignment;
import com.guilherme.etlfw.exceptions.InvalidRegistrySizeException;
import com.guilherme.etlfw.exceptions.UnkownRegistryException;
import com.guilherme.etlfw.mask.field.DelimitedFieldMask;
import com.guilherme.etlfw.mask.field.FieldType;
import com.guilherme.etlfw.mask.field.FixedLengthFieldMask;
import com.guilherme.etlfw.mask.field.XMLFieldMask;
import com.guilherme.etlfw.mask.file.DelimitedFileMask;
import com.guilherme.etlfw.mask.file.FixedLengthFileMask;
import com.guilherme.etlfw.mask.file.XMLFileMask;
import com.guilherme.etlfw.mask.registry.DelimitedRegistryMask;
import com.guilherme.etlfw.mask.registry.FixedLengthRegistryMask;
import com.guilherme.etlfw.mask.registry.XMLRegistryMask;
import com.guilherme.etlfw.persistence.PersistenceMechanism;
import com.guilherme.etlfw.xml.element.XMLElement;
import com.guilherme.etlfw.xml.reader.XMLReader;

public class ControladorDeImportacao {
	public ControladorDeImportacao() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			execucaoXML();
			execucaoDelimitado();
			execucaoTamanhoFixo();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void execucaoXML() {
		XMLFileMask mascaraDeArquivo;
		XMLRegistryMask mascaraDeProfessor;
		XMLRegistryMask mascaraDeAluno;
		XMLFieldMask mascaraDoNome;
		XMLFieldMask mascaraDoCPF;
		
		mascaraDeArquivo = new XMLFileMask("PROF-ALUNO", "1.0", "Arquivo de Professores e Alunos");
		mascaraDeProfessor = new XMLRegistryMask("Professores", "professor","1.0", "Professor");
		mascaraDeAluno = new XMLRegistryMask("Alunos", "aluno","1.0", "Aluno");
		mascaraDoNome = new XMLFieldMask("Nome", "Nome", 25, 0, FieldType.A, false);
		mascaraDoCPF = new XMLFieldMask("CPF", "CPF", 14, 0, FieldType.A, true);
		
		mascaraDeProfessor.addField(mascaraDoNome);
		mascaraDeProfessor.addField(mascaraDoCPF);

		mascaraDeAluno.addField(mascaraDoNome);
		mascaraDeAluno.addField(mascaraDoCPF);
		mascaraDeArquivo.addRegistryMask(mascaraDeProfessor);
		mascaraDeArquivo.addRegistryMask(mascaraDeAluno);
		
		XMLReader reader = new XMLReader(
		"/home/guilherme/workspace/ETL-Framework/entrada.xml");
		XMLRegistryMask registro;
		try {
			do {
				PersistenceMechanism.startConnection(
						"jdbc:mysql://localhost/estagio2", "root", "");
				XMLElement element = reader.getElement();
				registro = mascaraDeArquivo.getRegistryWithValues(element);
				System.out.println(registro.formatToInsert());
				PersistenceMechanism.insert(registro);
			} while (reader.next());
			PersistenceMechanism.finishConnection();
		} catch (UnkownRegistryException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	
	private void execucaoDelimitado() {
		DelimitedFileMask mascaraDeArquivo;
		DelimitedRegistryMask mascaraDeProfessor;
		DelimitedFieldMask mascaraDoTipo;
		DelimitedRegistryMask mascaraDeAluno;
		DelimitedFieldMask mascaraDoNome;
		DelimitedFieldMask mascaraDoCPF;
		ModificationAssignment modificacao;
		mascaraDeArquivo = new DelimitedFileMask("PROF-ALUNO", ';', "1.0", "Arquivo de Professores e Alunos");
		mascaraDeProfessor = new DelimitedRegistryMask("Professores", "1.0", "Professor");
		mascaraDeAluno = new DelimitedRegistryMask("Alunos", "1.0", "Aluno");
		mascaraDoTipo = new DelimitedFieldMask("Tipo", 0, 9, 0, FieldType.A,true, false);
		mascaraDoNome = new DelimitedFieldMask("Nome", 1, 35, 0, FieldType.A,false, false);
		mascaraDoCPF = new DelimitedFieldMask("CPF", 2, 14, 0, FieldType.A,false, true);
		modificacao = new ModificationAssignment(AssignmentType.ALTER, false);
		
		mascaraDoNome.addAssignment(modificacao);
		
		mascaraDeProfessor.addField(mascaraDoTipo);
		mascaraDeProfessor.addField(mascaraDoNome);
		mascaraDeProfessor.addField(mascaraDoCPF);

		mascaraDeAluno.addField(mascaraDoTipo);
		mascaraDeAluno.addField(mascaraDoNome);
		mascaraDeAluno.addField(mascaraDoCPF);
		
		mascaraDeArquivo.addRegistryMask(mascaraDeProfessor);
		mascaraDeArquivo.addRegistryMask(mascaraDeAluno);

		try {
			FileReader leitorTool;
			File arquivo;
			BufferedReader leitor;
			DelimitedRegistryMask registro;
			String linha;
			arquivo = new File(
					"/home/guilherme/workspace/ETL-Framework/arquivo.txt");
			leitorTool = new FileReader(arquivo);
			leitor = new BufferedReader(leitorTool);
			PersistenceMechanism.startConnection(
					"jdbc:mysql://localhost/estagio2", "root", "");

			while ((linha = leitor.readLine()) != null) {
				try {
					registro = mascaraDeArquivo.getRegistryWithValues(linha);
					System.out.println(registro.formatToInsert());
					PersistenceMechanism.insert(registro);
				} catch (UnkownRegistryException e) {
					e.printStackTrace();
				} catch (InvalidRegistrySizeException e) {
					e.printStackTrace();
				}
			}
			PersistenceMechanism.finishConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	private void execucaoTamanhoFixo() {
		FixedLengthFileMask mascaraDeArquivo;
		FixedLengthRegistryMask mascaraDeProfessor;
		FixedLengthRegistryMask mascaraDeAluno;
		FixedLengthFieldMask mascaraDoTipo;
		FixedLengthFieldMask mascaraDoNome;
		FixedLengthFieldMask mascaraDoCPF;
		FixedLengthFieldMask mascaraDoSexo;
		ModificationAssignment pendencia;

		mascaraDeArquivo = new FixedLengthFileMask("PROF-AL", "1.0", "Arquivo de Professores e Alunos");
		mascaraDeProfessor = new FixedLengthRegistryMask("Professores", "1.0", "Professores");
		mascaraDeAluno = new FixedLengthRegistryMask("Alunos", "1.0", "Alunos");
		mascaraDoTipo = new FixedLengthFieldMask("Tipo", 1, 11, 0, FieldType.A,	true, false);
		mascaraDoNome = new FixedLengthFieldMask("Nome", 12, 35, 0, FieldType.A,	false, false);
		mascaraDoCPF = new FixedLengthFieldMask("CPF", 36, 49, 0, FieldType.A, false, true);
		mascaraDoSexo = new FixedLengthFieldMask("Sexo", 50, 58, 0, FieldType.A, false, false);
		
		pendencia = new ModificationAssignment(AssignmentType.INSERT, false);
		mascaraDoSexo.addAssignment(pendencia);
		
		mascaraDeProfessor.addField(mascaraDoTipo);
		mascaraDeProfessor.addField(mascaraDoNome);
		mascaraDeProfessor.addField(mascaraDoCPF);
		mascaraDeProfessor.addField(mascaraDoSexo);
		
		mascaraDeAluno.addField(mascaraDoTipo);
		mascaraDeAluno.addField(mascaraDoNome);
		mascaraDeAluno.addField(mascaraDoCPF);
		mascaraDeAluno.addField(mascaraDoSexo);

		mascaraDeArquivo.addRegistryMask(mascaraDeProfessor);
		mascaraDeArquivo.addRegistryMask(mascaraDeAluno);
			try {
				
			FileReader leitorTool;
			File arquivo;
			BufferedReader leitor;
			FixedLengthRegistryMask registro;
			String linha;
			arquivo = new File(
					"/home/guilherme/workspace/ETL-Framework/Professores.txt");
			leitorTool = new FileReader(arquivo);
			leitor = new BufferedReader(leitorTool);
			PersistenceMechanism.startConnection(
					"jdbc:mysql://localhost/estagio2", "root", "");
			while ((linha = leitor.readLine()) != null) {
				try {
					registro = mascaraDeArquivo.getRegistryWithValues(linha);
					System.out.println(registro.formatToInsert());
					PersistenceMechanism.insert(registro);
				} catch (UnkownRegistryException e) {
					e.printStackTrace();
				} catch (InvalidRegistrySizeException e) {
					e.printStackTrace();
				}
			}
			PersistenceMechanism.finishConnection();
			}  catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public static void main(String args[]) {
		ControladorDeImportacao c;
		c = new ControladorDeImportacao();
	}
}
