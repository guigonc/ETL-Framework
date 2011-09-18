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
		execucaoDelimitado();
		//execucaoTamanhoFixo();
		//execucaoXML();
	}

	private void execucaoDelimitado() {
		DelimitedFileMask mascaraDeArquivo;
		DelimitedRegistryMask mascaraDeRegistro;
		DelimitedFieldMask mascaraDoNome;
		DelimitedFieldMask mascaraDoCPF;
		ModificationAssignment pendencia;

		mascaraDeArquivo = new DelimitedFileMask("PROF", ';', "1.0",
				"Arquivo de Professores");
		mascaraDeRegistro = new DelimitedRegistryMask("Professores", "1.0",
				"Professor");
		mascaraDoNome = new DelimitedFieldMask("Nome", 0, 25, 0, FieldType.A,
				false, true);
		mascaraDoCPF = new DelimitedFieldMask("CPF", 1, 14, 0, FieldType.A,
				false, true);
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
					//System.out.println(registro.formatToAlter());
					System.out.println(registro.formatToCreateTable());
					PersistenceMechanism.insert(registro);
				} catch (UnkownRegistryException e) {
					e.printStackTrace();
				} catch (InvalidRegistrySizeException e) {
					e.printStackTrace();
				}
			}
			PersistenceMechanism.finishConnection();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	private void execucaoXML() {
		XMLFileMask mascaraDeArquivo;
		XMLRegistryMask mascaraDeRegistro;
		XMLFieldMask mascaraDoNome;
		XMLFieldMask mascaraDoCPF;

		mascaraDeArquivo = new XMLFileMask("PROF", "1.0",
				"Arquivo de Professores");
		mascaraDeRegistro = new XMLRegistryMask("Professores", "professor",
				"1.0", "Professor");
		mascaraDoNome = new XMLFieldMask("Nome", "Nome", 25, 0, FieldType.A,
				false, false);
		mascaraDoCPF = new XMLFieldMask("CPF", "CPF", 14, 0, FieldType.A,
				false, true);

		mascaraDeRegistro.addField(mascaraDoNome);
		mascaraDeRegistro.addField(mascaraDoCPF);
		mascaraDeArquivo.addRegistryMask(mascaraDeRegistro);

		XMLReader reader = new XMLReader(
				"/home/guilherme/workspace/ETL-Framework/entrada.xml");
		XMLRegistryMask registro;
		try {
			do {
				PersistenceMechanism.startConnection(
						"jdbc:mysql://localhost/estagio2", "root", "");
				XMLElement element = reader.getElement();
				registro = mascaraDeArquivo.getRegistryWithValues(element);
				PersistenceMechanism.insert(registro);
			} while (reader.next());
			PersistenceMechanism.finishConnection();
		} catch (UnkownRegistryException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	private void execucaoTamanhoFixo() {
		FixedLengthFileMask mascaraDeArquivo;
		FixedLengthRegistryMask mascaraDeRegistro;
		FixedLengthFieldMask mascaraDoNome;
		FixedLengthFieldMask mascaraDoCPF;
		ModificationAssignment pendencia;

		mascaraDeArquivo = new FixedLengthFileMask("PROF", "1.0",
				"Arquivo de Professores");
		mascaraDeRegistro = new FixedLengthRegistryMask("Professores", "1.0",
				"Professor");
		mascaraDoNome = new FixedLengthFieldMask("Nome", 1, 25, 0, FieldType.A,
				false, false);
		mascaraDoCPF = new FixedLengthFieldMask("CPF", 26, 39, 0, FieldType.A,
				false, true);
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
					PersistenceMechanism.insert(registro);
				} catch (UnkownRegistryException e) {
					e.printStackTrace();
				} catch (InvalidRegistrySizeException e) {
					e.printStackTrace();
				}
			}
			PersistenceMechanism.finishConnection();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (FileNotFoundException e) {
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
