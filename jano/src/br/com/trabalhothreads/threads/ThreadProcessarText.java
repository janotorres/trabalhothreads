package br.com.trabalhothreads.threads;

import br.com.trabalhothreads.objects.Texto;


public class ThreadProcessarText implements Runnable{

	private Texto texto;

	public ThreadProcessarText(Texto texto){
		this.texto = texto;
				
	}
	
	@Override
	public void run() {
		this.texto.processar();
	}

}
