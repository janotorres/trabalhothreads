package br.com.trabalhothreads.objects;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import jomp.runtime.OMP;

public class Texto_jomp {


	private List frases;

	private List frasesValidas;

	private List frasesInvalidas;

	private File file;

	public Texto_jomp() {
		super();
	}

	public Texto_jomp(File file) {
		this.frases = new ArrayList();
		this.frasesInvalidas = new ArrayList();
		this.frasesValidas = new ArrayList();
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void processar() {
		BufferedReader reader = null;
		try {
			System.out.println("Tentando iniciar processamento do  arquivo : "
					+ this.getFile().getName());
			System.out.println("Iniciando processamento do  arquivo : "
					+ this.getFile().getName());
			reader = new BufferedReader(new FileReader(this.getFile()));
			String line = null;
			StringBuilder stringBuilder = new StringBuilder();
			String ls = System.getProperty("line.separator");

			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}

			String texto = stringBuilder.toString();
			String[] frases = texto.split("\\.");
			for (int i = 0; i < frases.length; i++) {
				this.frases.add(new Frases(frases[i], i));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			System.out.println("Finalizando processamento do  arquivo : "
					+ this.getFile().getName());
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (Exception e1) {
				throw new RuntimeException(e1);
			}
		}
	}

	public void processarFrases() {
		try {
			System.out
					.println("Iniciando processamento das Frases do  arquivo : "
							+ this.getFile().getName());
			while (!this.frases.isEmpty()) {
				Frases frase = (Frases) this.frases.get(0);
				String conteudo = frase.getConteudo();
				if (conteudo == null || "".equals(conteudo.trim())) {
					this.frases.remove(frase);
					continue;
				}
				String[] words = conteudo.split("\\s+");

				boolean fraseValida = true;
				OMP.setNumThreads(words.length);

// OMP PARALLEL BLOCK BEGINS
{
  __omp_Class0 __omp_Object0 = new __omp_Class0();
  // shared variables
  __omp_Object0.fraseValida = fraseValida;
  __omp_Object0.words = words;
  __omp_Object0.conteudo = conteudo;
  __omp_Object0.frase = frase;
  __omp_Object0.file = file;
  // firstprivate variables
  try {
    jomp.runtime.OMP.doParallel(__omp_Object0);
  } catch(Throwable __omp_exception) {
    System.err.println("OMP Warning: Illegal thread exception ignored!");
    System.err.println(__omp_exception);
  }
  // reduction variables
  // shared variables
  fraseValida = __omp_Object0.fraseValida;
  words = __omp_Object0.words;
  conteudo = __omp_Object0.conteudo;
  frase = __omp_Object0.frase;
  file = __omp_Object0.file;
}
// OMP PARALLEL BLOCK ENDS


				if (!fraseValida) {
					frase.setStatus(Status.INVALIDA);
					frasesInvalidas.add(frase);
					frases.remove(0);
				} else {
					frase.setStatus(Status.VALIDA);
					this.frasesValidas.add(frase);
					this.frases.remove(0);
				}
			}

			System.out
					.println("Finalizando processamento das Frases do  arquivo : "
							+ this.getFile().getName());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void imprimirRelatorio() {
		try {

			if (!this.frasesInvalidas.isEmpty()) {

				File pastaErros = new File(file.getParentFile().getPath()
						+ File.separator + "erros");
				if (!pastaErros.exists())
					pastaErros.mkdir();
				BufferedWriter bufferedWriter = null;
				bufferedWriter = new BufferedWriter(
						new FileWriter(new File(pastaErros.getPath()
								+ File.separator + file.getName())));
				bufferedWriter.write("Relatorio de erros.\r\n");

				for (int i = 0; i < frasesInvalidas.size(); i++) {
					Frases frase = (Frases) frasesInvalidas.get(i);
					bufferedWriter.write("Frase: " + frase.getConteudo()
							+ ", lista de palavras inv\u00e1lidas: "
							+ frase.palavrasInvalidas() + "\r\n");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

// OMP PARALLEL REGION INNER CLASS DEFINITION BEGINS
private class __omp_Class0 extends jomp.runtime.BusyTask {
  // shared variables
  boolean fraseValida;
  String [ ] words;
  String conteudo;
  Frases frase;
  File file;
  // firstprivate variables
  // variables to hold results of reduction

  public void go(int __omp_me) throws Throwable {
  // firstprivate variables + init
  // private variables
  // reduction variables, init to default
    // OMP USER CODE BEGINS

                                          { // OMP FOR BLOCK BEGINS
                                          // copy of firstprivate variables, initialized
                                          // copy of lastprivate variables
                                          // variables to hold result of reduction
                                          boolean _cp_fraseValida;
                                          boolean amLast=false;
                                          {
                                            // firstprivate variables + init
                                            // [last]private variables
                                            // reduction variables + init to default
                                            boolean  fraseValida = true;
                                            // -------------------------------------
                                            jomp.runtime.LoopData __omp_WholeData2 = new jomp.runtime.LoopData();
                                            jomp.runtime.LoopData __omp_ChunkData1 = new jomp.runtime.LoopData();
                                            __omp_WholeData2.start = (long)( 0);
                                            __omp_WholeData2.stop = (long)( words.length);
                                            __omp_WholeData2.step = (long)(1);
                                            jomp.runtime.OMP.setChunkStatic(__omp_WholeData2);
                                            while(!__omp_ChunkData1.isLast && jomp.runtime.OMP.getLoopStatic(__omp_me, __omp_WholeData2, __omp_ChunkData1)) {
                                            for(;;) {
                                              if(__omp_WholeData2.step > 0) {
                                                 if(__omp_ChunkData1.stop > __omp_WholeData2.stop) __omp_ChunkData1.stop = __omp_WholeData2.stop;
                                                 if(__omp_ChunkData1.start >= __omp_WholeData2.stop) break;
                                              } else {
                                                 if(__omp_ChunkData1.stop < __omp_WholeData2.stop) __omp_ChunkData1.stop = __omp_WholeData2.stop;
                                                 if(__omp_ChunkData1.start > __omp_WholeData2.stop) break;
                                              }
                                              for(int i = (int)__omp_ChunkData1.start; i < __omp_ChunkData1.stop; i += __omp_ChunkData1.step) {
                                                // OMP USER CODE BEGINS
 {
					String word = words[i];
					boolean validWord = Lexical.validateWord(word);
					fraseValida = validWord;
					if (!validWord) {
						frase.addInvalidWord(word);
					}
				}
                                                // OMP USER CODE ENDS
                                                if (i == (__omp_WholeData2.stop-1)) amLast = true;
                                              } // of for 
                                              if(__omp_ChunkData1.startStep == 0)
                                                break;
                                              __omp_ChunkData1.start += __omp_ChunkData1.startStep;
                                              __omp_ChunkData1.stop += __omp_ChunkData1.startStep;
                                            } // of for(;;)
                                            } // of while
                                            // call reducer
                                            _cp_fraseValida = (boolean) jomp.runtime.OMP.doAndReduce(__omp_me, fraseValida);
                                            jomp.runtime.OMP.doBarrier(__omp_me);
                                            // copy lastprivate variables out
                                            if (amLast) {
                                            }
                                          }
                                          // set global from lastprivate variables
                                          if (amLast) {
                                          }
                                          // set global from reduction variables
                                          if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
                                            fraseValida = _cp_fraseValida;
                                          }
                                          } // OMP FOR BLOCK ENDS

    // OMP USER CODE ENDS
  // call reducer
  // output to _rd_ copy
  if (jomp.runtime.OMP.getThreadNum(__omp_me) == 0) {
  }
  }
}
// OMP PARALLEL REGION INNER CLASS DEFINITION ENDS

}

