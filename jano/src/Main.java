import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {			
			System.out.println("Informe o número de arquivos a serem processados simultaneamente.");
			Integer quantidadeArquivosSimultaneos = scanner.nextInt(); 	
			System.out.println("Informe o diretório onde se encontram os arquivos:");
			String pathname = scanner.next();
			File directory = new File(pathname);
			File[] files = directory.listFiles();
			List<Texto> textos = new ArrayList<Texto>();
			if (files != null){
				System.out.println("Quantidade de arquivos a serem processados:"+files.length);
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					System.out.println("Arquivo: "+file.getName());
					textos.add(new Texto(file));
				}			
			}	
			
			AlgorithmLexical algorithm = new AlgorithmLexical(textos, quantidadeArquivosSimultaneos);
			algorithm.start();
		} finally {
			scanner.close();
		}
	}
}
