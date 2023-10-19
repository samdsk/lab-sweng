package it.unimi.di.sweng.esame.views;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;

public class DisplayView extends Region {
  @NotNull private final Label[] rows;

  public DisplayView(@NotNull String title, int rows) {
    this.rows = new Label[rows];
    setBackground(new Background(
        new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(5.0), Insets.EMPTY)));
    setBorder(new Border(
        new BorderStroke(null, BorderStrokeStyle.SOLID, new CornerRadii(5.0), new BorderWidths(2))));

    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10, 10, 10, 10));
    Label title1 = new Label(title);
    title1.setFont(Font.font("sans", 20));
    grid.add(title1, 0, 0);
    for (int i = 0; i < rows; i++) {
      this.rows[i] = new Label(String.format("%-35s","Row #" + i));
      this.rows[i].setPadding(new Insets(10, 10, 10, 10));
      this.rows[i].setFont(Font.font("monospace", 14));
      grid.add(this.rows[i], 0, i + 1);
    }
    this.getChildren().add(grid);
  }

  public void set(int i, @NotNull String s) {
    rows[i].setText(s);
  }

  public int size() {
    return rows.length;
  }


}
