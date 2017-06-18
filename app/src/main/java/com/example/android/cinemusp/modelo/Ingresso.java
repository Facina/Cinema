package com.example.android.cinemusp.modelo;

public class Ingresso {



	Assento assento;
	float preco;

	public float getPreco2() {
		return preco;
	}
	public Assento getAssento() {
		return assento;
	}
	
	public Ingresso(Assento assento, Preco precos, boolean meia) {
		this.assento = assento;
		this.preco = assento.getPreco(precos, meia);
	}
}
