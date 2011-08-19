package com.br.guilherme.etlfw.mask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.MissingFormatWidthException;

import com.br.guilherme.etlfw.exceptions.InvalidRegistrySizeException;
import com.br.guilherme.etlfw.format.database.AlterationFormatBuilder;
import com.br.guilherme.etlfw.format.database.DeletionFormatBuilder;
import com.br.guilherme.etlfw.format.database.InsertionFormatBuilder;
import com.br.guilherme.etlfw.format.database.TableCreationFormatBuilder;

public class RegistryMask {

   private String codigo;
   private String versao;
   private String descricao;
   private int tamanho;
   private List<FieldMask> campos;
   private FieldMask identificador;

   public RegistryMask(final String codigoDoRegistro, String versaoDoRegistro, final String descricaoDoRegistro) {
      codigo       = codigoDoRegistro;
      versao       = versaoDoRegistro;
      descricao    = descricaoDoRegistro;
      tamanho      = 0;
      campos       = Collections.emptyList();
   }
   
   public String getTableName() { return codigo; }
   public String getVersion() { return versao; }
   public String getDescricao() { return descricao; }
   public List<FieldMask> getFields() { return campos; }
   public int getTamanho() { return tamanho; }
   public FieldMask getIdentificador() { return identificador; }

   public void addField(final FieldMask campo) {
      if (this.campos.isEmpty()) {
         this.campos = new ArrayList<FieldMask>();
      }
      this.campos.add(campo);

      atualizaTamanhoDoRegistro();
      identificaRegistro(campo);
   }

   private void identificaRegistro(final FieldMask campo) {
      if (campo.contemTipoRegistro() == true) {
         this.identificador = campo;
      }
   }

   private void atualizaTamanhoDoRegistro() {
      this.tamanho = 0;
      for (FieldMask mc: campos) {
         if (getTamanho() < mc.getPosicaoFinal()) {
            this.tamanho = mc.getPosicaoFinal();
         }
      }
   }
   
   public RegistryMask getRegistryWithValues(final String linhaDoArquivo) throws InvalidRegistrySizeException {
      int tamanhoDoRegistro = getTamanho();
      String linha = String.format("%1$-" + tamanhoDoRegistro + "s", linhaDoArquivo);
      int tamanhoDaLinha = linha.length();
      
      if (tamanhoDaLinha == tamanhoDoRegistro) {
         try {
            for (FieldMask mc : campos)
            	mc.setValor(linha.substring(mc.getPosicaoInicial() - 1, mc.getPosicaoFinal()));
         } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new InvalidRegistrySizeException(indexOutOfBoundsException);
         }
      } else {
            throw new InvalidRegistrySizeException();
      }
      return this;
   }

   public String formataParaTabela() {
      TableCreationFormatBuilder formatoDeTabelaBuilder = new TableCreationFormatBuilder();

      formatoDeTabelaBuilder.addTableName(getTableName());

      for (FieldMask campo : campos) {
         formatoDeTabelaBuilder.addField(campo);
      }
      formatoDeTabelaBuilder.finish();

      return formatoDeTabelaBuilder.toString();
   }

   public String formataParaInsercao() {
      InsertionFormatBuilder formatoDeInsercaoBuilder = new InsertionFormatBuilder();

      formatoDeInsercaoBuilder.addTableName(getTableName());
      for (FieldMask campo : campos) {
         formatoDeInsercaoBuilder.addField(campo);
      }
      formatoDeInsercaoBuilder.finish();
      return formatoDeInsercaoBuilder.toString();
   }

   String formataParaRemocao() {
      DeletionFormatBuilder formatoDeRemocaoBuilder = new DeletionFormatBuilder();

      formatoDeRemocaoBuilder.addTableName(getTableName());
      for (FieldMask campo : campos) {
         formatoDeRemocaoBuilder.addField(campo);
      }
      formatoDeRemocaoBuilder.finish();
      return formatoDeRemocaoBuilder.toString();
   }

   public String formataParaAlteracao() {
      AlterationFormatBuilder formatoDeAlteracaoBuilder = new AlterationFormatBuilder();

      formatoDeAlteracaoBuilder.addTableName(getTableName());
      for (FieldMask campo : campos) {
          formatoDeAlteracaoBuilder.addField(campo);
      }
      formatoDeAlteracaoBuilder.finish();
      return formatoDeAlteracaoBuilder.toString();
   }

   public int getInitialPosition() {
      return getIdentificador().getPosicaoInicial();
   }

   public int getFinalPosition() {
      return getIdentificador().getPosicaoFinal();
   }

   public void mudaEstadoDasPendenciasDeAlteracaoPara(boolean novoEstado) {    
      for (FieldMask mc : campos) {
         mc.modificaEstadoDasPendencias(novoEstado);
      }
   }
}