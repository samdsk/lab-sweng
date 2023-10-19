package it.unimi.di.sweng.esame.view;

import it.unimi.di.sweng.esame.presenter.InputPresenter;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public class SetDelayView extends Region {
  private final TextField codTreno = new TextField();
  private final TextField delay = new TextField();
  private final Button delayButton = new Button("Delay");


  public SetDelayView() {
    setBackground(new Background(
        new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(5.0), Insets.EMPTY)));
    setBorder(new Border(
        new BorderStroke(null, BorderStrokeStyle.SOLID, new CornerRadii(5.0), new BorderWidths(2))));


    codTreno.setPrefColumnCount(8);
    delay.setPrefColumnCount(4);
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10, 10, 10, 10));
    grid.setHgap(10.0);
    grid.add(codTreno, 0, 0);
    grid.add(delay, 1, 0);
    grid.add(delayButton, 2, 0);

    this.getChildren().add(grid);
  }

  public void addHandlers(@NotNull InputPresenter presenter) {
    delayButton.setOnAction(eh -> presenter.action(codTreno.getText(), delay.getText()));
  }

}
