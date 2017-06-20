package com.example.android.cinemusp.modelo;

/**
 * @author grupo 4 - Turma B POO
 * Classe de preco
 */
public class Preco {

    private int idPreco;
    private float precoPadrao;
    private float precoReclinavel;
    private float precoMovel;
    private float precoObeso;
    private float precoCasal;
    private float precoCadeirante;
    
    public Preco(){
        
    }
    
    public Preco(float precoPadrao, float precoReclinavel, float precoMovel,
            float precoObeso, float precoCasal, float precoCadeirante) {
        this.precoPadrao = precoPadrao;
        this.precoReclinavel = precoReclinavel;
        this.precoMovel = precoMovel;
        this.precoObeso = precoObeso;
        this.precoCasal = precoCasal;
        this.precoCadeirante = precoCadeirante;
    }

    /**
     * @return the idPreco
     */

    /**
     * @param idPreco the idPreco to set
     */
    public void setIdPreco(int idPreco) {
        this.idPreco = idPreco;
    }

    /**
     * @return the precoPadrao
     */
    public float getPrecoPadrao() {
        return precoPadrao;
    }

    /**
     * @param precoPadrao the precoPadrao to set
     */
    public void setPrecoPadrao(float precoPadrao) {
        this.precoPadrao = precoPadrao;
    }

    /**
     * @return the precoReclinavel
     */
    public float getPrecoReclinavel() {
        return precoReclinavel;
    }

    /**
     * @param precoReclinavel the precoReclinavel to set
     */
    public void setPrecoReclinavel(float precoReclinavel) {
        this.precoReclinavel = precoReclinavel;
    }

    /**
     * @return the precoMovel
     */
    public float getPrecoMovel() {
        return precoMovel;
    }

    /**
     * @param precoMovel the precoMovel to set
     */
    public void setPrecoMovel(float precoMovel) {
        this.precoMovel = precoMovel;
    }

    /**
     * @return the precoObeso
     */
    public float getPrecoObeso() {
        return precoObeso;
    }

    /**
     * @param precoObeso the precoObeso to set
     */
    public void setPrecoObeso(float precoObeso) {
        this.precoObeso = precoObeso;
    }

    /**
     * @return the precoCasal
     */
    public float getPrecoCasal() {
        return precoCasal;
    }

    /**
     * @param precoCasal the precoCasal to set
     */
    public void setPrecoCasal(float precoCasal) {
        this.precoCasal = precoCasal;
    }

    /**
     * @return the precoCadeirante
     */
    public float getPrecoCadeirante() {
        return precoCadeirante;
    }

    /**
     * @param precoCadeirante the precoCadeirante to set
     */
    public void setPrecoCadeirante(float precoCadeirante) {
        this.precoCadeirante = precoCadeirante;
    }



}
