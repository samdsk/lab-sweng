package it.unimi.di.sweng.temperature;

import it.unimi.di.sweng.temperature.model.Model;
import it.unimi.di.sweng.temperature.model.TemperatureModel;
import it.unimi.di.sweng.temperature.presenter.FahrenheitStrategy;
import it.unimi.di.sweng.temperature.presenter.Presenter;
import it.unimi.di.sweng.temperature.presenter.ScaleStrategy;
import it.unimi.di.sweng.temperature.presenter.TemperaturePresenter;
import it.unimi.di.sweng.temperature.view.MyTextView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    stage.setTitle("Temperature 2022");

    MyTextView celsiusField = new MyTextView("Celsius");
    MyTextView fahrenheitField = new MyTextView("Fahrenheit");

    GridPane gridPane = new GridPane();
    gridPane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    gridPane.setPadding(new Insets(10, 10, 10, 10));

    gridPane.add(celsiusField, 1, 0);
    gridPane.add(fahrenheitField, 1, 1);

    Model model = new TemperatureModel();
    Presenter celsiusPresenter = new TemperaturePresenter(model,celsiusField, ScaleStrategy.CELSIUS_SCALE);

    Presenter fahrenheitePresenter = new TemperaturePresenter(model,fahrenheitField,FahrenheitStrategy.INSTANCE);

    Scene scene = new Scene(gridPane);
    stage.setScene(scene);
    stage.show();

    // scommentare dopo aver creato il model
    model.notifyObservers();
  }
}
