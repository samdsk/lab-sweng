package it.unimi.di.sweng.esame;

import it.unimi.di.sweng.esame.model.Model;
import it.unimi.di.sweng.esame.model.ObservableModel;
import it.unimi.di.sweng.esame.presenter.DisplayPresenter;
import it.unimi.di.sweng.esame.presenter.SetDelayPresenter;
import it.unimi.di.sweng.esame.view.DepartureView;
import it.unimi.di.sweng.esame.view.SetDelayView;
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
  //TODO da completare

  public static final int MAX_ROW_ITEMS_IN_VIEW = 8;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {

    primaryStage.setTitle("Lambrate Station");

    DepartureView[] command = new DepartureView[2];

    SetDelayView input = new SetDelayView();

    GridPane gridPane = new GridPane();
    gridPane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    gridPane.setPadding(new Insets(10, 10, 10, 10));

    gridPane.add(input, 0, 0);
    GridPane.setColumnSpan(input, GridPane.REMAINING);
    for (int i = 0; i < 2; i++) {
      command[i] = new DepartureView("Departed", MAX_ROW_ITEMS_IN_VIEW, "Departures #"+i);
      gridPane.add(command[i], i, 1);
    }

    //TODO creare e connettere model e presenters

    ObservableModel model = new ObservableModel();

    new DisplayPresenter(model,command[0],0);
    new DisplayPresenter(model,command[1],8);
    new SetDelayPresenter(model,input);

    model.readFile();

    //HINT: utile dopo aver definito model per inizializzare viste
    model.notifyObservers();

    Scene scene = new Scene(gridPane);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
