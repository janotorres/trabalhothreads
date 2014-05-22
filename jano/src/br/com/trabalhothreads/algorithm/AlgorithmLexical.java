package br.com.trabalhothreads.algorithm;

import java.util.List;

import jomp.runtime.OMP;
import br.com.trabalhothreads.objects.Texto;
import br.com.trabalhothreads.objects.Texto_jomp;

public class AlgorithmLexical {

	private List textos;

	public AlgorithmLexical(List textos) {
		this.textos = textos;
	}

	public void start() {

		OMP.setNumThreads(textos.size());
		int i;
		Texto_jomp texto;
		// omp parallel private(i, texto)
		{
			i = OMP.getThreadNum();
			texto = (Texto_jomp) textos.get(i);
			texto.processar();
		}

		for (int j = 0; j < textos.size(); j++) {
			texto = (Texto_jomp) textos.get(j);
			texto.processarFrases();
		}
		

		OMP.setNumThreads(textos.size());
		// omp parallel for private(j, texto)
		for (int j = 0; j < textos.size(); j++) {
			texto = (Texto_jomp) textos.get(j);
			texto.imprimirRelatorio();
		}
	}

}
