package it.unimi.di.sweng.esame;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import it.unimi.di.sweng.esame.view.DepartureView;
import it.unimi.di.sweng.esame.view.SetDelayView;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.assertj.core.util.introspection.FieldSupport;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;


import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class GUITest {

  private Label[] leftLabels;
  private TextField codTreno;
  private TextField delay;
  private Button delayButton;
  private Label[] rightLabels;
  private Button[] departButtons;


  @Start
  public void start(Stage primaryStage) {
    // Richiama il metodo start del Main in cu avete definito la interfaccia
    Main m = new Main();
    m.start(primaryStage);

    // ritrova riferimenti alle viste
    GridPane gp = (GridPane) primaryStage.getScene().getRoot();
    ObservableList<Node> view = gp.getChildren();
    SetDelayView input = (SetDelayView) view.get(0);
    DepartureView[] command = new DepartureView[2];
    command[0] = (DepartureView) view.get(1);
    command[1] = (DepartureView) view.get(2);


    // estraggo alcuni campi privati dalle viste per poterli "robotizzare e/o verificare"
    leftLabels = FieldSupport.EXTRACTION.fieldValue("labels", Label[].class, command[0]);
    rightLabels = FieldSupport.EXTRACTION.fieldValue("labels", Label[].class, command[1]);
    codTreno = FieldSupport.EXTRACTION.fieldValue("codTreno", TextField.class, input);
    delay = FieldSupport.EXTRACTION.fieldValue("delay", TextField.class, input);
    delayButton = FieldSupport.EXTRACTION.fieldValue("delayButton", Button.class, input);
    departButtons = FieldSupport.EXTRACTION.fieldValue("buttons", Button[].class, command[0]);
  }

  @Test
  public void _10_testLeftViewStartTest() {
    verifyThat(leftLabels[0], hasText(matchesPattern("\s*TI 3029.*")));
  }

  @Test
  public void _11_testRightViewStart() {
    verifyThat(rightLabels[0], hasText(matchesPattern("\s*TN 10471.*")));
  }

  @Test
  public void _45_testFormatStart() {
    assumeThat(leftLabels[0].getText().trim()).startsWith("TI 3029");
    verifyThat(leftLabels[0], hasText(matchesPattern("\s*TI 3029.*ALBENGA.*14:32.*\s0\s*")));
  }

  @Test
  public void _20_testSetDelay(FxRobot robot) {
    robot.doubleClickOn(codTreno);
    robot.write("TN 2170");
    robot.doubleClickOn(delay);
    robot.write("6");
    robot.clickOn(delayButton);


    verifyThat(leftLabels[1], hasText(matchesPattern("\s*TTX 2470.*")));
    verifyThat(leftLabels[2], hasText(matchesPattern("\s*TN 2170.*")));
  }


  @Test
  public void _12_testLeftViewOrder() {
    verifyThat(leftLabels[1], hasText(matchesPattern("\s*TN 2170.*")));
    verifyThat(leftLabels[4], hasText(matchesPattern("\s*TN 2629.*")));
    verifyThat(leftLabels[7], hasText(matchesPattern("\s*TN 10928.*")));
  }

  @Test
  public void _46_testFormatAfterSetDelay(FxRobot robot) {
    robot.doubleClickOn(codTreno);
    robot.write("TN 2170");
    robot.doubleClickOn(delay);
    robot.write("6");
    robot.clickOn(delayButton);

    assumeThat(leftLabels[2].getText().trim()).startsWith("TN 2170");
    verifyThat(leftLabels[2], hasText(matchesPattern("\s*TN 2170.*\s6\s*")));
  }

  @Test
  public void _22_testSetBigDelay(FxRobot robot) {
    robot.doubleClickOn(codTreno);
    robot.write("TN 2170");
    robot.doubleClickOn(delay);
    robot.write("59");
    robot.clickOn(delayButton);

    verifyThat(rightLabels[6], hasText(matchesPattern("\s*TN 2170.*")));
  }

  @Test
  public void _21_testResetDelay(FxRobot robot) {
    robot.doubleClickOn(codTreno);
    robot.write("TN 2629");
    robot.doubleClickOn(delay);
    robot.doubleClickOn(delay);
    robot.write("0");
    robot.clickOn(delayButton);

    verifyThat(leftLabels[1], hasText(matchesPattern("\s*TN 2629.*")));
    verifyThat(leftLabels[2], hasText(matchesPattern("\s*TN 2170.*")));
  }

  @Test
  public void _30_invalidCodeException(FxRobot robot) {
    robot.doubleClickOn(codTreno);
    robot.write("pippo");
    robot.doubleClickOn(delay);
    robot.write("0");
    robot.clickOn(delayButton);
  }

  @Test
  public void _31_emptyCodeException(FxRobot robot) {
    robot.doubleClickOn(codTreno);
    robot.write("");
    robot.doubleClickOn(delay);
    robot.write("0");
    robot.clickOn(delayButton);
  }

  @Test
  public void _34_invalidDelayNumberException(FxRobot robot) {
    robot.doubleClickOn(codTreno);
    robot.write("TN 2629");
    robot.doubleClickOn(delay);
    robot.write("a");
    robot.clickOn(delayButton);
  }

  @Test
  public void _35_emptyDelayNumberException(FxRobot robot) {
    robot.doubleClickOn(codTreno);
    robot.write("TN 2629");
    robot.doubleClickOn(delay);
    robot.write("");
    robot.clickOn(delayButton);

    verifyThat(leftLabels[1], hasText(matchesPattern("\s*TN 2170.*")));
    verifyThat(leftLabels[4], hasText(matchesPattern("\s*TN 2629.*")));
  }

  @Test
  public void _25_singleDepartedLeftTrain(FxRobot robot) {


    robot.clickOn(departButtons[1]);

    verifyThat(leftLabels[0], hasText(matchesPattern("\s*TI 3029.*")));
    verifyThat(leftLabels[1], hasText(matchesPattern("\s*TTX 2470.*")));
    verifyThat(leftLabels[3], hasText(matchesPattern("\s*TN 2629.*")));
  }

  @Test
  public void _26_manyDepartedTrain(FxRobot robot) {
    for (int i = 0; i < 7; i++) {
      robot.clickOn(departButtons[1]);
    }

    verifyThat(leftLabels[0], hasText(matchesPattern("\s*TI 3029.*")));
    verifyThat(leftLabels[1], hasText(matchesPattern("\s*TN 10471.*")));
    verifyThat(leftLabels[7], hasText(matchesPattern("\s*TN 2331.*")));
    verifyThat(rightLabels[2], hasText(matchesPattern("\s*TN 2631.*")));
  }


  @Test
  public void _47_emptySlotsAfterManyDepartedTrain(FxRobot robot) {
    for (int i = 0; i < 7; i++) {
      robot.clickOn(departButtons[1]);
    }

    // accetto vari valori (vuoto, treni terminati, ---)
    verifyThat(rightLabels[6], hasText(matchesPattern("|(Treni terminati)|(---)")));
  }
}
