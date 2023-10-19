package it.unimi.di.sweng.esame;

import org.jetbrains.annotations.NotNull;

public interface Observer<T> {
	void update(@NotNull Observable<T> observable, @NotNull T state);
}
