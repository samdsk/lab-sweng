package it.unimi.di.sweng.esame;

import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import it.unimi.di.sweng.esame.views.DisplayView;
import it.unimi.di.sweng.esame.views.NextNationView;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.assertj.core.api.Assumptions;
import org.assertj.core.util.introspection.FieldSupport;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class GUITest {

  private Label[] leftLabels;
  private Label[] rightLabels;
  private Label[] podiumLabels;
  private TextField nationsVoted;
  private Label nationName;
  private Label errorMessage;

  @Start
  public void start(Stage primaryStage) {
    // Richiama il metodo start del Main in cu avete definito la interfaccia
    Main m = new Main();
    m.start(primaryStage);

    // ritrova riferimenti alle viste
    GridPane gp = (GridPane) primaryStage.getScene().getRoot();
    ObservableList<Node> view = gp.getChildren();
    NextNationView input = (NextNationView) view.get(0);
    DisplayView leftView = (DisplayView) view.get(1);
    DisplayView rightView = (DisplayView) view.get(2);
    DisplayView bottomView = (DisplayView) view.get(3);

    // estraggo alcuni campi privati dalle viste per poterli "robotizzare e/o verificare"

    nationsVoted = FieldSupport.EXTRACTION.fieldValue("votes", TextField.class, input);
    nationName = FieldSupport.EXTRACTION.fieldValue("name", Label.class, input);
    errorMessage = FieldSupport.EXTRACTION.fieldValue("error", Label.class, input);

    leftLabels = FieldSupport.EXTRACTION.fieldValue("rows", Label[].class, leftView);
    rightLabels = FieldSupport.EXTRACTION.fieldValue("rows", Label[].class, rightView);
    podiumLabels = FieldSupport.EXTRACTION.fieldValue("rows", Label[].class, bottomView);
  }

  @Test
  public void _10_testLeftViewStartTest() {
    verifyThat(leftLabels[0], hasText(matchesPattern("Australia *0")));
    verifyThat(nationName, hasText("Australia"));
  }


  @Test
  public void _11_testRightViewStart() {
    verifyThat(rightLabels[0], hasText(matchesPattern("Paesi Bassi *0")));
  }

  @Test
  public void _20_orderAfterOneVoteTest(FxRobot robot) {
    robot.doubleClickOn(nationsVoted);
    robot.write("IT AZ PT UK NL");
    robot.press(KeyCode.ENTER);
    robot.release(KeyCode.ENTER);

    verifyThat(leftLabels[0], hasText(startsWith("Italia")));
    verifyThat(leftLabels[2], hasText(startsWith("Portogallo")));
    verifyThat(nationName, hasText("Azerbaijan"));
  }


  @Test
  public void _21_lastVoteAfterOneVoteTest(FxRobot robot) {
    robot.doubleClickOn(nationsVoted);
    robot.write("IT AZ PT UK NL");
    robot.press(KeyCode.ENTER);
    robot.release(KeyCode.ENTER);

    Assumptions.assumeThat(leftLabels[0].getText().startsWith("Italia"));
    verifyThat(leftLabels[0], hasText(matchesPattern("Italia *\\[5] *5")));
    verifyThat(leftLabels[2], hasText(matchesPattern("Portogallo *\\[3] *3")));
  }

  @ParameterizedTest(name = "{0} -> {1}")
  @CsvSource({"IT, Invalid number of votes",
      "IT AZ PT AU UK, You cannot vote for yourself",
      "IT AZ PP NL UK, Invalid vote code: PP",
      "IT AZ IT NL UK, Duplicated votes"})
  public void _30_wrongInputs(String votes, String message, FxRobot robot) {
    robot.doubleClickOn(nationsVoted);
    robot.write(votes);
    robot.press(KeyCode.ENTER);
    robot.release(KeyCode.ENTER);

    verifyThat(errorMessage, hasText(message));
  }


  @Test
  public void _40_bottomViewStartTest() {
    verifyThat(podiumLabels[0], hasText("---"));
    verifyThat(podiumLabels[1], hasText("---"));
    verifyThat(podiumLabels[2], hasText("---"));
  }

  @Test
  public void _41_podiumOrderAfterOneVoteTest(FxRobot robot) {
    robot.doubleClickOn(nationsVoted);
    robot.write("IT AZ PT UK NL");
    robot.press(KeyCode.ENTER);
    robot.release(KeyCode.ENTER);

    verifyThat(podiumLabels[0], hasText(matchesPattern("Italia *5 *\\[1]")));
    verifyThat(podiumLabels[1], hasText(matchesPattern("Azerbaijan *4 *\\[0]")));
    verifyThat(podiumLabels[2], hasText(matchesPattern("Portogallo *3 *\\[0]")));
  }

  @Test
  public void _42_podiumOrderTest(FxRobot robot) {
    robot.doubleClickOn(nationsVoted);
    robot.write("IT EE AZ UK NL");
    robot.press(KeyCode.ENTER);
    robot.release(KeyCode.ENTER);

    robot.doubleClickOn(nationsVoted);
    robot.write("RS EE IT UK NL");
    robot.press(KeyCode.ENTER);
    robot.release(KeyCode.ENTER);

    verifyThat(podiumLabels[0], hasText(matchesPattern("Italia *8 *\\[1]")));
    verifyThat(podiumLabels[1], hasText(matchesPattern("Estonia *8 *\\[0]")));
    verifyThat(podiumLabels[2], hasText(matchesPattern("Serbia *5 *\\[1]")));
  }

  @Test
  public void _43_rankOrderTest(FxRobot robot) {
    robot.doubleClickOn(nationsVoted);
    robot.write("IT EE AZ UK NL");
    robot.press(KeyCode.ENTER);
    robot.release(KeyCode.ENTER);

    robot.doubleClickOn(nationsVoted);
    robot.write("RS EE IT UK NL");

    robot.press(KeyCode.ENTER);
    robot.release(KeyCode.ENTER);

    verifyThat(leftLabels[0], hasText(matchesPattern("Estonia *\\[4] *8")));
    verifyThat(leftLabels[1], hasText(matchesPattern("Italia *\\[3] *8")));
    verifyThat(leftLabels[2], hasText(matchesPattern("Serbia *\\[5] *5")));
  }

  // Fino a quando come conseguenza dell'ENTER non svuotate la casella di testo
  // questo test avrà problemi
  @Test
  public void _99_endOfVotesTest(FxRobot robot) {
    for (int i = 0; i < 8; i++) {
      robot.doubleClickOn(nationsVoted);
      robot.write("UA PT SE UK ES");
      robot.press(KeyCode.ENTER);
      robot.release(KeyCode.ENTER);
    }

    for (int i = 0; i < 8; i++) {
      robot.doubleClickOn(nationsVoted);
      robot.write("GR AZ IT NO AU");
      robot.press(KeyCode.ENTER);
      robot.release(KeyCode.ENTER);
    }
    verifyThat(nationName, hasText("END OF VOTES"));
  }
}
