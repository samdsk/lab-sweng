package it.unimi.di.sweng.esame;

import org.jetbrains.annotations.NotNull;

public interface Observable<T> {
	void addObsever(@NotNull Observer<T> presenter);

	void notifyObservers();

	@NotNull T getState();
}
