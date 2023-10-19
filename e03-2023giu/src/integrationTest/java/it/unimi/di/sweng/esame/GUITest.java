package it.unimi.di.sweng.esame;

import static org.hamcrest.Matchers.matchesPattern;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import it.unimi.di.sweng.esame.views.DisplayView;
import it.unimi.di.sweng.esame.views.CentralStationView;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.assertj.core.util.introspection.FieldSupport;
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

  private Label[] segnalazioniAttive;
  private Label[] segnalazioniRisolte;
  private TextField inputMessage;
  private Button bottoneSegnala;
  private Button bottoneRisolvi;

  private Label errorMessage;

  @Start
  public void start(Stage primaryStage) {
    Main m = new Main();
    m.start(primaryStage);

    GridPane gp = (GridPane) primaryStage.getScene().getRoot();
    ObservableList<Node> view = gp.getChildren();
    CentralStationView input = (CentralStationView) view.get(0);
    DisplayView leftView = (DisplayView) view.get(1);
    DisplayView rightView = (DisplayView) view.get(2);

    inputMessage = FieldSupport.EXTRACTION.fieldValue("textField", TextField.class, input);
    errorMessage = FieldSupport.EXTRACTION.fieldValue("error", Label.class, input);
    bottoneSegnala = FieldSupport.EXTRACTION.fieldValue("button", Button.class, input);
    bottoneRisolvi = FieldSupport.EXTRACTION.fieldValue("button2", Button.class, input);

    segnalazioniAttive = FieldSupport.EXTRACTION.fieldValue("rows", Label[].class, leftView);
    segnalazioniRisolte = FieldSupport.EXTRACTION.fieldValue("rows", Label[].class, rightView);
  }

  void selezioneContenutoCasellaTesto(FxRobot robot) {
    robot.doubleClickOn(inputMessage).clickOn(inputMessage); //triplo click per selezionare tutto
  }

  @Test
  public void testSegnalaOK(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot);
    robot.write("A4,57,incidente");
    robot.doubleClickOn(inputMessage);
    robot.clickOn(inputMessage);
    robot.clickOn(bottoneSegnala);
    verifyThat(errorMessage, hasText(""));
    verifyThat(segnalazioniAttive[0], hasText(matchesPattern("incidente sulla A4 al Km 57")));
  }

  @Test
  public void testRisolviOK(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot);
    robot.write("A4,57,incidente");
    robot.clickOn(bottoneSegnala);
    selezioneContenutoCasellaTesto(robot);
    robot.write("A4,57");
    robot.clickOn(bottoneRisolvi);
    verifyThat(errorMessage, hasText(""));
    verifyThat(segnalazioniAttive[0], hasText(""));
    verifyThat(segnalazioniRisolte[0], hasText(matchesPattern("incidente sulla A4 al Km 57")));
  }

  @Test
  public void testSegnalaConErroreCampoMancante(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot);
    robot.write("A4,57");
    robot.clickOn(bottoneSegnala);
    verifyThat(errorMessage, hasText("campo descrizione mancante"));
  }

  @Test
  public void testSegnalaConErroreCampoKmSbagliato(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot);
    robot.write("A4,b,prova");
    robot.clickOn(bottoneSegnala);
    verifyThat(errorMessage, hasText("campo km non numerico"));
  }

  @Test
  public void testSegnalaConErroreDoppiaSegnalazione(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot);
    robot.write("A4,42,prova");
    robot.clickOn(bottoneSegnala);
    selezioneContenutoCasellaTesto(robot);
    robot.write("A4,42,prova");
    robot.clickOn(bottoneSegnala);
    verifyThat(errorMessage, hasText("altra segnalazione già presente per questo tratto"));
  }

  @Test
  public void testRisolviConErroreSegnalazioneNonPresente(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot);
    robot.write("A4,42");
    robot.clickOn(bottoneRisolvi);
    verifyThat(errorMessage, hasText("segnalazione non presente per questo tratto"));
  }
}
