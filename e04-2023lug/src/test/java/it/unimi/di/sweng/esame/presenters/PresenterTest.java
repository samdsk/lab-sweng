package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.Flag;
import it.unimi.di.sweng.esame.model.Model;
import it.unimi.di.sweng.esame.model.Postazione;
import it.unimi.di.sweng.esame.views.PostazioneView;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PresenterTest {
	@Test
	void testInputPresenterArriva() {
		Model model = mock(Model.class);
		PostazioneView view = mock(PostazioneView.class);
		int zona = 1;
		Presenter SUT = new InputPresenter(model,view,zona);

		SUT.action("Arriva","Sam");

		verify(model).arriva(any(),eq(zona));
	}
	@Test
	void testInputPresenterArrivaInvalidName() {
		Model model = mock(Model.class);
		PostazioneView view = mock(PostazioneView.class);
		int zona = 1;
		Presenter SUT = new InputPresenter(model,view,zona);

		SUT.action("Arriva","");

		verify(view).showError("nome vuoto");

	}
	@Test
	void testInputPresenterVaVia() {
		Model model = mock(Model.class);
		PostazioneView view = mock(PostazioneView.class);
		int zona = 1;
		Presenter SUT = new InputPresenter(model,view,zona);

		SUT.action("Va via","Sam");

		verify(model).vaVia(zona);
	}
	@Test
	void testInputPresenterVaViaError() {
		int zona = 1;
		Model model = mock(Model.class);
		doThrow(new IllegalArgumentException("postazione non presidiata")).when(model).vaVia(zona);

		PostazioneView view = mock(PostazioneView.class);
		Presenter SUT = new InputPresenter(model,view,zona);

		SUT.action("Va via","Sam");

		verify(view).showError("postazione non presidiata");
	}

	@Test
	void testInputPresenterSegnala() {
		Model model = mock(Model.class);
		PostazioneView view = mock(PostazioneView.class);
		int zona = 1;
		Presenter SUT = new InputPresenter(model,view,zona);

		SUT.action("Segnala","Sam,ROSSA");

		verify(model).segnala(Flag.ROSSA,zona);
	}
	@Test
	void testInputPresenterSegnalaBandieraVuota() {
		Model model = mock(Model.class);
		PostazioneView view = mock(PostazioneView.class);
		int zona = 1;
		Presenter SUT = new InputPresenter(model,view,zona);

		SUT.action("Segnala","Sam,");

		verify(view).showError("Indicare colore bandiera");
	}
	@Test
	void testInputPresenterSegnalaBandieraNonValida() {
		Model model = mock(Model.class);
		PostazioneView view = mock(PostazioneView.class);
		int zona = 1;
		Presenter SUT = new InputPresenter(model,view,zona);

		SUT.action("Segnala","Sam,BLU");

		verify(view).showError("Bandiera non valida");
	}
	@Test
	void testInputPresenterArrivaSuccess() {
		Model model = mock(Model.class);
		PostazioneView view = mock(PostazioneView.class);
		int zona = 1;
		Presenter SUT = new InputPresenter(model,view,zona);

		SUT.action("Arriva","Sam");

		verify(view).setBagnino("Sam");
		verify(view).showSuccess();
	}
	@Test
	void testInputPresenterVaViaSuccess() {
		Model model = mock(Model.class);
		PostazioneView view = mock(PostazioneView.class);
		int zona = 1;
		Presenter SUT = new InputPresenter(model,view,zona);

		SUT.action("Va via","Sam");

		verify(view).setBagnino("postazione non presidiata");
		verify(view).showSuccess();
	}


}