package br.com.trabalhothreads.threads;

import br.com.trabalhothreads.objects.Texto;


public class ThreadGerarRelatorioText implements Runnable{

	private Texto texto;
	
	public ThreadGerarRelatorioText(Texto texto){
		this.texto = texto;				
	}
	
	@Override
	public void run(){
		this.texto.imprimirRelatorio();
	}

}
