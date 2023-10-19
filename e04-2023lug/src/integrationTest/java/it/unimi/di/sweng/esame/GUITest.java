package it.unimi.di.sweng.esame;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.startsWith;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import it.unimi.di.sweng.esame.views.DisplayView;
import it.unimi.di.sweng.esame.views.PostazioneView;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class GUITest {

  private Label[] elencoPostazioni;
  private Label[] elencoBagnini;
  private final TextField[] inputMessage = new TextField[Main.NUMPOSTAZIONI];
  private final Button[][] bottoni = new Button[Main.NUMPOSTAZIONI][];
  private final Label[] errorMessage = new Label[Main.NUMPOSTAZIONI];
  private final Label[] bagniniNomi = new Label[Main.NUMPOSTAZIONI];

  @Start
  public void start(Stage primaryStage) {
    Main m = new Main();
    m.start(primaryStage);

    GridPane gp = (GridPane) primaryStage.getScene().getRoot();
    ObservableList<Node> view = gp.getChildren();

    PostazioneView[] postazioniView = new PostazioneView[Main.NUMPOSTAZIONI];
    for (int i = 0; i < Main.NUMPOSTAZIONI; i++) {
      postazioniView[i] = (PostazioneView) view.get(i);
      bottoni[i] = FieldSupport.EXTRACTION.fieldValue("button", Button[].class, postazioniView[i]);
      inputMessage[i] = FieldSupport.EXTRACTION.fieldValue("textField", TextField.class, postazioniView[i]);
      errorMessage[i] = FieldSupport.EXTRACTION.fieldValue("error", Label.class, postazioniView[i]);
      bagniniNomi[i] = FieldSupport.EXTRACTION.fieldValue("bagnino", Label.class, postazioniView[i]);
    }

    DisplayView leftView = (DisplayView) view.get(Main.NUMPOSTAZIONI);
    DisplayView rightView = (DisplayView) view.get(Main.NUMPOSTAZIONI + 1);

    elencoPostazioni = FieldSupport.EXTRACTION.fieldValue("rows", Label[].class, leftView);
    elencoBagnini = FieldSupport.EXTRACTION.fieldValue("rows", Label[].class, rightView);
  }

  void selezioneContenutoCasellaTesto(FxRobot robot, TextField field) {
    robot.doubleClickOn(field).clickOn(field); //triplo click per selezionare tutto
  }

  @Test
  public void testArrivaOK(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, inputMessage[2]);
    robot.write("Carlo");
    robot.clickOn(bottoni[2][0]);
    verifyThat(errorMessage[2], hasText(""));
    verifyThat(elencoPostazioni[2], hasText("[2] Carlo segnala ancora nulla"));
    verifyThat(elencoBagnini[0], hasText("Carlo è alla postazione 2"));
    verifyThat(bagniniNomi[2] , hasText("Carlo"));
  }

  @Test
  public void testArrivaNomeVuotoFail(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, inputMessage[2]);
    robot.write("");
    robot.clickOn(bottoni[2][0]);
    verifyThat(errorMessage[2], hasText("nome vuoto"));
    verifyThat(elencoPostazioni[2], hasText("postazione non presidiata"));
  }

  @Test
  public void testArrivaNomeLungoFail(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, inputMessage[2]);
    robot.write("Nometroppolungomapropriopropriopriprio");
    robot.clickOn(bottoni[2][0]);
    verifyThat(errorMessage[2], hasText("nome troppo lungo"));
    verifyThat(elencoPostazioni[2], hasText("postazione non presidiata"));
  }


  @Test
  public void testVaViaNonpresenteFail(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, inputMessage[2]);
    robot.write("");
    robot.clickOn(bottoni[2][2]);
    verifyThat(errorMessage[2], hasText("Bagnino non presente"));
    verifyThat(elencoPostazioni[2], hasText("postazione non presidiata"));
  }

  @Test
  public void testSegnalaBandieraVuota(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, inputMessage[1]);
    robot.write("");
    robot.clickOn(bottoni[1][1]);
    verifyThat(errorMessage[1], hasText("Indicare colore bandiera"));
  }

  @Test
  public void testSegnalaBandieraSbagliata(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, inputMessage[1]);
    robot.write("TURCHESE");
    robot.clickOn(bottoni[1][1]);
    verifyThat(errorMessage[1], hasText("Bandiera non valida"));
  }

  @Test
  public void testSegnalaBandieraDaPostazioneVuota(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, inputMessage[1]);
    robot.write("ROSSA");
    robot.clickOn(bottoni[1][1]);
    verifyThat(errorMessage[1], hasText("postazione non presidiata"));
  }


  @Test
  public void testSegnalaBandieraOK(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, inputMessage[2]);
    robot.write("Carlo");
    robot.clickOn(bottoni[2][0]);

    selezioneContenutoCasellaTesto(robot, inputMessage[2]);
    robot.write("ROSSA");
    robot.clickOn(bottoni[2][1]);
    verifyThat(errorMessage[2], hasText(""));
    verifyThat(elencoPostazioni[2], hasText("[2] Carlo segnala pericolo elevato"));

  }


  @Test
  public void testArrivaBagninoFail(FxRobot robot) {

    selezioneContenutoCasellaTesto(robot, inputMessage[2]);
    robot.write("Carlo");
    robot.clickOn(bottoni[2][0]);

    selezioneContenutoCasellaTesto(robot, inputMessage[2]);
    robot.write("Mattia");
    robot.clickOn(bottoni[2][0]);

    verifyThat(errorMessage[2], hasText("postazione già occupata"));
  }

  @Test
  public void testArrivaBagninoFail2(FxRobot robot) {

    selezioneContenutoCasellaTesto(robot, inputMessage[2]);
    robot.write("Carlo");
    robot.clickOn(bottoni[2][0]);

    selezioneContenutoCasellaTesto(robot, inputMessage[0]);
    robot.write("Carlo");
    robot.clickOn(bottoni[0][0]);

    verifyThat(errorMessage[0], hasText("bagnino già presente in altra postazione"));
  }

  @Test
  public void testOrdinamentoListaBagnini(FxRobot robot) {
    selezioneContenutoCasellaTesto(robot, inputMessage[0]);
    robot.write("Violetta");
    selezioneContenutoCasellaTesto(robot, inputMessage[2]);
    robot.write("Mattia");
    selezioneContenutoCasellaTesto(robot, inputMessage[1]);
    robot.write("Carlo");
    selezioneContenutoCasellaTesto(robot, inputMessage[3]);
    robot.write("Anna");

    robot.clickOn(bottoni[2][0]);
    robot.clickOn(bottoni[1][0]);
    robot.clickOn(bottoni[3][0]);
    robot.clickOn(bottoni[0][0]);

    verifyThat(elencoBagnini[0], hasText("Anna è alla postazione 3"));
    verifyThat(elencoBagnini[1], hasText(startsWith("Carlo è alla postazione 1")));
    verifyThat(elencoBagnini[2], hasText(startsWith("Mattia è alla postazione 2")));
    verifyThat(elencoBagnini[3], hasText(startsWith("Violetta è alla postazione 0")));

    robot.clickOn(bottoni[1][2]);

    verifyThat(elencoBagnini[0], hasText("Anna è alla postazione 3"));
    verifyThat(elencoBagnini[1], hasText(startsWith("Mattia è alla postazione 2")));
    verifyThat(elencoBagnini[2], hasText(startsWith("Violetta è alla postazione 0")));
  }
}
