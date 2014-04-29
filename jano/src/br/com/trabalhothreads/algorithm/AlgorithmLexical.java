package br.com.trabalhothreads.algorithm;

import java.util.List;
import java.util.concurrent.Semaphore;

import br.com.trabalhothreads.objects.Texto;
import br.com.trabalhothreads.threads.ThreadAnalisarText;
import br.com.trabalhothreads.threads.ThreadGerarRelatorioText;
import br.com.trabalhothreads.threads.ThreadProcessarText;


public class AlgorithmLexical {

	private List<Texto> textos;
	
	private Semaphore semaphore;
	
	public AlgorithmLexical(List<Texto> textos, Integer quantidadeArquivosSimultaneos) {
		this.textos =  textos;
		this.semaphore = new Semaphore(quantidadeArquivosSimultaneos , true);
	}

	public void start() {
		for (int i = 0; i < textos.size(); i++) {
			ThreadProcessarText threadProcessarText = new ThreadProcessarText(textos.get(i), this.semaphore);
			ThreadAnalisarText  threadAnalisarText = new ThreadAnalisarText(textos.get(i));				
			ThreadGerarRelatorioText threadGerarRelatorioText = new ThreadGerarRelatorioText(textos.get(i));	

			
			new Thread(threadProcessarText).start();
			new Thread(threadAnalisarText).start();
			new Thread(threadGerarRelatorioText).start();

		}
	}
	
	
	
	

}
