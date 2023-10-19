package it.unimi.di.sweng.esame;

import static org.hamcrest.Matchers.startsWith;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import it.unimi.di.sweng.esame.views.DisplayView;
import it.unimi.di.sweng.esame.views.SummerReportView;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.assertj.core.util.introspection.FieldSupport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.TextInputControlMatchers;

@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class GUITest {


  private static final boolean HEADLESS = false;
  private final TextField[] inputMessage = new TextField[Main.NUMPOSTAZIONI];
  private final Button[][] bottoni = new Button[Main.NUMPOSTAZIONI][];
  private final Label[] errorMessage = new Label[Main.NUMPOSTAZIONI];
  private Label[] elencoSinistra;
  private Label[] elencoDestra;

  @BeforeAll
  public static void setupSpec() {
    if (HEADLESS) System.setProperty("testfx.headless", "true");
  }

  @Start
  public void start(Stage primaryStage) {
    Main m = new Main();
    m.start(primaryStage);


    GridPane gp = (GridPane) primaryStage.getScene().getRoot();
    ObservableList<Node> view = gp.getChildren();

    SummerReportView[] postazioniView = new SummerReportView[Main.NUMPOSTAZIONI];
    for (int i = 0; i < Main.NUMPOSTAZIONI; i++) {
      postazioniView[i] = (SummerReportView) view.get(i);
      bottoni[i] = FieldSupport.EXTRACTION.fieldValue("button", Button[].class, postazioniView[i]);
      inputMessage[i] = FieldSupport.EXTRACTION.fieldValue("textField", TextField.class, postazioniView[i]);
      errorMessage[i] = FieldSupport.EXTRACTION.fieldValue("error", Label.class, postazioniView[i]);
    }

    DisplayView leftView = (DisplayView) view.get(Main.NUMPOSTAZIONI);
    DisplayView rightView = (DisplayView) view.get(Main.NUMPOSTAZIONI + 1);

    elencoSinistra = FieldSupport.EXTRACTION.fieldValue("rows", Label[].class, leftView);
    elencoDestra = FieldSupport.EXTRACTION.fieldValue("rows", Label[].class, rightView);
  }

  void selezioneContenutoCasellaTesto(FxRobot robot, TextField field) {
    robot.doubleClickOn(field).clickOn(field); //triplo click per selezionare tutto
  }

  @Test
  public void situazioneInizialeVistaSinistra(FxRobot robot) {
    verifyThat(elencoSinistra[0], hasText("Richiesto un ELETTRICISTA all'appartamento A201 alle 07:55:20"));
    verifyThat(elencoSinistra[1], hasText("Richiesto un FABBRO all'appartamento P205 alle 08:45:17"));
    verifyThat(elencoSinistra[2], hasText("Richiesto un IDRAULICO all'appartamento P204 alle 08:48:18"));
    verifyThat(elencoSinistra[3], hasText("Richiesto un IDRAULICO all'appartamento A203 alle 08:48:21"));
  }

  @Test
  public void situazioneInizialeVistaDestra(FxRobot robot) {
    verifyThat(elencoDestra[0], hasText("Richiesto un FABBRO all'appartamento A201 a 37,22km"));
    verifyThat(elencoDestra[1], hasText("Richiesto un FABBRO all'appartamento P205 a 50,51km"));
    verifyThat(elencoDestra[2], hasText("Richiesto un FABBRO all'appartamento H010 a 161,02km"));
    verifyThat(elencoDestra[3], hasText(""));
  }

  @Test
  public void testSegnalaCodiceSbagliato(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, inputMessage[0]);
    robot.write("PP02;FABBRO;1;1");
    robot.clickOn(bottoni[0][0]);
    verifyThat(errorMessage[0], hasText("Codice appartamento non valido"));
  }

  @Test
  public void testSegnalaTecnicoNonValido(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, inputMessage[0]);
    robot.write("P002;PROFESSORE;1;1");
    robot.clickOn(bottoni[0][0]);
    verifyThat(errorMessage[0], hasText("Tecnico non valido"));
  }

  @Test
  public void testSegnalaLatitudineNonValida(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, inputMessage[0]);
    robot.write("P002;FABBRO;-100;1");
    robot.clickOn(bottoni[0][0]);
    verifyThat(errorMessage[0], hasText("Latitudine non valida"));
  }

  @Test
  public void testSegnalaLongitudineNonValida(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, inputMessage[0]);
    robot.write("P002;FABBRO;-80;190");
    robot.clickOn(bottoni[0][0]);
    verifyThat(errorMessage[0], hasText("Longitudine non valida"));
  }


  @Test
  public void testSegnalazioneValida(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, inputMessage[0]);
    robot.write("P002;FABBRO;80;160");
    robot.clickOn(bottoni[0][0]);
    verifyThat(errorMessage[0], hasText(""));
    verifyThat(inputMessage[0], TextInputControlMatchers.hasText(""));
  }

  @Test
  public void testSegnalazioneDoppia(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, inputMessage[0]);
    robot.write("P205;FABBRO;80;160");
    robot.clickOn(bottoni[0][0]);
    verifyThat(errorMessage[0], hasText("intervento già presente"));
  }

  @Test
  public void testSegnalazioneOKInserita(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, inputMessage[1]);
    robot.write("P225;FABBRO;80;160");
    robot.clickOn(bottoni[1][0]);
    verifyThat(errorMessage[1], hasText(""));
    verifyThat(elencoDestra[3], hasText("Richiesto un FABBRO all'appartamento P225 a 5938,25km"));
  }

  @Test
  public void testChiusuraNonPossibile(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, inputMessage[1]);
    robot.write("P001;FABBRO");
    robot.clickOn(bottoni[1][1]);
    verifyThat(errorMessage[1], hasText("intervento non presente"));
  }

  @Test
  public void testChiusuraTroppiCampi(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, inputMessage[1]);
    robot.write("P001;FABBRO;1;1");
    robot.clickOn(bottoni[1][1]);
    verifyThat(errorMessage[1], hasText("Servono 2 campi separati da punto e virgola"));
  }

  @Test
  public void testSegnalazionePochiCampi(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, inputMessage[1]);
    robot.write("P001;FABBRO");
    robot.clickOn(bottoni[1][0]);
    verifyThat(errorMessage[1], hasText("Servono 4 campi separati da punto e virgola"));
  }
}
