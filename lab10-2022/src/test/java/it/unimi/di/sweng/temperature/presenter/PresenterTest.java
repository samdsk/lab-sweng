package it.unimi.di.sweng.temperature.presenter;

import it.unimi.di.sweng.temperature.model.Model;
import it.unimi.di.sweng.temperature.model.TemperatureModel;
import it.unimi.di.sweng.temperature.view.View;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PresenterTest {
    @Test
    void testPresenterGetStateFromModel() {
        Model model = mock(Model.class);
        View view = mock(View.class);

        when(model.getState()).thenReturn(42.);

        Presenter presenter = new TemperaturePresenter(model,view,ScaleStrategy.CELSIUS_SCALE);

        assertThat(presenter.getTemp()).isCloseTo(42.0,within(0.01));
        verify(model,times(1)).getState();
    }

    @Test
    void testCelsiusStrategyFromCelsius() {
        ScaleStrategy scaleStrategy = ScaleStrategy.CELSIUS_SCALE;

        assertThat(scaleStrategy.convertFromCelsius(0.))
                .isCloseTo(0.,within(0.01));

        assertThat(scaleStrategy.convertFromCelsius(100.))
                .isCloseTo(100.,within(0.01));


    }

    @Test
    void testCelsiusStrategyToCelsius() {
        ScaleStrategy scaleStrategy = ScaleStrategy.CELSIUS_SCALE;

        assertThat(scaleStrategy.convertToCelsius(92.3))
                .isCloseTo(92.3,within(0.01));

        assertThat(scaleStrategy.convertToCelsius(0.))
                .isCloseTo(0.,within(0.01));


    }
    @ParameterizedTest
    @CsvSource({"0.0, 32.0","100.0, 212.0"})
    void testFahrenheitStrategyFromCelsiusToFahrenheit(double c,double f) {
        ScaleStrategy scaleStrategy = FahrenheitStrategy.INSTANCE;

        assertThat(scaleStrategy.convertFromCelsius(c))
                .isCloseTo(f,within(0.01));

        assertThat(scaleStrategy.convertFromCelsius(c))
                .isCloseTo(f,within(0.01));


    }
    @Test
    void testFahrenheitStrategyFromFahrenheitToCelsius() {
        ScaleStrategy scaleStrategy = FahrenheitStrategy.INSTANCE;

        assertThat(scaleStrategy.convertToCelsius(212.))
                .isCloseTo(100.,within(0.01));

        assertThat(scaleStrategy.convertToCelsius(23.))
                .isCloseTo(-5.,within(0.01));
    }

    @Test
    void testPresenterUpdateModel() {
        Model model = new TemperatureModel();

        View view = mock(View.class);

        Presenter presenter = new TemperaturePresenter(model,view,ScaleStrategy.CELSIUS_SCALE);

        presenter.updateModel("34.5");

        assertThat(presenter.getTemp()).isCloseTo(34.5,within(0.01));


    }
    @Test
    void testPresenterUpdateModelFahrenheit() {
        Model model = new TemperatureModel();

        View view = mock(View.class);

        Presenter presenter = new TemperaturePresenter(model,view,FahrenheitStrategy.INSTANCE);

        presenter.updateModel("96.7");

        assertThat(presenter.getTemp()).isCloseTo(96.7,within(0.01));


    }

    @Test
    void testPresenterObserverUpdate() {
        Model model = new TemperatureModel();
        View view = mock(View.class);

        Presenter presenter = new TemperaturePresenter(model,view,FahrenheitStrategy.INSTANCE);
        model.setTemp(34.6);

        assertThat(presenter.getPresenterTemp()).isCloseTo(34.6,within(0.01));
    }
}