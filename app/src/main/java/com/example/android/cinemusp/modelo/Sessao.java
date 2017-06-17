package com.example.android.cinemusp.modelo;

import com.example.android.cinemusp.persistencia.CinemaException;


import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;



public class Sessao {
    public String getHorarioString() {
        return horarioString;
    }

    public void setHorarioString(String horarioString) {
        this.horarioString = horarioString;
    }

    private String horarioString;

        private int idSessao;
	private Filme filme;
	private Sala sala;
	private Date data;
        private Time horario;
	private Preco precos;
	private boolean tresD;
        private boolean legendado;
        private boolean imax;
        private boolean quatroK;
        private boolean lotada;
        private Assento[][] mapa;
	
        public Sessao(){
            
        }
        
	public Sessao(Filme filme, Sala sala,Date data ,Time horario, Preco precos, boolean tresD,boolean legendado, boolean imax, boolean quatroK) {
		this.filme = filme;

		this.sala = new Sala(sala.getNumeroSala(), sala.getNFileiras(), sala.getMaxAssentos(), sala.getMapa());
		this.horario = horario;
                this.data = data;
		this.precos = precos;
		this.tresD = tresD;
                this.quatroK = quatroK;
                this.imax = imax;
                this.legendado = legendado;
                lotada = false;
                mapa = sala.getMapa();
	}

    /**
     * @return the idSessao
     */
    public int getIdSessao() {
        return idSessao;
    }

    /**
     * @param idSessao the idSessao to set
     */
    public void setIdSessao(int idSessao) {
        this.idSessao = idSessao;
    }

    /**
     * @return the filme
     */
    public Filme getFilme() {
        return filme;
    }

    /**
     * @param filme the filme to set
     */
    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    /**
     * @return the sala
     */
    public Sala getSala() {
        return sala;
    }

    /**
     * @param sala the sala to set
     */
    public void setSala(Sala sala) {
        this.sala = sala;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * @return the horario
     */
    public Time getHorario() {
        return horario;
    }

    /**
     * @param horario the horario to set
     */
    public void setHorario(Time horario) {
        this.horario = horario;
    }

    /**
     * @return the precos
     */
    public Preco getPrecos() {
        return precos;
    }

    /**
     * @param precos the precos to set
     */
    public void setPrecos(Preco precos) {
        this.precos = precos;
    }

    /**
     * @return the tresD
     */
    public boolean isTresD() {
        return tresD;
    }

    /**
     * @param tresD the tresD to set
     */
    public void setTresD(boolean tresD) {
        this.tresD = tresD;
    }

    /**
     * @return the legendado
     */
    public boolean isLegendado() {
        return legendado;
    }

    /**
     * @param legendado the legendado to set
     */
    public void setLegendado(boolean legendado) {
        this.legendado = legendado;
    }

    /**
     * @return the imax
     */
    public boolean isImax() {
        return imax;
    }

    /**
     * @param imax the imax to set
     */
    public void setImax(boolean imax) {
        this.imax = imax;
    }

    /**
     * @return the quatroK
     */
    public boolean isQuatroK() {
        return quatroK;
    }

    /**
     * @param quatroK the quatroK to set
     */
    public void setQuatroK(boolean quatroK) {
        this.quatroK = quatroK;
    }

    /**
     * @return the lotada
     */
    public boolean isLotada() {
        return lotada;
    }

    /**
     * @param lotada the lotada to set
     */
    public void setLotada(boolean lotada) {
        this.lotada = lotada;
    }

    /**
     * @return the mapa
     */
    public Assento[][] getMapa() {
        return mapa;
    }

    /**
     * @param mapa the mapa to set
     */
    public void setMapa(Assento[][] mapa) {
        this.mapa = mapa;
    }

    public void setHorario(String horario) throws CinemaException {
        int horas,minutos;
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Time hora = null;
        
        if(horario.equals("  :  ")){
            throw new CinemaException ("Preencha o horário das sessões");
        }
        
        horas = Integer.parseInt(horario.split(":")[0]);
        minutos = Integer.parseInt(horario.split(":")[1]);
        
        if(horas < 0 || horas > 23 || minutos < 0 || minutos > 59){
            throw new CinemaException("Horário Inválido!");
        }
        
        try {
            hora = new Time(formatter.parse(horario).getTime());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        
        this.horario = hora;
    }

	
}
