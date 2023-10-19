package it.unimi.di.sweng.esame.presenters;

import org.jetbrains.annotations.NotNull;

public interface Presenter {
  void action(@NotNull String cmd, @NotNull String args);
}
