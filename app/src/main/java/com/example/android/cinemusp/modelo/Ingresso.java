package com.example.android.cinemusp.modelo;

public class Ingresso {



	Assento assento;
	float preco;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	int x;
	int y;

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
	public Ingresso(Assento assento, Preco precos, boolean meia,int x,int y) {
		this.assento = assento;
		this.preco = assento.getPreco(precos, meia);
		this.x = x;
		this.y = y;
	}

}
