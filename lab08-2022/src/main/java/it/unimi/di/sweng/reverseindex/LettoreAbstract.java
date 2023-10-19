package it.unimi.di.sweng.reverseindex;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class LettoreAbstract implements Lettore{
	protected final @NotNull List<String> documents = new ArrayList<>();
	protected final @NotNull List<String> stopWords = new ArrayList<>();

	@Override
	public int getDocumentCount() {
		return documents.size();
	}

	@Override
	public String getDocument(int index) {
		assert index >= 0 && index < documents.size() : "index:"+index+" is out of range!";
		return documents.get(index);
	}

	@Override
	public void setStopWords(List<String> stopWords) {
		this.stopWords.addAll(stopWords);
	}

	@NotNull
	@Override
	public Iterator<String> iterator() {
		return Collections.unmodifiableList(documents).iterator();
	}
}
