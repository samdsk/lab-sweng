package it.unimi.di.sweng.temperature.model;

import it.unimi.di.sweng.temperature.Observer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TemperatureModel implements Model {
  private final List<Observer<Double>> observers = new ArrayList<>();

  private double temperature = 0.;
  @Override
  public void notifyObservers() {
    for(Observer<Double> o : observers)
      o.update(this, temperature);
  }

  @Override
  public void addObserver(@NotNull Observer<Double> obs) {
    observers.add(obs);
  }

  @Override
  public @NotNull Double getState() {
    return getTemp();
  }

  @Override
  public double getTemp() { return temperature;}

  @Override
  public void setTemp(double temp) {
    if(temp != this.temperature){
      temperature = temp;
      notifyObservers();
    }
  }
}
