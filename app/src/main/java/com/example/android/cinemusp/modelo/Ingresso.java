package com.example.android.cinemusp.modelo;

/**
 * Classe de ingresso
 */
public class Ingresso {



	Assento assento;
	float preco;

	public int getX() {
		return x;
	}


	public int getY() {
		return y;
	}


	int x;
	int y;

	public float getPreco2() {
		return preco;
	}
	public Assento getAssento() {
		return assento;
	}

	/**
	 * Construtor
	 * @param assento
	 * @param precos
	 * @param meia
	 * @param x fileira do ingresso
	 * @param y coluna do ingresso
	 */
	public Ingresso(Assento assento, Preco precos, boolean meia,int x,int y) {
		this.assento = assento;
		this.preco = assento.getPreco(precos, meia);
		this.x = x;
		this.y = y;
	}

}
