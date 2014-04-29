package br.com.trabalhothreads.objects;

import java.util.ArrayList;
import java.util.List;

public class Frases{

	public Frases(String conteudo, int position){
		this.conteudo = conteudo;
		this.status = Status.NAO_AVALIADA;
		this.position = position;
		this.invalidWords = new ArrayList<String>();
	}
	
	private List<String> invalidWords;
	
	private int position;

	private String conteudo;
	
	private Status status;

	public String getConteudo() {
		return conteudo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void addInvalidWord(String word) {
		this.invalidWords.add(word);
	}
	
	public String palavrasInvalidas(){
		String palavrasInvalidas = "";
		for (String word : this.invalidWords){
			palavrasInvalidas += word;
		}
		
		return palavrasInvalidas;
	}

	public int getPosition() {
		return position;
	}

}
