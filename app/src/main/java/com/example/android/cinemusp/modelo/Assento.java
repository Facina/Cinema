package com.example.android.cinemusp.modelo;


/**
 * @author Grupo 4 - Turma B POO
 * Classe abstrada de assentos
 */
public abstract class Assento {

    @Override
    public String toString() {
        return "" + idAssento;
    }

    private int idAssento;


    private boolean status;

    /**
     * construtor de assento
     */

    public Assento() {
        status = false;
    }


    /**
     * Classe abstrata que retorna o tipo do assento de 1-6
     * @return
     */
    public abstract int getTipo();

    public abstract float getPreco(Preco preco, boolean meia);

    /**
     * retorna se o assento ja foi comprada ou não
     * @return the status
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }


    /**
     * @return the idAssento
     */
    public int getIdAssento() {
        return idAssento;
    }

    /**
     * @param idAssento the idAssento to set
     */
    public void setIdAssento(int idAssento) {
        this.idAssento = idAssento;
    }


}
