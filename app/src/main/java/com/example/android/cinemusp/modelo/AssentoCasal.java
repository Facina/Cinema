package com.example.android.cinemusp.modelo;

/**
 * @author Grupo 4 - Turma B POO
 * Subclasse assento de casal
 */
public class AssentoCasal extends Assento {

    /**
     * Construtor
     */
    public AssentoCasal() {
        super();
    }

    /**
     * retorna o preco do ingresso
     * @param preco
     * @param meia
     * @return um float que é o preco do ingresso
     */
    public float getPreco(Preco preco, boolean meia) {

        if (meia == false) {
            return preco.getPrecoCasal();
        } else {
            return preco.getPrecoCasal() / 2;
        }
    }

    /**
     * retorna o tipo de assento
     * @return int
     */
    public int getTipo() {
        return 4;
    }
}
