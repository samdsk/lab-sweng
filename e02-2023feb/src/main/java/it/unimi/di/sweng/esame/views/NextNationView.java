package it.unimi.di.sweng.esame.views;


import it.unimi.di.sweng.esame.presenters.Presenter;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;

public class NextNationView extends Region {
  @NotNull
  private final TextField votes;
  @NotNull
  private final Label name;
  private final Label error;

  public NextNationView() {
    name = new Label("NEXT NATION");
    votes = new TextField();
    name.setFont(Font.font("sans", 20));
    name.setMinWidth(200);
    votes.setMaxWidth(250);
    votes.setFont(Font.font("sans", 20));
    setBackground(new Background(
        new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5.0), Insets.EMPTY)));
    setBorder(new Border(
        new BorderStroke(null, BorderStrokeStyle.SOLID, new CornerRadii(5.0), new BorderWidths(2))));

    name.setPadding(new Insets(10, 10, 10, 10));
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10, 10, 10, 10));

    grid.add(name, 0, 0);
    grid.add(votes, 1, 0);

    error = new Label("");
    grid.add(error, 0, 1, 2, 1);

    this.getChildren().add(grid);
  }

  public void addHandlers(@NotNull Presenter presenter) {
    votes.setOnAction(eh -> presenter.action(name.getText(), votes.getText()));   // tasto invio nella casella di testo
  }

  public void setName(String nome) {
    name.setText(nome);
    votes.setText("");
  }

  public void showError(String s) {
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
