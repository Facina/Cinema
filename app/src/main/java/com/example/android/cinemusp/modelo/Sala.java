package com.example.android.cinemusp.modelo;

import com.example.android.cinemusp.persistencia.CinemaException;


public class Sala {

    private int idSala, numero;
    private int nFileiras, maxAssentos;
    private Assento[][] mapa;

    public Sala() {
    }

    public Sala(int numero, int nFileiras, int maxAssentos) {
        this.nFileiras = nFileiras;
        this.maxAssentos = maxAssentos;
        mapa = new Assento[nFileiras][maxAssentos];
        this.numero = numero;
    }

    public Sala(int numero, int nFileiras, int maxAssentos, Assento[][] mapa) {
        this.nFileiras = nFileiras;
        this.maxAssentos = maxAssentos;
        this.mapa = mapa;
        this.numero = numero;
    }

    public void setNumeroSala(int numero) {
        this.numero = numero;
    }

    public void setAssentos(Assento[][] mapaAssentos) throws CinemaException {
        if (mapaAssentos == null) {
            throw new CinemaException("Preencha o mapa de assentos");
        }

        mapa = mapaAssentos;
    }

    public Assento[][] getAssentos() {
        return mapa;
    }

    public void setAssento(int tipoAssento, int coordX, int coordY) {
        if (coordX - 1 >= 0 && mapa[coordX][coordY] instanceof AssentoObeso) {
            return;
        }
        // lança exceção
        switch (tipoAssento) {
            case 1:
                mapa[coordX][coordY] = new AssentoPadrao();
                break;
            case 2:
                mapa[coordX][coordY] = new AssentoReclinavel();
                break;
            case 6:
                mapa[coordX][coordY] = new AssentoMovel();
                break;
            case 3:
                mapa[coordX][coordY] = new AssentoCadeirante();
                break;
            case 5:
                if (coordX == maxAssentos - 1) {
                    break;
                }
                // lança exceção
                mapa[coordX][coordY] = new AssentoObeso();
                break;
            case 4:
                if (coordX == maxAssentos - 1) {
                    break;
                }
                // lança exceção
                mapa[coordX][coordY] = new AssentoCasal();
                mapa[coordX + 1][coordY] = new AssentoCasal();
                break;
            default:
            // lança exceção
        }
    }

    public void setNumerosAssentos() {
        int i, j, k;
        for (i = 0; i < nFileiras; i++) {
            for (j = 0, k = 1; j < maxAssentos; j++) {
                if (mapa[i][j] != null) {
                    mapa[i][j].setNumero(k++);
                }
            }
        }
    }

    public Assento getAssento(int coordX, int coordY) {
        return mapa[coordX][coordY];
    }

    public Assento[][] getMapa() {
        return mapa;
    }

    public int getNumeroSala() {
        return numero;
    }

    public int getNFileiras() {
        return nFileiras;
    }

    public void setNFileiras(int nFileiras) throws CinemaException {
        if (nFileiras <= 0) {
            throw new CinemaException("Número de fileiras inválido");
        }

        this.nFileiras = nFileiras;
    }

    public int getMaxAssentos() {
        return maxAssentos;
    }

    public void setMaxAssentos(int maxAssentos) throws CinemaException {
        if (maxAssentos <= 0) {
            throw new CinemaException("Número de assentos por fileira inválido");
        }

        this.maxAssentos = maxAssentos;
    }

    public void setNumeroSala(String stringNumero) throws CinemaException {
        int num;

        if (stringNumero.equals("")) {
            throw new CinemaException("Preencha o campo de número da sala");
        }

        try {
            num = Integer.parseInt(stringNumero);
        } catch (NumberFormatException e) {
            throw new CinemaException("Número da sala inválido");
        }

        setNumeroSala(num);

    }

    /**
     * @return the idSala
     */
    public int getIdSala() {
        return idSala;
    }

    /**
     * @param idSala the idSala to set
     */
    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }


}
