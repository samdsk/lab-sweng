package it.unimi.di.sweng.temperature.model;

import it.unimi.di.sweng.temperature.Observable;

public interface Model extends Observable<Double> {
  double getTemp();
  void setTemp(double temp);
}
