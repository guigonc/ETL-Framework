package com.br.guilherme.etlfw.mask.registry;

import java.util.List;

import com.br.guilherme.etlfw.format.database.DeletionFormatBuilder;
import com.br.guilherme.etlfw.format.database.InsertionFormatBuilder;
import com.br.guilherme.etlfw.format.database.TableCreationFormatBuilder;
import com.br.guilherme.etlfw.mask.field.FieldMask;
import com.br.guilherme.etlfw.mask.field.TextFieldMask;
import com.br.guilherme.etlfw.mask.field.XMLFieldMask;

public abstract class RegistryMask<T extends FieldMask> {
	protected String tableName;
	protected String version;
	protected String description;
	protected List<T> fields;
	protected T identifier;
	
	public String getTableName() { return tableName; }
	   
	   public String getVersion() { return version; }
	   
	   public String getDescription() { return description; }

	public String formatToInsert() {
		InsertionFormatBuilder insertionFormat = new InsertionFormatBuilder();

		insertionFormat.addTableName(getTableName());
		for (T field : fields) {
			insertionFormat.addField(field);
		}
		insertionFormat.finish();
		return insertionFormat.toString();
	}
	
	public String formatToCreateTable() {
	      TableCreationFormatBuilder tableCreationFormat = new TableCreationFormatBuilder();

	      tableCreationFormat.addTableName(getTableName());

	      for (T field : fields) {
	         tableCreationFormat.addField(field);
	      }
	      tableCreationFormat.finish();

	      return tableCreationFormat.toString();
	   }

	   public String formatToDelete() {
	      DeletionFormatBuilder deletionformat = new DeletionFormatBuilder();

	      deletionformat.addTableName(getTableName());
	      for (T field : fields) {
	         deletionformat.addField(field);
	      }
	      deletionformat.finish();
	      return deletionformat.toString();
	   }
	   
}
