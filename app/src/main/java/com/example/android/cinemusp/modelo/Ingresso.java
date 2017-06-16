package com.example.android.cinemusp.modelo;

public class Ingresso {
	
	Assento assento;
	float preco;
	
	public Ingresso(Assento assento, Preco precos, boolean meia) {
		this.assento = assento;
		this.preco = assento.getPreco(precos, meia);
	}
}
