package br.com.trabalhothreads.objects;

public class Lexical {

	private static Lexical instance;

	private Lexical(){
		super();
	}
	public static Lexical getInstance() {
		if (instance == null)
			instance = new Lexical();
		return instance;
	}

	public boolean validateWord(String word) {
		return false;
	}

}
