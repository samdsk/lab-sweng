package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.Model;
import it.unimi.di.sweng.esame.model.Posizione;
import it.unimi.di.sweng.esame.views.CentralStationView;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PresenterTest {
	@Test
	void testInputPresenterSegnalaCampiMancati() {
		Model model = mock(Model.class);
		CentralStationView view = mock(CentralStationView.class);

		Presenter SUT = new InputPresenter(model,view);

		SUT.action("Segnala","A4,54");

		verify(view).showError("campo descrizione mancante");
	}

	@Test
	void testCampoPosizioneNonNumerico() {
		Model model = mock(Model.class);
		CentralStationView view = mock(CentralStationView.class);

		Presenter SUT = new InputPresenter(model,view);

		SUT.action("Segnala","A4,sdf,Hello");

		verify(view).showError("campo km non numerico");
	}

	@Test
	void testAggiungiSegnalazioneAModel() {
		Model model = mock(Model.class);
		CentralStationView view = mock(CentralStationView.class);

		Presenter SUT = new InputPresenter(model,view);

		SUT.action("Segnala","A4,45,incidente");

		verify(view).showSuccess();
		verify(model).addSegnalazione(any());
	}
	@Test
	void testRisolvi() {
		Model model = mock(Model.class);
		Posizione p = new Posizione("A4",45);
		when(model.contains(p)).thenReturn(true);

		CentralStationView view = mock(CentralStationView.class);

		Presenter SUT = new InputPresenter(model,view);

		SUT.action("Segnala","A4,45,incidente");
		SUT.action("Risolto","A4,45");

		verify(model).risolvi(any());
	}
	@Test
	void testRisolviNumeriCampiSbagliato() {
		Model model = mock(Model.class);
		CentralStationView view = mock(CentralStationView.class);

		Presenter SUT = new InputPresenter(model,view);

		SUT.action("Risolvi","A4");
		verify(view).showError("campi mancanti");
	}
	@Test
	void testTroppeSegnalazioniAperti() {
		Model model = mock(Model.class);
		CentralStationView view = mock(CentralStationView.class);

		Presenter SUT = new InputPresenter(model,view);

		when(model.segnalazioniAperti()).thenReturn(8);

		SUT.action("Segnala","A1,45,incidente");

		verify(view).showError("troppe segnalazioni aperte");
	}

	@Test
	void testNonPossoRisolvereUnSegnalazioneNonPresente() {
		Model model = mock(Model.class);
		when(model.contains(new Posizione("A1",45))).thenReturn(false);

		CentralStationView view = mock(CentralStationView.class);

		Presenter SUT = new InputPresenter(model,view);

		SUT.action("Risolto","A1,45");

		verify(view).showError("segnalazione non presente per questo tratto");
	}
}