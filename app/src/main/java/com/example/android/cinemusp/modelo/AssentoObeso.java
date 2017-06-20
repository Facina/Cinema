package com.example.android.cinemusp.modelo;

/**
 * Subclasse AssentoObesso
 */
public class AssentoObeso extends Assento {

    /**
     * construtor
     */
    public AssentoObeso() {
        super();
    }

    /**
     * retorna o preco do assento
     * @param preco
     * @param meia
     * @return float preco
     */
    public float getPreco(Preco preco, boolean meia) {

        if (meia == false) {
            return preco.getPrecoObeso();
        } else {
            return preco.getPrecoObeso() / 2;
        }
    }

    /**
     * retorna o tipo do assento
     * @return
     */
    public int getTipo() {
        return 5;
    }
}
