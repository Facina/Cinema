package com.example.android.cinemusp.modelo;

/**
 * @authos Grupo 4 - Turma B POO
 * Subclasse assento padrao
 */
public class AssentoPadrao extends Assento {

    /**
     * construtor
     */
    public AssentoPadrao() {
        super();

    }

    /**
     * retorna o preco do assento
     * @param preco
     * @param meia
     * @return flaot preco
     */
    public float getPreco(Preco preco, boolean meia) {

        if (meia == false) {
            return preco.getPrecoPadrao();
        } else {
            return preco.getPrecoPadrao() / 2;
        }
    }

    /**
     * retorna o tipo do assento
     * @return int
     */
    public int getTipo() {
        return 1;
    }
}
