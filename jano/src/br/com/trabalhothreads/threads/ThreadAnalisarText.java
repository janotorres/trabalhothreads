package br.com.trabalhothreads.threads;

import br.com.trabalhothreads.objects.Texto;


public class ThreadAnalisarText implements Runnable {

	private Texto texto;

	public ThreadAnalisarText(Texto texto) {
		this.texto = texto;
	}

	@Override
	public void run() {
		texto.processarFrases();
	}
	
}
