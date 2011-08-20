package com.br.guilherme.etlfw.mask;

public enum FieldType {

   A, N, NS, AN, ANS, MM, DD, YYYY, HH, MI, SS, DATA1, DATE2, CID, HOUR, UF, ML, SL;

   public static String getIndexFieldType(FieldType type) {

      if (type.equals(FieldType.A)) {
         return "0";
      }

      if (type.equals(FieldType.N)) {
         return "1";
      }

      if (type.equals(FieldType.NS)) {
         return "2";
      }

      if (type.equals(FieldType.AN)) {
         return "3";
      }

      if (type.equals(FieldType.ANS)) {
         return "4";
      }

      if (type.equals(FieldType.MM)) {
         return "5";
      }

      if (type.equals(FieldType.DD)) {
         return "6";
      }

      if (type.equals(FieldType.YYYY)) {
         return "7";
      }

      if (type.equals(FieldType.HH)) {
         return "8";
      }

      if (type.equals(FieldType.MI)) {
         return "9";
      }

      if (type.equals(FieldType.SS)) {
         return "10";
      }

      if (type.equals(FieldType.DATA1)) {
         return "11";
      }

      if (type.equals(FieldType.DATE2)) {
         return "12";
      }

      if (type.equals(FieldType.CID)) {
         return "13";
      }

      if (type.equals(FieldType.HOUR)) {
         return "14";
      }

      if (type.equals(FieldType.UF)) {
         return "15";
      }

      if (type.equals(FieldType.ML)) {
         return "90";
      }

      if (type.equals(FieldType.SL)) {
         return "91";
      }


      return "0";


   }

   public static FieldType getFieldType(int number) {
      switch (number) {
         case 0:
            return FieldType.A;
         case 1:
            return FieldType.N;
         case 2:
            return FieldType.NS;
         case 3:
            return FieldType.AN;
         case 4:
            return FieldType.ANS;
         case 5:
            return FieldType.MM;
         case 6:
            return FieldType.DD;
         case 7:
            return FieldType.YYYY;
         case 8:
            return FieldType.HH;
         case 9:
            return FieldType.MI;
         case 10:
            return FieldType.SS;
         case 11:
            return FieldType.DATA1;
         case 12:
            return FieldType.DATE2;
         case 13:
            return FieldType.CID;
         case 14:
            return FieldType.HOUR;
         case 15:
            return FieldType.UF;
         case 90:
            return FieldType.ML;
         case 91:
            return FieldType.SL;

      }
      return FieldType.A;
   }

   public static String getDataBaseFieldFormat(FieldType type) {

      if (type.equals(FieldType.A)) {
         return "VARCHAR";
      }

      if (type.equals(FieldType.N)) {
         return "DECIMAL";
      }

      if (type.equals(FieldType.NS)) {
         return "DECIMAL";
      }

      if (type.equals(FieldType.AN)) {
         return "VARCHAR";
      }

      if (type.equals(FieldType.ANS)) {
         return "VARCHAR";
      }

      if (type.equals(FieldType.MM)) {
         return "DECIMAL";
      }

      if (type.equals(FieldType.DD)) {
         return "DECIMAL";
      }

      if (type.equals(FieldType.YYYY)) {
         return "DECIMAL";
      }

      if (type.equals(FieldType.HH)) {
         return "DECIMAL";
      }

      if (type.equals(FieldType.MI)) {
         return "DECIMAL";
      }

      if (type.equals(FieldType.SS)) {
         return "DECIMAL";
      }

      if (type.equals(FieldType.DATA1)) {
         return "VARCHAR";
      }

      if (type.equals(FieldType.DATE2)) {
         return "DATE";
      }

      if (type.equals(FieldType.CID)) {
         return "VARCHAR";
      }

      if (type.equals(FieldType.HOUR)) {
         return "TIME";
      }

      if (type.equals(FieldType.UF)) {
         return "CHAR";
      }

      if (type.equals(FieldType.ML)) {
         return "DECIMAL";
      }

      if (type.equals(FieldType.SL)) {
         return "DECIMAL";
      }
      return "VARCHAR";
   }
}