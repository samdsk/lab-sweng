package it.unimi.di.sweng.reverseindex;

import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.util.*;

public class LettoreNormale extends LettoreAbstract {

	public LettoreNormale() {
		super();
	}

	@Override
	public void leggiInput(Reader reader) {
		Scanner scanner = new Scanner(reader);

		while(scanner.hasNextLine()){
			String temp = scanner.nextLine();
			temp = removeWords(temp,super.stopWords);
			super.documents.add(temp);
		}
	}
}
