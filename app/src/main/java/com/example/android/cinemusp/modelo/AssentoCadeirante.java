package com.example.android.cinemusp.modelo;

public class AssentoCadeirante extends Assento {

    public AssentoCadeirante() {
        super();
        this.setImgL("/Users/marcelosuckowdebarrosrodrigues/Downloads/USP/Cinema/src/icons/Add-icon.png");
        this.setImgR(null);
    }

    public int getEspacoOcupado() {
        return 1;
    }

    public float getPreco(Preco preco, boolean meia) {

        if (meia == false) {
            return preco.getPrecoCadeirante();
        } else {
            return preco.getPrecoCadeirante()/2;
        }
    }

    public int getTipo() {
        return 3;
    }

}
