import java.io.File;
import java.util.List;


public class Texto {
	
	private List<Frases> frases;
	
	private File file;

	public Texto(File file) {
		this.file = file;
	}

	public List<Frases> getFrases() {
		return frases;
	}

	public void setFrases(List<Frases> frases) {
		this.frases = frases;
	}

}
