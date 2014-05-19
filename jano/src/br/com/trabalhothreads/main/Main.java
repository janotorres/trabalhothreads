package br.com.trabalhothreads.main;


import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.trabalhothreads.algorithm.AlgorithmLexical_jomp;
import br.com.trabalhothreads.objects.Texto_jomp;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
	    Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Informe o diretório onde se encontram os arquivos:");
			String pathname = "C:\\tmp"; //scanner.next();
			File directory = new File(pathname);
			File[] files = directory.listFiles();
			List<Texto_jomp> textos = new ArrayList<Texto_jomp>();
			
			if (files != null){
				System.out.println("Quantidade de arquivos a serem processados:"+files.length);
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					if (file.isFile()){
						System.out.println("Arquivo: "+file.getName());
						textos.add(new Texto_jomp(file));
					}
				}			
			}	
			
			AlgorithmLexical_jomp algorithm = new AlgorithmLexical_jomp(textos);
			algorithm.start();
		} finally {
			scanner.close();
		} 
	}
}
