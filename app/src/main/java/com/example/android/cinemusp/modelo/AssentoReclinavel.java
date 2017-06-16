package com.example.android.cinemusp.modelo;

public class AssentoReclinavel extends Assento {
	
	public AssentoReclinavel() {
		super();
                this.setImgL("/Users/marcelosuckowdebarrosrodrigues/Downloads/USP/Cinema/src/icons/Search16.png");
                this.setImgR(null);
	}
	
	public int getEspacoOcupado() {
		return 1;
	}
        
     public float getPreco(Preco preco, boolean meia) {

        if (meia == false) {
            return preco.getPrecoReclinavel();
        } else {
            return preco.getPrecoReclinavel() / 2;
        }
    }
     
     public int getTipo() {
        return 2;
    }
}
