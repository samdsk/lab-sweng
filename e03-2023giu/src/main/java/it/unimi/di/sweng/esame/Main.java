package it.unimi.di.sweng.esame;



import it.unimi.di.sweng.esame.model.ObservableModel;
import it.unimi.di.sweng.esame.presenters.InputPresenter;
import it.unimi.di.sweng.esame.presenters.SegnalazioniAperte;
import it.unimi.di.sweng.esame.presenters.SegnalazioniRisolte;
import it.unimi.di.sweng.esame.views.CentralStationView;
import it.unimi.di.sweng.esame.views.DisplayView;
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
  final public static int PANEL_SIZE = 8;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {

    primaryStage.setTitle("Autostrade");

    CentralStationView stationView = new CentralStationView();

    DisplayView leftSideView = new DisplayView("Segnalazioni Attive", PANEL_SIZE);
    DisplayView rightSideView = new DisplayView("Segnalazioni Chiuse", PANEL_SIZE);

    GridPane gridPane = new GridPane();
    gridPane.setBackground(new Background(new BackgroundFill(Color.DARKOLIVEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
    gridPane.setPadding(new Insets(10, 10, 10, 10));

    gridPane.add(stationView, 0, 0);
    GridPane.setColumnSpan(stationView, GridPane.REMAINING);
    gridPane.add(leftSideView, 0, 1);
    gridPane.add(rightSideView, 1, 1);

    //TODO creare presenters e connettere model e view

    ObservableModel model = new ObservableModel();
    new SegnalazioniAperte(model,leftSideView);
    new SegnalazioniRisolte(model,rightSideView);
    new InputPresenter(model,stationView);

    // HINT: per aggiornare lo stato delle viste all'inizio
    model.notifyObservers();

    Scene scene = new Scene(gridPane);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
