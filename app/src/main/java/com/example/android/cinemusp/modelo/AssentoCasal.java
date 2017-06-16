package com.example.android.cinemusp.modelo;

public class AssentoCasal extends Assento {

    public AssentoCasal() {
        super();
        this.setImgL("/Users/marcelosuckowdebarrosrodrigues/Downloads/USP/Cinema/src/icons/Delete16.png");
        this.setImgR("/Users/marcelosuckowdebarrosrodrigues/Downloads/USP/Cinema/src/icons/Delete-icon.png");
    }

    public int getEspacoOcupado() {
        return 2;
    }

    public float getPreco(Preco preco, boolean meia) {

        if (meia == false) {
            return preco.getPrecoCasal();
        } else {
            return preco.getPrecoCasal() / 2;
        }
    }

    public int getTipo() {
        return 4;
    }
}
