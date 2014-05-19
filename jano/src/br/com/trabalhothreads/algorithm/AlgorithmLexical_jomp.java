package br.com.trabalhothreads.algorithm;

import java.util.List;

import jomp.runtime.OMP;
import br.com.trabalhothreads.objects.Texto;
import br.com.trabalhothreads.objects.Texto_jomp;


public class AlgorithmLexical_jomp {

	
	private List textos;
	
	public AlgorithmLexical_jomp(List textos) {
		this.textos =  textos;
	}

	public void start() {
		
		OMP.setNumThreads(textos.size());
		int i;
		Texto_jomp texto;

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class0 __omp_Object0 = new __omp_Class0();
  // shared variables
  __omp_Object0.textos = textos;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object0);
  } catch(Throwable __omp_exception) {
    System.err.println("OMP Warning: Illegal thread exception ignored!");
    System.err.println(__omp_exception);
  }
  // reduction variables
  // shared variables
  textos = __omp_Object0.textos;
}
// OMP PARALLEL BLOCK ENDS

	}

// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private class __omp_Class0 extends jomp.runtime.BusyTask {
  // shared variables
  List textos;
  // firstprivate variables
  // variables to hold results of reduction

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  int i;
  Texto_jomp texto = new Texto_jomp();
  // reduction variables, init to default
    // OMP USER CODE BEGINS

		{
			i = OMP.getThreadNum();
			texto = (Texto_jomp) textos.get(i);
		    texto.processar();
		    texto.processarFrases();
		    texto.imprimirRelatorio();
		}
    // OMP USER CODE ENDS
  // call reducer
  // output to _rd_ copy
  if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
  }
  }
}
// OMP PARALLEL REGION INNER CLASS DEFINITION ENDS

}

