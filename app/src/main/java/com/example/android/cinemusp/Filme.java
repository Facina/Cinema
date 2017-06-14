package com.example.android.cinemusp;

import java.sql.Date;
import java.text.SimpleDateFormat;



/**
 *
 * @author marcelosuckowdebarrosrodrigues
 */
public class Filme {

    private String nome, sinopse, imgLink, classificacao;
    private Date dataEstreia, dataSaida;
    private int idFilme, duracao;

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) throws CinemaException {

        if (nome.length() == 0) {
            throw new CinemaException("Campo nome não pode ficar vazio");
        }

        if (nome.length() > 80) {
            throw new CinemaException("Nome deve ter no máximo 80 caracteres");
        }


        this.nome = nome;

    }

    /**
     * @return the imgLink
     */
    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) throws CinemaException {
        if (imgLink.length() > 1024) {
            throw new CinemaException("Link deve ter no máximo 1024 caracteres");
        }

        this.imgLink = imgLink;
    }

    /**
     * @return the sinopse
     */
    public String getSinopse() {
        return sinopse;
    }

    /**
     *
     * @param sinopse
     */
    public void setSinopse(String sinopse) throws CinemaException {
        if (sinopse.length() > 1024) {
            throw new CinemaException("Sinopse deve ter no máximo 1024 caracteres");
        }

        this.sinopse = sinopse;
    }

    /**
     * @return the dataEstreia
     */
    public Date getDataEstreia() {
        return dataEstreia;
    }

    /**
     * @param dataEstreia the dataEstreia to set
     */
    public void setDataEstreia(Date dataEstreia) {
        this.dataEstreia = dataEstreia;
    }

    /**
     * @return the dataSaida
     */
    public Date getDataSaida() {
        return dataSaida;
    }

    /**
     * @param dataSaida the dataSaida to set
     */
    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    /**
     * @return the idFilme
     */
    public int getidFilme() {
        return getIdFilme();
    }

    /**
     * @param idFilme the idFilme to set
     */
    public void setidFilme(int idFilme) {
        this.setIdFilme(idFilme);
    }

    /**
     * @return the classificacao
     */
    public String getClassificacao() {
        return classificacao;
    }

    /**
     * @param classificacao the classificacao to set
     */
    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    /**
     * @return the duracao
     */
    public int getDuracao() {
        return duracao;
    }

    /**
     * @param duracao the duracao to set
     */
    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    /**
     * @param duracao the duracao to set
     */
    public void setDuracao(String duracao) throws CinemaException {
        int a;

        if (duracao.length() == 0) {
            throw new CinemaException("Duração inválida. O campo está vazio");
        }

        try {
            a = Integer.parseInt(duracao);
        } catch (NumberFormatException e) {
            throw new CinemaException("Duração Inválida! A duração deve ser um número inteiro.");
        }

        if (a <= 0) {
            throw new CinemaException("Duração inválida. A duração deve ser maior que zero");
        } else {
            this.setDuracao(a);
        }

    }

    public void setDataEstreia(String dataEstreia) throws CinemaException {
        SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
        Date data;

        if (dataEstreia.equals("  /  /    ")) {
            throw new CinemaException("Campo Data de Estreia não pode ser vazio");
        }

        checarData(dataEstreia, "estreia");

        String dt[] = dataEstreia.split("/");
        data = new java.sql.Date(Integer.parseInt(dt[2]) - 1900, Integer.parseInt(dt[1]) - 1, Integer.parseInt(dt[0]));
        setDataEstreia(data);

    }

    public void setDataSaida(String dataSaida) throws CinemaException {
        SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
        Date data;

        if (dataSaida.equals("  /  /    ")) {
            return;
        }

        checarData(dataSaida, "saida");

        String dt[] = dataSaida.split("/");
        data = new java.sql.Date(Integer.parseInt(dt[2]) - 1900, Integer.parseInt(dt[1]) - 1, Integer.parseInt(dt[0]));
        if(data.before(dataEstreia)){
            throw new CinemaException("Data de estreia está depois da data de saída");
        }
        setDataSaida(data);
    }

    private void checarData(String dataEstreia, String id) throws CinemaException {
        int ano = 0, mes = 0, dia = 0, limite = 0;

        for (int i = 3; i >= 0; i--) {
            ano += (int) (Math.pow(10, i) * (Character.getNumericValue(dataEstreia.charAt(9 - i))));

            if (i < 2) {
                mes += (int) (Math.pow(10, i) * (Character.getNumericValue(dataEstreia.charAt(4 - i))));
                dia += (int) (Math.pow(10, i) * (Character.getNumericValue(dataEstreia.charAt(1 - i))));
            }
        }

        if (ano > 2500 || ano < 1900) {
            throw new CinemaException("Ano de " + id + " inválido" + ano);
        }
        if (mes < 1 || mes > 12) {
            throw new CinemaException("Mês de " + id + " inválido");
        }
        if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
            limite = 31;
        } else if (mes != 2) {
            limite = 30;
        } else if (ano % 4 != 0 || ano % 100 == 0) {
            limite = 28;
        } else {
            limite = 29;
        }

        if (dia < 1 || dia > limite) {
            throw new CinemaException("Data de " + id + " inválida");
        }
    }
    /*
    public static ArrayList<Filme> pesquisarFilmes(String texto, int selectedIndex) throws CinemaException {
        ControleFilmes cf = new ControleFilmes();
        ArrayList<Filme> lista = null;

        if (texto.equals("") && selectedIndex == 0) {
            lista = cf.pesquisarFilmes(null, 0); //pesquisar todos os filmes
        } else if (selectedIndex == 0) {
            lista = cf.pesquisarFilmes(texto, 1); //pesquisar por nome
        } else if(texto.equals("")){
            lista = cf.pesquisarFilmes(null, 2); //pesquisar todos filmes em cartaz
        }else{
            lista = cf.pesquisarFilmes(texto, 3); //pesquisar filmes em cartaz por nome
        }


        return lista;

    }*/

    /**
     * @return the idFilme
     */
    public int getIdFilme() {
        return idFilme;
    }

    /**
     * @param idFilme the idFilme to set
     */
    public void setIdFilme(int idFilme) {
        this.idFilme = idFilme;
    }
}
