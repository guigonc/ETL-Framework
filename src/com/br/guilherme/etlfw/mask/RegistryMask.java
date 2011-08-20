package com.br.guilherme.etlfw.mask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.br.guilherme.etlfw.exceptions.InvalidRegistrySizeException;
import com.br.guilherme.etlfw.format.database.AlterationFormatBuilder;
import com.br.guilherme.etlfw.format.database.DeletionFormatBuilder;
import com.br.guilherme.etlfw.format.database.InsertionFormatBuilder;
import com.br.guilherme.etlfw.format.database.TableCreationFormatBuilder;

public class RegistryMask {

   private String tableName;
   private String version;
   private String description;
   private int size;
   private List<FieldMask> fields;
   private FieldMask identifier;

   public RegistryMask(final String tableName, String version, final String description) {
      this.tableName = tableName;
      this.version = version;
      this.description = description;
      size = 0;
      fields = Collections.emptyList();
   }
   
   public String getTableName() { return tableName; }
   
   public String getVersion() { return version; }
   
   public String getDescription() { return description; }
   
   public List<FieldMask> getFields() { return fields; }
   
   public int size() { return size; }
   
   public FieldMask getIdentifier() { return identifier; }

   public void addField(final FieldMask field) {
      if (this.fields.isEmpty()) {
         this.fields = new ArrayList<FieldMask>();
      }
      this.fields.add(field);

      setSize();
      identifyRegistry(field);
   }

   private void identifyRegistry(final FieldMask field) {
      if (field.hasRegistryType() == true) {
         this.identifier = field;
      }
   }

   private void setSize() {
      this.size = 0;
      for (FieldMask mask: fields) {
         if (size() < mask.getFinalPosition()) {
            this.size = mask.getFinalPosition();
         }
      }
   }
   
   public RegistryMask getRegistryWithValues(final String fileLine) throws InvalidRegistrySizeException {
      int registrySize = size();
      String line = String.format("%1$-" + registrySize + "s", fileLine);
      int lineSize = line.length();
      
      if (lineSize == registrySize) {
         try {
            for (FieldMask mask : fields)
            	mask.setValue(line.substring(mask.getInitialPosition() - 1, mask.getFinalPosition()));
         } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new InvalidRegistrySizeException(indexOutOfBoundsException);
         }
      } else {
            throw new InvalidRegistrySizeException();
      }
      return this;
   }

   public String formatToCreateTable() {
      TableCreationFormatBuilder tableCreationFormat = new TableCreationFormatBuilder();

      tableCreationFormat.addTableName(getTableName());

      for (FieldMask field : fields) {
         tableCreationFormat.addField(field);
      }
      tableCreationFormat.finish();

      return tableCreationFormat.toString();
   }

   public String formatToInsert() {
      InsertionFormatBuilder insertionFormat = new InsertionFormatBuilder();

      insertionFormat.addTableName(getTableName());
      for (FieldMask field : fields) {
         insertionFormat.addField(field);
      }
      insertionFormat.finish();
      return insertionFormat.toString();
   }

   String formatToDelete() {
      DeletionFormatBuilder deletionformat = new DeletionFormatBuilder();

      deletionformat.addTableName(getTableName());
      for (FieldMask field : fields) {
         deletionformat.addField(field);
      }
      deletionformat.finish();
      return deletionformat.toString();
   }

   public String formatToAlter() {
      AlterationFormatBuilder alterationFormat = new AlterationFormatBuilder();

      alterationFormat.addTableName(getTableName());
      for (FieldMask field : fields) {
          alterationFormat.addField(field);
      }
      alterationFormat.finish();
      return alterationFormat.toString();
   }

   public int getInitialPosition() {
      return getIdentifier().getInitialPosition();
   }

   public int getFinalPosition(){
	   	return getIdentifier().getFinalPosition();
   }

   public void changeAssignmentState(boolean state) {    
      for (FieldMask field : fields) {
         field.modifyAssignmentState(state);
      }
   }
   
}