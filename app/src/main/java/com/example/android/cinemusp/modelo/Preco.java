package com.example.android.cinemusp.modelo;

import com.example.android.cinemusp.persistencia.CinemaException;

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
    public int getIdPreco() {
        return idPreco;
    }

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

    public void setPrecoPadrao(String valor, boolean enabled) throws CinemaException {
        float preco;

        if (valor.equals("")) {
            if (enabled == false) {
                this.precoPadrao = 0;
                return;
            } else {
                throw new CinemaException("Preencha o preço do Assento Padrão");
            }
        }

        try {
            preco = Float.parseFloat(valor);
        } catch (NumberFormatException ex) {
            throw new CinemaException("Valor para preço de assento padrão inválido");
        }

        this.precoPadrao = preco;
    }

    public void setPrecoReclinavel(String valor, boolean enabled) throws CinemaException {
        float preco;

        if (valor.equals("")) {
            if (enabled == false) {
                this.precoReclinavel = 0;
                return;
            } else {
                throw new CinemaException("Preencha o preço do Assento Reclinavel");
            }
        }

        try {
            preco = Float.parseFloat(valor);
        } catch (NumberFormatException ex) {
            throw new CinemaException("Valor para preço de assento reclinavel inválido");
        }

        this.precoReclinavel = preco;
    }

    public void setPrecoCadeirante(String valor, boolean enabled) throws CinemaException {
        float preco;

        if (valor.equals("")) {
            if (enabled == false) {
                this.precoCadeirante = 0;
                return;
            } else {
                throw new CinemaException("Preencha o preço do Assento de Cadeirante");
            }
        }

        try {
            preco = Float.parseFloat(valor);
        } catch (NumberFormatException ex) {
            throw new CinemaException("Valor para preço de assento de cadeirante inválido");
        }

        this.precoCadeirante = preco;
    }
    
    public void setPrecoCasal(String valor, boolean enabled) throws CinemaException {
        float preco;

        if (valor.equals("")) {
            if (enabled == false) {
                this.precoCasal = 0;
                return;
            } else {
                throw new CinemaException("Preencha o preço do Assento de Casal");
            }
        }

        try {
            preco = Float.parseFloat(valor);
        } catch (NumberFormatException ex) {
            throw new CinemaException("Valor para preço de assento de casal inválido");
        }

        this.precoCasal = preco;
    }
    
    public void setPrecoObeso(String valor, boolean enabled) throws CinemaException {
        float preco;

        if (valor.equals("")) {
            if (enabled == false) {
                this.precoObeso = 0;
                return;
            } else {
                throw new CinemaException("Preencha o preço do Assento de Obeso");
            }
        }

        try {
            preco = Float.parseFloat(valor);
        } catch (NumberFormatException ex) {
            throw new CinemaException("Valor para preço de assento de obeso inválido");
        }

        this.precoObeso = preco;
    }
    
    public void setPrecoMovel(String valor, boolean enabled) throws CinemaException {
        float preco;

        if (valor.equals("")) {
            if (enabled == false) {
                this.precoMovel = 0;
                return;
            } else {
                throw new CinemaException("Preencha o preço do Assento Móvel");
            }
        }

        try {
            preco = Float.parseFloat(valor);
        } catch (NumberFormatException ex) {
            throw new CinemaException("Valor para preço de assento móvel inválido");
        }

        this.precoMovel = preco;
    }
}
