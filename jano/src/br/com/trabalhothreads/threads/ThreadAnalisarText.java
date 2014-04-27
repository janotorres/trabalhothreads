package br.com.trabalhothreads.threads;

import br.com.trabalhothreads.objects.Frases;
import br.com.trabalhothreads.objects.Lexical;
import br.com.trabalhothreads.objects.Status;
import br.com.trabalhothreads.objects.Texto;


public class ThreadAnalisarText implements Runnable {

	private Texto texto;

	public ThreadAnalisarText(Texto texto) {
		this.texto = texto;
	}

	@Override
	public void run() {
		for (int i = 0; i < texto.getFrases().size(); i++) {
			ThreadAnalisarPhrase threadAnalisarPhrase = new ThreadAnalisarPhrase(texto.getFrases().get(0));
			new  Thread(threadAnalisarPhrase).start();
		}
		
	}
	
	public class ThreadAnalisarPhrase implements Runnable {
		private Frases frase;

		public ThreadAnalisarPhrase(Frases  frase){
			this.frase = frase;			
		}

		@Override
		public void run() {
			String[] words = this.frase.getConteudo().split(" ");
			for (int i = 0; i < words.length; i++) {
				String word = words[i];
				Lexical lexical = Lexical.getInstance();
				boolean validWord = lexical.validateWord(word);
				if (!validWord){
					frase.addInvalidWord(word);
					frase.setStatus(Status.INVALIDA);
				}
			}
			
			if (frase.getStatus().equals(Status.INVALIDA)){
				frase.setStatus(Status.VALIDA);
			}
		}
	
		
	}

}
