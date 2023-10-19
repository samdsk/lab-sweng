package it.unimi.di.sweng.reverseindex;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface Lettore extends Iterable<String> {
	int getDocumentCount();
	String getDocument(int index);

	void leggiInput(Reader reader);

	void setStopWords(List<String> stopWords);

	default String removeWords(String string, List<String> stopWords){
		for(String word : stopWords){
			string = string.replaceAll(word,"");
		}
		return string.replaceAll("[ ]+"," ");
	}
}
