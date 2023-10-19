package it.unimi.di.sweng.temperature.presenter;

public enum FahrenheitStrategy implements ScaleStrategy {
    INSTANCE;
    @Override
    public double convertFromCelsius(double temp) {
        return (temp * 9./5.)+ 32.;
    }

    @Override
    public double convertToCelsius(double temp) {
        return (temp - 32.) * 5./9.;
    }
}
