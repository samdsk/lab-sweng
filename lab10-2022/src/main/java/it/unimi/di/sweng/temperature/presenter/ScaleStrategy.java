package it.unimi.di.sweng.temperature.presenter;

public interface ScaleStrategy {
  ScaleStrategy CELSIUS_SCALE = new ScaleStrategy() {
    @Override
    public double convertFromCelsius(double temperature) {
      return temperature;
    }

    @Override
    public double convertToCelsius(double temperature) {
      return temperature;
    }
  };

  double convertFromCelsius(double temperature);

  double convertToCelsius(double temperature);
}
