package com.br.guilherme.etlfw.mask.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.br.guilherme.etlfw.format.database.AlterationFormatBuilder;
import com.br.guilherme.etlfw.format.database.DeletionFormatBuilder;
import com.br.guilherme.etlfw.format.database.InsertionFormatBuilder;
import com.br.guilherme.etlfw.format.database.TableCreationFormatBuilder;
import com.br.guilherme.etlfw.mask.field.XMLFieldMask;

public class XMLRegistryMask extends RegistryMask{

   private String tableName;
   private String tagName; 
   private String version;
   private String description;
   private int size;
   private List<XMLFieldMask> fields;
   private XMLFieldMask identifier;

   public XMLRegistryMask(String tableName, String tagName, String version, String description) {
      this.tableName = tableName;
      this.tagName = tagName;
      this.version = version;
      this.description = description;
      size = 0;
      fields = Collections.emptyList();
   }
   
   public String getTableName() { return tableName; }
   
   public String getTagName() { return tagName; }
   
   public String getVersion() { return version; }
   
   public String getDescription() { return description; }
   
   public List<XMLFieldMask> getFields() { return fields; }
   
   public int size() { return size; }
   
   public XMLFieldMask getIdentifier() { return identifier; }

   public void addField(final XMLFieldMask field) {
      if (this.fields.isEmpty()) {
         this.fields = new ArrayList<XMLFieldMask>();
      }
      this.fields.add(field);

      setSize();
      identifyRegistry(field);
   }

   private void identifyRegistry(final XMLFieldMask field) {
      if (field.hasRegistryType() == true) {
         this.identifier = field;
      }
   }

   private void setSize() {
      this.size = 0;
      for (XMLFieldMask mask: fields) {
            this.size += mask.size();
         }
      }
   
   /*public XMLRegistryMask getRegistryWithValues(final NodeList fileElement) throws InvalidRegistrySizeException {
      int registrySize = size();
      String line = String.format("%1$-" + registrySize + "s", fileElement);
      int lineSize = line.length();
      
      if (lineSize == registrySize) {
         try {
            for (XMLFieldMask mask : fields)
            	mask.setValue(line.substring(mask.getInitialPosition() - 1, mask.getFinalPosition()));
         } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new InvalidRegistrySizeException(indexOutOfBoundsException);
         }
      } else {
            throw new InvalidRegistrySizeException();
      }
      return this;
   }*/

   public String formatToAlter() {
      AlterationFormatBuilder alterationFormat = new AlterationFormatBuilder();

      alterationFormat.addTableName(getTableName());
      for (XMLFieldMask field : fields) {
          alterationFormat.addField(field);
      }
      alterationFormat.finish();
      return alterationFormat.toString();
   }

   public void changeAssignmentState(boolean state) {    
      for (XMLFieldMask field : fields) {
         field.modifyAssignmentState(state);
      }
   }
   
}