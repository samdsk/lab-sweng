package it.unimi.di.sweng.esame.views;

import it.unimi.di.sweng.esame.presenters.Presenter;
import org.jetbrains.annotations.NotNull;

public interface InputView {
  void addHandlers(@NotNull Presenter presenter);

  void showError(@NotNull String s);

  void showSuccess();
}
