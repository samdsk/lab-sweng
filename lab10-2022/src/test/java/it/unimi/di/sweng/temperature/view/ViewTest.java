package it.unimi.di.sweng.temperature.view;

import it.unimi.di.sweng.temperature.model.Model;
import it.unimi.di.sweng.temperature.model.TemperatureModel;
import it.unimi.di.sweng.temperature.presenter.FahrenheitStrategy;
import it.unimi.di.sweng.temperature.presenter.Presenter;
import it.unimi.di.sweng.temperature.presenter.ScaleStrategy;
import it.unimi.di.sweng.temperature.presenter.TemperaturePresenter;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ViewTest {

    @Test
    void testModelSetTempOnView() {
        Model model = mock(Model.class);
        View view = mock(View.class);
        Presenter SUT = new TemperaturePresenter(model,view, FahrenheitStrategy.INSTANCE);

        SUT.update(model,32.0);

        verify(view).setValue("89.60");
    }

    @Test
    void testViewCallsModelUpdateFromPresenter() {
        Model model = new TemperatureModel();
        View view = mock(View.class);
        Presenter SUT = new TemperaturePresenter(model,view, ScaleStrategy.CELSIUS_SCALE);

        SUT.updateModel("34.5");

        verify(view).setValue("34.50");
    }
}