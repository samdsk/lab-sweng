package it.unimi.di.sweng.katamaestro;

public interface Observer<T> {
	void update(T state);
}
