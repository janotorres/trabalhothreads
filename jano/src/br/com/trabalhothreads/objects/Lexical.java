package br.com.trabalhothreads.objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexical {

	private static Lexical instance;
	Connection c;

	private Lexical() {
		super();
	}

	public static Lexical getInstance() {
		if (instance == null)
			instance = new Lexical();
		return instance;
	}

	public boolean validateWord(String word) {
		try {
			if (c == null) {
				//Class.forName("org.hsqldb.jdbc.JDBCDriver");
				//c = DriverManager.getConnection("jdbc:hsqldb:file:/base/base",
				//		"SA", "");

				Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
				Matcher match = pt.matcher(word);
				while (match.find()) {
					String s = match.group();
					word = word.replaceAll("\\" + s, "");
				}

				try {
					Integer.parseInt(word);
					return true;
				} catch (NumberFormatException nfe) {
					//analisar palavra
					return false;
				}

			}
			return false;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
