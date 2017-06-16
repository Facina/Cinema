package com.example.android.cinemusp.modelo;

public class AssentoMovel extends Assento {

    public AssentoMovel() {
        super();
        this.setImgL("/Users/marcelosuckowdebarrosrodrigues/Downloads/USP/Cinema/src/icons/Edit 16.png");
        this.setImgR(null);
    }

    public int getEspacoOcupado() {
        return 1;
    }

    public float getPreco(Preco preco, boolean meia) {

        if (meia == false) {
            return preco.getPrecoMovel();
        } else {
            return preco.getPrecoMovel() / 2;
        }
    }
    
    public int getTipo() {
        return 6;
    }
}
