package com.br.guilherme.etlfw.format.database;

import com.br.guilherme.etlfw.mask.field.FieldType;
import com.br.guilherme.etlfw.mask.field.TextFieldMask;

public abstract class DataBaseFormat {

   protected StringBuilder mainClause;
   protected StringBuilder complementaryClause;
   protected boolean hasHeader;
   protected boolean hasField;

   protected DataBaseFormat(StringBuilder mainClause) {
      this.mainClause = mainClause;
      complementaryClause = null;
      hasHeader = false;
      hasField = false;
   }

   protected DataBaseFormat(StringBuilder mainClause, StringBuilder complementaryClause) {
      this.mainClause  = mainClause;
      this.complementaryClause = complementaryClause;
      hasHeader = false;
      hasField = false;

   }

   protected final String formatField(TextFieldMask mask) {
      StringBuilder result = new StringBuilder();

      result.append(mask.getFieldName())
              .append(space())
              .append(FieldType.getDataBaseFieldFormat(mask.getFieldType()));

      if (mask.getDecimalPlaces() > 0) {
         result.append(open())
                 .append(mask.size() + mask.getDecimalPlaces())
                 .append(comma()).append(mask.getDecimalPlaces())
                 .append(close());
      } else {
         switch (mask.getFieldType()) {
            case DATE2:
            case HOUR:
               break;
            default:
               result.append(open()).append(mask.size()).append(close());
         }
      }
      return result.toString();
   }

   protected final String formatValue(TextFieldMask mask) {
      StringBuilder result = new StringBuilder();
      switch (mask.getFieldType()) {
         case N:
         case NS:
            result.append(formatDecimalValue(mask.getValue(), mask.getDecimalPlaces()));
            break;
         case MM:
         case DD:
         case YYYY:
         case HH:
         case MI:
         case SS:
         case ML:
         case SL:
            result.append(mask.getValue());
            break;
         default:
            result.append("\"").append(mask.getValue()).append("\"");
      }

      return result.toString();
   }

   protected final String formatDecimalValue(String value, int decimalPlaces) {
      StringBuilder result = new StringBuilder();
      
      if (decimalPlaces == 0) {
         result.append(value);
      } else {
         double divider = Math.pow(10, decimalPlaces);
         double valueWithDecimalPlaces = Double.valueOf(value.trim()).doubleValue() / divider;
         result.append(valueWithDecimalPlaces);
      }
      return result.toString();
   }

   protected final String formatCondition(TextFieldMask mask, String operator) {
      StringBuilder resultado = new StringBuilder();

      resultado.append(mask.getFieldName())
               .append(operator)
               .append(formatValue(mask));

      return resultado.toString();
   }

   protected final char open() { return '('; }

   protected final char space() { return ' ';}

   protected final char close() { return ')'; }
   
   protected final char comma() { return ','; }

   @Override
   public String toString() {
      if (complementaryClause != null){
         mainClause.append(complementaryClause);
      }
      return mainClause.toString();
   }

}
