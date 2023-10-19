package it.unimi.di.sweng.esame.views;

import org.jetbrains.annotations.NotNull;

public interface OutputView {

  void set(int i, @NotNull String s);

  int size();
}
