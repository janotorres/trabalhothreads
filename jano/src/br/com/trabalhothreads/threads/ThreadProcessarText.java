package br.com.trabalhothreads.threads;

import java.util.concurrent.Semaphore;

import br.com.trabalhothreads.objects.Texto;


public class ThreadProcessarText implements Runnable{

	private Texto texto;
	private Semaphore semaphore;

	public ThreadProcessarText(Texto texto, Semaphore semaphore){
		this.texto = texto;
		this.semaphore = semaphore;
				
	}
	
	@Override
	public void run() {
		this.texto.processar(this.semaphore);
	}

}
