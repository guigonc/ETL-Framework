package com.br.guilherme.etlfw.mask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.br.guilherme.etlfw.assignment.ModificationAssignment;

public class FieldMask{

    private String codigo;
    private int posicaoInicial;
    private int posicaoFinal;
    private FieldType tipoDeCampo;
    private int tamanho;
    private int decimais;
    private boolean contemTipoRegistro;
    private boolean contemChavePrimaria;
    private String valor;
    private List<ModificationAssignment> pendencias;

    public FieldMask(String codigo, int inicio, int fim, int decimais, FieldType tipoDeCampo,
    		boolean contemTipoDeRegistro, boolean contemChavePrimaria, boolean contemNumeroDeVersao) {
    	this.codigo = codigo.toUpperCase();
    	this.posicaoInicial = inicio;
    	this.posicaoFinal = fim;
        this.tipoDeCampo = tipoDeCampo;
        this.tamanho = fim - inicio  + 1;
        this.decimais = decimais;
        this.contemTipoRegistro = contemTipoDeRegistro;
        this.contemChavePrimaria = contemChavePrimaria;
        this.pendencias = Collections.emptyList();
    }

    public String getTableName() { return codigo; }
    
    public int getPosicaoInicial() { return posicaoInicial; }
    
    public int getPosicaoFinal() { return posicaoFinal; }
    
    public int getDecimalPlaces() { return decimais; }
    
    public FieldType getFieldType() { return tipoDeCampo; }
    
    public int size() { return tamanho; }
    
    public boolean contemTipoRegistro() { return contemTipoRegistro; }
    
    public boolean contemChavePrimaria() { return contemChavePrimaria; }
    
    public String getValue() { return valor; }
    
    public void setValor(String valor) { this.valor = valor; }
    
    public List<ModificationAssignment> getAlterationAssignment() { return pendencias; }
    
	public void addAssignment(final ModificationAssignment pendencia) {
		if (pendencias.isEmpty()) {
			pendencias = new ArrayList<ModificationAssignment>();
		}
		pendencias.add(pendencia);
	}

	public void modificaEstadoDasPendencias(boolean novoEstado) {
		for (ModificationAssignment pendencia : getAlterationAssignment()) {
			pendencia.setSolved(novoEstado);
		}
	}
   
}