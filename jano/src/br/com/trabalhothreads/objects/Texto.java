package br.com.trabalhothreads.objects;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import jomp.runtime.OMP;

public class Texto {

	private List frases;

	private List frasesValidas;

	private List frasesInvalidas;

	private File file;

	public Texto() {
		super();
	}

	public Texto(File file) {
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
				// omp parallel for private(i) reduction(&:fraseValida)
				for (int i = 0; i < words.length; i++) {
					String word = words[i];
					boolean validWord = Lexical.validateWord(word);
					fraseValida = validWord;
					if (!validWord) {
						frase.addInvalidWord(word);
					}
				}

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
							+ ", lista de palavras inválidas: "
							+ frase.palavrasInvalidas() + "\r\n");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
