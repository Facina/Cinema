package com.example.android.cinemusp.modelo;
// DEPENDE DE MUITAS DECIS??ES POSTERIORES AINDA

public class CompraIngresso {
	
	Ingresso[] ingressos;
	int nIngressos;
	Sessao sessao;
	
	public CompraIngresso(int nIngressos, Sessao sessao) {
		this.nIngressos = nIngressos;
		this.sessao = sessao;
		ingressos = new Ingresso[nIngressos];
	}
	
	public void compra() {
		
	}
}
