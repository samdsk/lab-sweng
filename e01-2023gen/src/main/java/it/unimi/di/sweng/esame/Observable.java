package it.unimi.di.sweng.esame;

import org.jetbrains.annotations.NotNull;

public interface Observable<T> {
	void addObserver(@NotNull Observer<@NotNull T> observer);
	void removeObserver(@NotNull Observer<@NotNull T> observer);
	@NotNull T getState();

	void notifyObservers();
}
