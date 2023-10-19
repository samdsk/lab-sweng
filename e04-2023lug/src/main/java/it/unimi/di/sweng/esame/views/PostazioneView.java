package it.unimi.di.sweng.esame.views;

import it.unimi.di.sweng.esame.presenters.Presenter;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PostazioneView extends Region {

  @NotNull
  private final TextField textField;
  @NotNull
  private final Label error;
  @NotNull
  private final Button[] button = new Button[3];

  @NotNull
  private final Label bagnino;


  public PostazioneView() {
    bagnino = new Label("postazione non presidiata");
    bagnino.setFont(Font.font("sans", 24));
    button[0] = new Button("Arriva");
    button[1] = new Button("Segnala");
    button[2] = new Button("Va via");
    textField = new TextField();
    error = new Label("");
    textField.setFont(Font.font("sans", 20));

    setBackground(new Background(
        new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5.0), Insets.EMPTY)));
    setBorder(new Border(
        new BorderStroke(null, BorderStrokeStyle.SOLID, new CornerRadii(5.0), new BorderWidths(2))));

    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10, 10, 10, 10));

    grid.add(bagnino, 0, 0, 3, 1);
    grid.add(textField, 0, 2, 3, 1);
    for (int i = 0; i < 3; i++) {
      grid.add(button[i], i, 1);
      button[i].setFont(Font.font("sans", 20));
    }
    grid.add(error, 0, 3, 3, 1);

    this.getChildren().add(grid);
  }

  public void addHandlers(@NotNull Presenter presenter) {
    /*Arriva */
    button[0].setOnAction(eh -> presenter.action(button[0].getText(), textField.getText()));
    /*Segnala*/
    button[1].setOnAction(eh -> presenter.action(button[1].getText(), bagnino.getText() + "," + textField.getText()));
    /*Va via */
    button[2].setOnAction(eh -> presenter.action(button[2].getText(), bagnino.getText()));
  }


  public void showError(@NotNull String s) {
    error.setText(s);
    setBackground(new Background(
        new BackgroundFill(Color.YELLOW, new CornerRadii(5.0), Insets.EMPTY)));
  }


  public void showSuccess() {
    error.setText("");
    textField.clear();
    setBackground(new Background(
        new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5.0), Insets.EMPTY)));
  }

  public void setBagnino(@Nullable String s) {
    bagnino.setText(s);
  }
}
