package com.example.android.cinemusp.modelo;

public class AssentoObeso extends Assento {

    public AssentoObeso() {
        super();
        this.setImgL("/Users/marcelosuckowdebarrosrodrigues/Downloads/USP/Cinema/src/icons/filme_16.png");
        this.setImgR("/Users/marcelosuckowdebarrosrodrigues/Downloads/USP/Cinema/src/icons/filme_24.png");
    }

    public int getEspacoOcupado() {
        return 2;
    }

    public float getPreco(Preco preco, boolean meia) {

        if (meia == false) {
            return preco.getPrecoObeso();
        } else {
            return preco.getPrecoObeso() / 2;
        }
    }
    
    public int getTipo() {
        return 5;
    }
}
