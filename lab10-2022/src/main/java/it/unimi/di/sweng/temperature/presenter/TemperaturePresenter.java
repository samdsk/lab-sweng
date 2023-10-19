package it.unimi.di.sweng.temperature.presenter;

import it.unimi.di.sweng.temperature.Observable;
import it.unimi.di.sweng.temperature.model.Model;
import it.unimi.di.sweng.temperature.view.View;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class TemperaturePresenter implements Presenter{
    private final @NotNull Model model;
    private final @NotNull View view;
    private double temperature = 0.;
    private final ScaleStrategy scaleStrategy;

    public TemperaturePresenter(@NotNull Model model, @NotNull View view,@NotNull ScaleStrategy scaleStrategy) {

        this.model = model;
        this.view = view;
        this.scaleStrategy = scaleStrategy;

        model.addObserver(this);
        view.addHandlers(this);
    }

    @Override
    public void update(@NotNull Observable<Double> subject, @NotNull Double state) {
        this.temperature = state;
        double fromCelsius = scaleStrategy.convertFromCelsius(temperature);

        // Locale.US per avere 10.00 invece di 10,00 come nel sistema italiano
        view.setValue(String.format(Locale.US,"%.2f",fromCelsius));
    }

    @Override
    public void updateModel(@NotNull String text) {
        double temp = Double.parseDouble(text);
        this.temperature = scaleStrategy.convertToCelsius(temp);

        model.setTemp(temperature);

    }

    @Override
    public double getTemp() {
        double newTemp = model.getState();

        if(temperature != newTemp)
            temperature = newTemp;

        return scaleStrategy.convertFromCelsius(temperature);
    }

    @Override
    public double getPresenterTemp() {
        return temperature;
    }

}
