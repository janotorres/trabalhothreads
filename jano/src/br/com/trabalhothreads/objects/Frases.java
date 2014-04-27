package br.com.trabalhothreads.objects;

public class Frases {

	public Frases(String conteudo){
		this.conteudo = conteudo;
		this.status = Status.NAO_AVALIADA;
		
	}
	private String conteudo;
	
	private Status status;

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void addInvalidWord(String word) {
		// TODO Auto-generated method stub
		
	}
	
}
