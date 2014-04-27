package br.com.trabalhothreads.objects;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Texto {
	
	private List<Frases> frases;
	
	private File file;

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

	
	public void processar() {
		System.out.println("Iniciando processamento do  arquivo : " + this.file.getName());
		BufferedReader reader = null;
		try { 
			reader = new BufferedReader( new FileReader(file));
			String         line = null;
		    StringBuilder  stringBuilder = new StringBuilder();
		    String         ls = System.getProperty("line.separator");
	
		    while( ( line = reader.readLine() ) != null ) {
		        stringBuilder.append( line );
		        stringBuilder.append( ls );
		    }
		    
		    String texto = stringBuilder.toString();
		    String[] frases = texto.split(".");
		    for (int i = 0; i < frases.length; i++) {
		    	this.frases.add(new Frases(frases[i]));
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
		
		System.out.println("Finalizando processamento do  arquivo : " + this.file.getName());
	}
}
