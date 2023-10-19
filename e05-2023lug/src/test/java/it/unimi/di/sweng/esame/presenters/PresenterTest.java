package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.*;
import it.unimi.di.sweng.esame.views.DisplayView;
import it.unimi.di.sweng.esame.views.OutputView;
import it.unimi.di.sweng.esame.views.SummerReportView;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class PresenterTest {
	@Test
	void testInputPresenterSegnalaOk() {
		Modello model = mock(Modello.class);
		SummerReportView view = mock(SummerReportView.class);

		Presenter SUT = new InputPresenter(model,view);

		SUT.action("Segnala","P002;FABBRO;80;160");

		verify(model).segnala(argThat( s -> s.app().equals(new Appartamento("P002", Tecnico.FABBRO))));
		verify(view).showSuccess();
	}

	@Test
	void testTecnicoNonValido() {
		Modello model = mock(Modello.class);
		SummerReportView view = mock(SummerReportView.class);

		Presenter SUT = new InputPresenter(model,view);

		SUT.action("Segnala","P002;PROF;80;160");

		verify(view).showError("Tecnico non valido");
	}
	@Test
	void testLatitudineNonValido() {
		Modello model = mock(Modello.class);
		SummerReportView view = mock(SummerReportView.class);

		Presenter SUT = new InputPresenter(model,view);

		SUT.action("Segnala","P002;FABBRO;-100;160");

		verify(view).showError("Latitudine non valida");
	}
	@Test
	void testLongitudineNonValido() {
		Modello model = mock(Modello.class);
		SummerReportView view = mock(SummerReportView.class);

		Presenter SUT = new InputPresenter(model,view);

		SUT.action("Segnala","P002;FABBRO;-80;190");

		verify(view).showError("Longitudine non valida");
	}

	@Test
	void testSegnalazionePochiCampi() {
		Modello model = mock(Modello.class);
		SummerReportView view = mock(SummerReportView.class);

		Presenter SUT = new InputPresenter(model,view);

		SUT.action("Segnala","P002;FABBRO");

		verify(view).showError("Servono 4 campi separati da punto e virgola");
	}
	@Test
	void testRisolviOk() {
		Modello model = mock(Modello.class);
		SummerReportView view = mock(SummerReportView.class);

		Presenter SUT = new InputPresenter(model,view);

		SUT.action("Risolvi","P002;FABBRO");

		verify(model).risolvi(argThat( s -> s.equals(new Appartamento("P002", Tecnico.FABBRO))));
		verify(view).showSuccess();

	}
	@Test
	void testRisolviTroppiCampi() {
		Modello model = mock(Modello.class);
		SummerReportView view = mock(SummerReportView.class);

		Presenter SUT = new InputPresenter(model,view);

		SUT.action("Risolvi","P001;FABBRO;1;1");

		verify(view).showError("Servono 2 campi separati da punto e virgola");

	}

	@Test
	void testDisplayPresenterInOrdineDiData() {
		ObservableModel model = mock(ObservableModel.class);
		OutputView view = mock(DisplayView.class);
		when(view.size()).thenReturn(2);

		DisplayPresenter SUT = new DisplayPresenterOrario(model,view);

		Segnalazione s2 = new Segnalazione(
				new Appartamento("A201",Tecnico.FABBRO),
				new Latitudine(5.24),
				new Longitudine(9.5),
				LocalTime.parse("07:55:20")
		);
		Segnalazione s1 = new Segnalazione(
				new Appartamento("P204",Tecnico.IDRAULICO),
				new Latitudine(45.2),
				new Longitudine(9.7),
				LocalTime.parse("08:48:18")
		);

		List<Segnalazione> list = new ArrayList<>();
		list.add(s1);
		list.add(s2);

		SUT.update(model,list);

		verify(view).set(0,s2.formatSegnalazione());
		verify(view).set(1,s1.formatSegnalazione());
	}
	@Test
	void testPrintStrategyFormatSegnalzione() {
		PrintStrategy SUT = new SegnalazioneConData();

		Segnalazione s1 = new Segnalazione(
				new Appartamento("P204",Tecnico.IDRAULICO),
				new Latitudine(45.2),
				new Longitudine(9.7),
				LocalTime.parse("08:48:18")
		);

		assertThat(SUT.format(s1)).isEqualTo(
				"Richiesto un IDRAULICO all'appartamento P204 alle 08:48:18"
		);
	}
	@Test
	void testPrintStrategyFormatoConDistanza() {
		PrintStrategy SUT = new SegnalzioneConDistanza();

		Segnalazione s1 = new Segnalazione(
				new Appartamento("A201",Tecnico.FABBRO),
				new Latitudine(45.24),
				new Longitudine(9.5),
				LocalTime.parse("08:52:19")
		);

		assertThat(SUT.format(s1)).isEqualTo(
				"Richiesto un FABBRO all'appartamento A201 a 37,22km"
		);
	}

	@Test
	void testOrderByDistance() {
		OrderStrategy SUT = new DistanceOrderStrategy();

		Segnalazione s2 = new Segnalazione(
				new Appartamento("A201",Tecnico.FABBRO),
				new Latitudine(45.24),
				new Longitudine(9.5),
				LocalTime.parse("08:52:19")
		);
		Segnalazione s1 = new Segnalazione(
				new Appartamento("P205",Tecnico.FABBRO),
				new Latitudine(45.23),
				new Longitudine(9.72),
				LocalTime.parse("08:45:17")
		);

		List<Segnalazione> list = new ArrayList<>();

		list.add(s1);
		list.add(s2);

		SUT.sort(list);

		assertThat(list).containsExactly(s2,s1);
	}

	@Test
	void testDisplayPresenterSolFabbriInOrdineDiDistanza() {
		ObservableModel model = mock(ObservableModel.class);
		OutputView view = mock(DisplayView.class);
		when(view.size()).thenReturn(3);

		DisplayPresenter SUT = new DisplayPresenterFabbri(model,view);

		Segnalazione s2 = new Segnalazione(
				new Appartamento("A201",Tecnico.FABBRO),
				new Latitudine(45.24),
				new Longitudine(9.5),
				LocalTime.parse("08:52:19")
		);
		Segnalazione s1 = new Segnalazione(
				new Appartamento("P205",Tecnico.FABBRO),
				new Latitudine(45.23),
				new Longitudine(9.72),
				LocalTime.parse("08:45:17")
		);
		Segnalazione s3 = new Segnalazione(
				new Appartamento("P204",Tecnico.IDRAULICO),
				new Latitudine(45.2),
				new Longitudine(9.7),
				LocalTime.parse("08:48:18")
		);

		List<Segnalazione> list = new ArrayList<>();
		list.add(s1);
		list.add(s2);
		list.add(s3);

		SUT.update(model,list);

		verify(view).set(0,s2.formatDistanza());
		verify(view).set(1,s1.formatDistanza());
		verify(view,never()).set(anyInt(),eq(s3.formatDistanza()));
	}
	@Test
	void testLaRigaDelTecnicoNonFABBRONonDeveEssereVuotaSeInMezzo() {
		ObservableModel model = mock(ObservableModel.class);
		OutputView view = mock(DisplayView.class);
		when(view.size()).thenReturn(4);

		DisplayPresenter SUT = new DisplayPresenterFabbri(model,view);

		Segnalazione s2 = new Segnalazione(
				new Appartamento("A201",Tecnico.FABBRO),
				new Latitudine(45.24),
				new Longitudine(9.5),
				LocalTime.parse("08:52:19")
		);
		Segnalazione s1 = new Segnalazione(
				new Appartamento("P205",Tecnico.IDRAULICO),
				new Latitudine(45.23),
				new Longitudine(9.72),
				LocalTime.parse("08:45:17")
		);
		Segnalazione s3 = new Segnalazione(
				new Appartamento("H010",Tecnico.FABBRO),
				new Latitudine(10.1),
				new Longitudine(11),
				LocalTime.parse("08:48:18")
		);

		List<Segnalazione> list = new ArrayList<>();
		list.add(s1);
		list.add(s3);
		list.add(s2);

		SUT.update(model,list);

		verify(view).set(0,s2.formatDistanza());
		verify(view).set(1,s3.formatDistanza());
		verify(view,never()).set(anyInt(),eq(s1.formatDistanza()));
	}


	@Test
	void testCallingResetDisplay() {
		ObservableModel model = mock(ObservableModel.class);
		OutputView view = mock(DisplayView.class);
		when(view.size()).thenReturn(8);

		DisplayPresenter SUT = new DisplayPresenterFabbri(model,view);

		SUT.update(model,new ArrayList<>());

		verify(view,times(8)).set(anyInt(),eq(""));
	}
}