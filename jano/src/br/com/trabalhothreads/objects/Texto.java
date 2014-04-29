package br.com.trabalhothreads.objects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Texto {
	
	private List<Frases> frases;
	
	private List<Frases> frasesValidas;

	private List<Frases> frasesInvalidas;
	
	private File file;

	private boolean loaded = false;
	
	private final Lock lock = new ReentrantLock();
	
	private final Condition condition  = lock.newCondition(); 

	public Texto(File file) {
		this.frases = new ArrayList<Frases>();
		this.frasesInvalidas = new ArrayList<Frases>();
		this.frasesValidas = new ArrayList<Frases>();
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public synchronized void processar(Semaphore semaphore) {
		BufferedReader reader = null;
		try {
			System.out.println("Tentando iniciar processamento do  arquivo : " + this.getFile().getName());
			semaphore.acquire();
			System.out.println("Iniciando processamento do  arquivo : " + this.getFile().getName());
			reader = new BufferedReader( new FileReader(this.getFile()));
			String         line = null;
		    StringBuilder  stringBuilder = new StringBuilder();
		    String         ls = System.getProperty("line.separator");
	
		    while( ( line = reader.readLine() ) != null ) {
		        stringBuilder.append( line );
		        stringBuilder.append( ls );
		    }
		    
		    String texto = stringBuilder.toString();
		    String[] frases = texto.split("\\.");
		    for (int i = 0; i < frases.length; i++) {
		    	this.frases.add(new Frases(frases[i], i));
			}
		} catch (Exception e){
			throw new RuntimeException(e);
		} finally{
			System.out.println("Finalizando processamento do  arquivo : " + this.getFile().getName());
			semaphore.release();
			try {
				if (reader != null){
					reader.close();
				}
			} catch (Exception e1){
				throw new RuntimeException(e1);
			}
		}
		
		loaded = true;
		notifyAll();
	}

	public synchronized void processarFrases(){
		try {
			
			while (!loaded)
				wait();
			
			lock.lock();
			
			try {
				System.out.println("Iniciando processamento das Frases do  arquivo : " + this.getFile().getName());
				while (!this.frases.isEmpty()) {				
					Frases frase = this.frases.get(0);
					String conteudo = frase.getConteudo();
					if (conteudo == null || "".equals(conteudo.trim())){
						this.frases.remove(frase);
						continue;
					}
					String[] words = conteudo.split("\\s+");
					for (int j = 0; j < words.length; j++) {
						String word = words[j];
						Lexical lexical = Lexical.getInstance();
						boolean validWord = lexical.validateWord(word);
						if (!validWord){
							frase.addInvalidWord(word);
							if (!frase.getStatus().equals(Status.INVALIDA)){
								frase.setStatus(Status.INVALIDA);
								this.frasesInvalidas.add(frase);
								this.frases.remove(0);
							}
							
						}
					}
					
					if (!frase.getStatus().equals(Status.INVALIDA)){
						frase.setStatus(Status.VALIDA);
						this.frasesValidas.add(frase);
						this.frases.remove(0);
					}	
				}
				
				System.out.println("Finalizando processamento das Frases do  arquivo : " + this.getFile().getName());
				condition.signalAll();
			} finally {
				lock.unlock();
			}
		} catch (Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public void imprimirRelatorio(){
			lock.lock();
			try {
				
				while(!this.frases.isEmpty() || !loaded)
					condition.await();
				
				System.out.println("Gerando Relatorio de erros." + this.getFile().getName());
				for (Frases frase : this.frasesInvalidas) {
					System.out.println("Gerando Relatorio de erros." + this.getFile().getName() +" Frase "+ frase.getConteudo() + ", lista de palavras inválidas: " + frase.palavrasInvalidas());					
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
	}
}
