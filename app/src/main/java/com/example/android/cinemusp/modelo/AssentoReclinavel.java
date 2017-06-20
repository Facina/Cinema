package com.example.android.cinemusp.modelo;

/**
 * Subclasse AssentoReclinavel
 * @author Grupo 4 - Turma B POO
 */
public class AssentoReclinavel extends Assento {
    /**
     * Construtor
     */
	public AssentoReclinavel() {
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
            return preco.getPrecoReclinavel();
        } else {
            return preco.getPrecoReclinavel() / 2;
        }
    }

    /**
     * retorna o tipo do assento
     * @return
     */
     public int getTipo() {
        return 2;
    }
}
