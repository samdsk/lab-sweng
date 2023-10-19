package it.unimi.di.sweng.temperature.view;

import it.unimi.di.sweng.temperature.presenter.Presenter;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public class MyTextView extends Region implements View {
  @NotNull private final TextField value;

  public MyTextView(@NotNull String scale) {
    setBackground(new Background(
        new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(5.0), Insets.EMPTY)));
    setBorder(new Border(
        new BorderStroke(null, BorderStrokeStyle.SOLID, new CornerRadii(5.0), new BorderWidths(2))));

    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10, 10, 10, 10));

    value = new TextField();
    value.setText("0.00");

    grid.add(new Label(scale),0,0);
    grid.add(value,0,1);

    this.getChildren().add(grid);
  }

  @Override
  public void addHandlers(@NotNull Presenter presenter) {
    value.setOnAction(eh -> presenter.updateModel(value.getText()));
  }

  @Override
  public @NotNull String getValue() {
    return value.getText();
  }

  @Override
  public void setValue(@NotNull String val) {
    value.setText(val);
  }
}
