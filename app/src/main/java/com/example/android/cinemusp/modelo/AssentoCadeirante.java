package com.example.android.cinemusp.modelo;

/**
 * @author Grupo 4 - Turma B POO
 * Subclasse assento cadeirante
 */
public class AssentoCadeirante extends Assento {

    /**
     * construtor
     */
    public AssentoCadeirante() {
        super();
    }


    /**
     * metodo que retorna o preco do assento
     * @param preco
     * @param meia
     * @return
     */
    public float getPreco(Preco preco, boolean meia) {

        if (meia == false) {
            return preco.getPrecoCadeirante();
        } else {
            return preco.getPrecoCadeirante()/2;
        }
    }

    /**
     * retorna o tipo do assento
     * @return
     */
    public int getTipo() {
        return 3;
    }

}
