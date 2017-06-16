package com.example.android.cinemusp.modelo;

public class AssentoPadrao extends Assento {

    public AssentoPadrao() {
        super();
        this.setImgL("/Users/marcelosuckowdebarrosrodrigues/Downloads/USP/Cinema/src/icons/Sala16.png");
        this.setImgR(null);
    }

    public int getEspacoOcupado() {
        return 1;
    }

    public float getPreco(Preco preco, boolean meia) {

        if (meia == false) {
            return preco.getPrecoPadrao();
        } else {
            return preco.getPrecoPadrao() / 2;
        }
    }
    
    public int getTipo() {
        return 1;
    }
}
