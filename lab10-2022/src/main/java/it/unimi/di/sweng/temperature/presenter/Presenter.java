package it.unimi.di.sweng.temperature.presenter;

import it.unimi.di.sweng.temperature.Observable;
import it.unimi.di.sweng.temperature.Observer;
import org.jetbrains.annotations.NotNull;

public interface Presenter extends Observer<Double> {
  void update(@NotNull Observable<Double> subject, @NotNull Double state);

  void updateModel(@NotNull String text);

  double getTemp();

  double getPresenterTemp();
}
