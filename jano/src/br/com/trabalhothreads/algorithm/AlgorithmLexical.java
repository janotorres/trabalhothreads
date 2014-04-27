package br.com.trabalhothreads.algorithm;

import java.util.List;

import br.com.trabalhothreads.objects.Texto;
import br.com.trabalhothreads.threads.ThreadAnalisarText;
import br.com.trabalhothreads.threads.ThreadProcessarText;


public class AlgorithmLexical {

	private List<Texto> textos;
	
	public AlgorithmLexical(List<Texto> textos, Integer quantidadeArquivosSimultaneos) {
		this.textos =  textos;
	}

	public void start() {
		for (int i = 0; i < textos.size(); i++) {
			ThreadProcessarText threadProcessarText = new ThreadProcessarText(textos.get(i));
			ThreadAnalisarText  threadAnalisarText = new ThreadAnalisarText(textos.get(i));	
			new Thread(threadProcessarText).start();
			new Thread(threadAnalisarText).start();
		}
	}
	
	
	

}
