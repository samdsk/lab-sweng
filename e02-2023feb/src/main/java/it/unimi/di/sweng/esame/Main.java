package it.unimi.di.sweng.esame;


import it.unimi.di.sweng.esame.model.Model;

import it.unimi.di.sweng.esame.presenters.DisplayPresenter;
import it.unimi.di.sweng.esame.presenters.InputPresenter;
import it.unimi.di.sweng.esame.presenters.PodioOrderStrategy;
import it.unimi.di.sweng.esame.presenters.PodioStrategy;
import it.unimi.di.sweng.esame.views.NextNationView;
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
  final public static int PANEL_B_SIZE = 3;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {

    primaryStage.setTitle("2023 - Eurovision Song Contest");

    NextNationView nextNationVotes = new NextNationView();

    DisplayView leftSideView = new DisplayView("Classifica", PANEL_SIZE);
    DisplayView rightSideView = new DisplayView("Classifica (cont)", PANEL_SIZE);
    DisplayView podiumView = new DisplayView("Podio", PANEL_B_SIZE);


    GridPane gridPane = new GridPane();
    gridPane.setBackground(new Background(new BackgroundFill(Color.DARKOLIVEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
    gridPane.setPadding(new Insets(10, 10, 10, 10));

    gridPane.add(nextNationVotes, 0, 0);
    GridPane.setColumnSpan(nextNationVotes, GridPane.REMAINING);
    gridPane.add(leftSideView, 0, 1);
    gridPane.add(rightSideView, 1, 1);

    gridPane.add(podiumView, 0, 2);
    GridPane.setColumnSpan(podiumView, GridPane.REMAINING);

    //TODO creare presenters e connettere model e view
    Model model = new Model();
    model.readFile();

    new DisplayPresenter(model,leftSideView);
    new DisplayPresenter(model,rightSideView,PANEL_SIZE);
    new DisplayPresenter(model,podiumView,0,PANEL_B_SIZE)
            .setPrintStrategy(new PodioStrategy())
            .setOrderStrategy(new PodioOrderStrategy());

    new InputPresenter(model,nextNationVotes);

    // HINT: per aggiornare lo stao viste all'inizio
    model.notifyObservers();

    Scene scene = new Scene(gridPane);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
