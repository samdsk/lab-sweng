package it.unimi.di.sweng.esame;

import org.jetbrains.annotations.NotNull;

public interface Observable<T> {
	void addObserver(@NotNull Observer<T> observer);

	void notifyObservers();

	@NotNull T getState();
}
