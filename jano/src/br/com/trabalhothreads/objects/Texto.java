package br.com.trabalhothreads.objects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class Texto {
	
	private List<Frases> frases;
	
	private File file;

	private boolean loaded = false;

	public Texto(File file) {
		this.frases = new ArrayList<Frases>();
		this.file = file;
	}

	public List<Frases> getFrases() {
		return frases;
	}

	public void setFrases(List<Frases> frases) {
		this.frases = frases;
	}

	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public synchronized void processar() {
		System.out.println("Iniciando processamento do  arquivo : " + this.getFile().getName());
		BufferedReader reader = null;
		try { 
			reader = new BufferedReader( new FileReader(this.getFile()));
			String         line = null;
		    StringBuilder  stringBuilder = new StringBuilder();
		    String         ls = System.getProperty("line.separator");
	
		    while( ( line = reader.readLine() ) != null ) {
		        stringBuilder.append( line );
		        stringBuilder.append( ls );
		    }
		    
		    String texto = stringBuilder.toString();
		    System.out.println(texto);
		    String[] frases = texto.split("\\.");
		    for (int i = 0; i < frases.length; i++) {
		    	System.out.println("Loading frase" + frases[i]);
		    	this.getFrases().add(new Frases(frases[i]));
			}
		} catch (Exception e){
			throw new RuntimeException(e);
		} finally{
			try {
				if (reader != null){
					reader.close();
				}
			} catch (Exception e1){
				throw new RuntimeException(e1);
			}
		}
		
		System.out.println("Finalizando processamento do  arquivo : " + this.getFile().getName());
		loaded = true;
		notifyAll();
	}

	public synchronized void processarFrases(){
		try {
			
			while (!loaded)
				wait();
			
			System.out.println(this.getFrases().size());

			for (int i = 0; i < this.getFrases().size(); i++) {
	
				
				Frases frase = this.getFrases().get(i);
				String conteudo = frase.getConteudo();
				if (conteudo == null || "".equals(conteudo.trim()))
					continue;
				System.out.println("Iniciando validação da frase : " + conteudo);
				String[] words = conteudo.split("\\s+");
				for (int j = 0; j < words.length; j++) {
					String word = words[j];
					Lexical lexical = Lexical.getInstance();
					boolean validWord = lexical.validateWord(word);
					if (!validWord){
						frase.addInvalidWord(word);
						frase.setStatus(Status.INVALIDA);
					}
				}
				
				if (frase.getStatus().equals(Status.INVALIDA)){
					frase.setStatus(Status.VALIDA);
				}
				System.out.println("Finalizando validação da frase : " + conteudo);
	
			}
		} catch (Exception e){
			throw new RuntimeException(e);
		}
	}
	public  boolean isLoaded() {
		return this.loaded;
	}

	public void setLoaded(boolean b) {
		this.loaded = b;		
	}
}
