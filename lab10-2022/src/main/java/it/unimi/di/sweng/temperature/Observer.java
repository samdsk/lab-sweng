package it.unimi.di.sweng.temperature;

import org.jetbrains.annotations.NotNull;

public interface Observer<T> {
  void update(@NotNull Observable<T> subject, @NotNull T state);
}
