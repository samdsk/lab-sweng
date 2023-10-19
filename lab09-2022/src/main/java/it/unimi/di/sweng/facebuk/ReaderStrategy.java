package it.unimi.di.sweng.facebuk;

import java.io.Reader;
import java.util.Map;

public interface ReaderStrategy {
	void read(Reader reader, Map<String, Utente> users);
}
