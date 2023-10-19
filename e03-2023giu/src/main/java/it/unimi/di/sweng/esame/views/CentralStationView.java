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

public class CentralStationView extends Region {
  @NotNull private final TextField textField;
  @NotNull private final Label error;
  @NotNull private final Button button;
  @NotNull private final Button button2;

  public CentralStationView() {
    button = new Button("Segnala");
    button2 = new Button("Risolto");
    textField = new TextField();
    error = new Label("");
    textField.setFont(Font.font("sans", 20));
    button.setFont(Font.font("sans", 20));
    button2.setFont(Font.font("sans", 20));
    setBackground(new Background(
        new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5.0), Insets.EMPTY)));
    setBorder(new Border(
        new BorderStroke(null, BorderStrokeStyle.SOLID, new CornerRadii(5.0), new BorderWidths(2))));

    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10, 10, 10, 10));

    grid.add(textField, 0, 0);
    grid.add(button, 1, 0);
    grid.add(button2, 2, 0);
    grid.add(error, 0, 1, 3, 1);

    this.getChildren().add(grid);
  }

  public void addHandlers(@NotNull Presenter presenter) {
    button.setOnAction(eh -> presenter.action(button.getText(), textField.getText()));
    button2.setOnAction(eh -> presenter.action(button2.getText(), textField.getText()));
  }


  public void showError(@NotNull String s) {
    error.setText(s);
    setBackground(new Background(
        new BackgroundFill(Color.YELLOW, new CornerRadii(5.0), Insets.EMPTY)));
  }


  public void showSuccess() {
    error.setText("");
    setBackground(new Background(
        new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5.0), Insets.EMPTY)));
  }
}
