package it.unimi.di.sweng.esame;


import it.unimi.di.sweng.esame.model.Modello;
import it.unimi.di.sweng.esame.model.ObservableModel;
import it.unimi.di.sweng.esame.presenters.DisplayPresenterFabbri;
import it.unimi.di.sweng.esame.presenters.DisplayPresenterOrario;
import it.unimi.di.sweng.esame.presenters.InputPresenter;
import it.unimi.di.sweng.esame.views.DisplayView;
import it.unimi.di.sweng.esame.views.SummerReportView;
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

  public static final int NUMPOSTAZIONI = 2;
  public static final int NUMVOCIELENCO = 8;

  public static void main(String[] args) {
    launch(args);
  }





  @Override
  public void start(Stage primaryStage) {

    primaryStage.setTitle("Summer Emergency");

    SummerReportView[] summerReportView = new SummerReportView[NUMPOSTAZIONI];

    for (int i = 0; i < NUMPOSTAZIONI; i++) {
      summerReportView[i] = new SummerReportView();
    }


    DisplayView leftSideView = new DisplayView("In ordine di data", NUMVOCIELENCO);
    DisplayView rightSideView = new DisplayView("Solo fabbri in ordine di distanza", NUMVOCIELENCO);

    GridPane gridPane = new GridPane();
    gridPane.setBackground(new Background(new BackgroundFill(Color.DARKOLIVEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
    gridPane.setPadding(new Insets(10, 10, 10, 10));

    ObservableModel model = new ObservableModel();

    for (int i = 0; i < NUMPOSTAZIONI; i++) {
      gridPane.add(summerReportView[i], i % 2, i / 2);
      new InputPresenter(model,summerReportView[i]);
    }


    gridPane.add(leftSideView, 0, 2);
    gridPane.add(rightSideView, 1, 2);

    //TODO creare presenters e connettere model e view

    new DisplayPresenterOrario(model,leftSideView);
    new DisplayPresenterFabbri(model,rightSideView);

    model.readFile();

    model.notifyObservers();

    Scene scene = new Scene(gridPane);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
