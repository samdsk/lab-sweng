package it.unimi.di.sweng.temperature.view;

import it.unimi.di.sweng.temperature.presenter.Presenter;
import org.jetbrains.annotations.NotNull;

public interface View {
  void addHandlers(@NotNull Presenter pres);

  @NotNull String getValue();
  void setValue(@NotNull String val);
}
