package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.Bagnino;
import it.unimi.di.sweng.esame.model.ObservableModel;
import it.unimi.di.sweng.esame.model.Postazione;
import it.unimi.di.sweng.esame.presenters.DisplayPresenter;
import it.unimi.di.sweng.esame.views.DisplayView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DisplayPresenterTest {
	@Test
	void testDisplayPresenterUpdatePostazioniElenco() {
		ObservableModel model = mock(ObservableModel.class);
		DisplayView view = mock(DisplayView.class);

		DisplayPresenter SUT = new DisplayPresenter(model,view);

		SUT.setPrintSTrategy(new PostazionePrintStrategy());

		Postazione p1 = mock(Postazione.class);
		when(p1.formatPostazione()).thenReturn("[0] Sam segnala ancora nulla");
		Postazione p2 = mock(Postazione.class);
		when(p1.formatPostazione()).thenReturn("[1] Carlo segnala ancora nulla");
		Postazione p3 = mock(Postazione.class);
		when(p1.formatPostazione()).thenReturn("[2] Maria segnala ancora nulla");
		Postazione p4 = mock(Postazione.class);
		when(p1.formatPostazione()).thenReturn("[3] Bob segnala ancora nulla");

		Postazione[] postazioni = {p1,p2,p3,p4};

		SUT.update(model,postazioni);

		verify(view).set(0,p1.formatPostazione());
		verify(view).set(1,p2.formatPostazione());
		verify(view).set(2,p3.formatPostazione());
		verify(view).set(3,p4.formatPostazione());
	}
	@Test
	void testDisplayPresenterUpdateBagniniElenco() {
		ObservableModel model = mock(ObservableModel.class);
		DisplayView view = mock(DisplayView.class);

		DisplayPresenter SUT = new DisplayPresenter(model,view);

		SUT.setPrintSTrategy(new BagniniPrintStrategy());

		Postazione p1 = mock(Postazione.class);
		when(p1.formatBagnini()).thenReturn("Sam alla postazione 1");
		when(p1.bagnino()).thenReturn(new Bagnino("Sam"));

		Postazione p2 = mock(Postazione.class);
		when(p2.formatBagnini()).thenReturn("Carlo è alla postazione 2");
		when(p2.bagnino()).thenReturn(new Bagnino("Carlo"));

		Postazione p3 = mock(Postazione.class);
		when(p3.formatBagnini()).thenReturn("Bob è alla postazione 3");
		when(p3.bagnino()).thenReturn(new Bagnino("Bob"));

		Postazione[] postazioni = {p1,p2,p3,Postazione.EmptyPostazione};

		SUT.update(model,postazioni);

		verify(view).set(0,p3.formatBagnini());
		verify(view).set(1,p2.formatBagnini());
		verify(view).set(2,p1.formatBagnini());
		verify(view).set(3,"");
	}
}