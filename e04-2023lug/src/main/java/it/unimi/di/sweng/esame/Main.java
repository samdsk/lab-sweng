package it.unimi.di.sweng.esame;


import it.unimi.di.sweng.esame.model.ObservableModel;
import it.unimi.di.sweng.esame.presenters.BagniniPrintStrategy;
import it.unimi.di.sweng.esame.presenters.DisplayPresenter;
import it.unimi.di.sweng.esame.presenters.InputPresenter;
import it.unimi.di.sweng.esame.presenters.PostazionePrintStrategy;
import it.unimi.di.sweng.esame.views.DisplayView;
import it.unimi.di.sweng.esame.views.PostazioneView;
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

  public static final int NUMPOSTAZIONI = 4;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {

    primaryStage.setTitle("Baywatch");

    PostazioneView[] postazioneView = new PostazioneView[NUMPOSTAZIONI];

    for (int i = 0; i < NUMPOSTAZIONI; i++) {
      postazioneView[i] = new PostazioneView();
    }


    DisplayView leftSideView = new DisplayView("Elenco postazioni", NUMPOSTAZIONI);
    DisplayView rightSideView = new DisplayView("Elenco Bagnini", NUMPOSTAZIONI);

    GridPane gridPane = new GridPane();
    gridPane.setBackground(new Background(new BackgroundFill(Color.DARKOLIVEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
    gridPane.setPadding(new Insets(10, 10, 10, 10));

    ObservableModel model = new ObservableModel();

    for (int i = 0; i < NUMPOSTAZIONI; i++) {
      gridPane.add(postazioneView[i], i % 2, i / 2);
      new InputPresenter(model,postazioneView[i],i);
    }


    gridPane.add(leftSideView, 0, 2);
    gridPane.add(rightSideView, 1, 2);
    new DisplayPresenter(model,leftSideView)
            .setPrintSTrategy(new PostazionePrintStrategy());

    new DisplayPresenter(model,rightSideView)
            .setPrintSTrategy(new BagniniPrintStrategy());

    model.notifyObservers();

    Scene scene = new Scene(gridPane);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
