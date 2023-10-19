package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.ModelTest;
import it.unimi.di.sweng.esame.model.ObservableModel;
import it.unimi.di.sweng.esame.model.Posizione;
import it.unimi.di.sweng.esame.model.Segnalazione;
import it.unimi.di.sweng.esame.views.DisplayView;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DisplayPresenterTest {
	@Test
	void testSegnalazioniAperti() {
		DisplayView view = mock(DisplayView.class);
		ObservableModel model = mock(ObservableModel.class);

		DisplayPresenter SUT = new SegnalazioniAperte(model,view);

		Segnalazione s1 = ModelTest.createMockedSegnalzione(
				new Posizione("A4",45),false);
		Segnalazione s2 = ModelTest.createMockedSegnalzione(
				new Posizione("A3",45),true);

		SUT.update(model, List.of(s1,s2));

		verify(view,times(1)).set(0,s1.formatDisplay());
		verify(view,never()).set(0,s2.formatDisplay());
	}
	@Test
	void testSegnalazioniRisolti() {
		DisplayView view = mock(DisplayView.class);
		ObservableModel model = mock(ObservableModel.class);

		DisplayPresenter SUT = new SegnalazioniRisolte(model,view);

		Segnalazione s1 = ModelTest.createMockedSegnalzione(
				new Posizione("A4",45),false);
		Segnalazione s2 = ModelTest.createMockedSegnalzione(
				new Posizione("A3",45),true);

		SUT.update(model, List.of(s1,s2));

		verify(view,times(1)).set(0,s2.formatDisplay());
		verify(view,never()).set(0,s1.formatDisplay());
	}

	@Test
	void testTogliSegnalazioniRisolteDaSinistra() {
		DisplayView view = mock(DisplayView.class);
		ObservableModel model = mock(ObservableModel.class);

		DisplayPresenter SUT = new SegnalazioniAperte(model,view);

		Segnalazione s1 = ModelTest.createMockedSegnalzione(
				new Posizione("A4",45),false);

		List<Segnalazione> list = List.of(s1);

		SUT.update(model, list);

		verify(view).set(0,s1.formatDisplay());

		when(s1.isRisolto()).thenReturn(true);
		SUT.update(model,list);

		verify(view).set(0,"");
	}
	@Test
	void testTogliLeRigheVuote() {
		DisplayView view = mock(DisplayView.class);
		ObservableModel model = mock(ObservableModel.class);

		DisplayPresenter SUT = new SegnalazioniRisolte(model,view);

		Segnalazione s1 = ModelTest.createMockedSegnalzione(
				new Posizione("A4",45),true);

		List<Segnalazione> list = List.of(s1);

		SUT.update(model, list);

		verify(view,times(7)).set(anyInt(),eq(""));


	}
}