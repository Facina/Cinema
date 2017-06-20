package com.example.android.cinemusp.modelo;

/**
 * @author Grupo 4 - Turma B POO
 * Subclasse Assento movel
 */
public class AssentoMovel extends Assento {

    /** Construtor
     *
     */
    public AssentoMovel() {
        super();

    }


    /**
     * retorna o  preoc do assento
     * @param preco
     * @param meia
     * @return float preco
     */
    public float getPreco(Preco preco, boolean meia) {

        if (meia == false) {
            return preco.getPrecoMovel();
        } else {
            return preco.getPrecoMovel() / 2;
        }
    }

    /**
     * retorna o tiop do assento
     * @return int 1-6
     */
    public int getTipo() {
        return 6;
    }
}
