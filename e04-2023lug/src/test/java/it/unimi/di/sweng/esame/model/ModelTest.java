package it.unimi.di.sweng.esame.model;

import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.Observer;
import it.unimi.di.sweng.esame.presenters.DisplayPresenter;
import it.unimi.di.sweng.esame.views.PostazioneView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ModelTest {
	@Test
	void testPostazione() {
		Bagnino bagnino = new Bagnino("Sam");
		int zona = 0;
		Postazione SUT = new ConcretePostazione(bagnino,zona,Flag.NONE);

		assertThat(SUT.formatPostazione()).isEqualTo("[0] Sam segnala ancora nulla");
	}

	@Test
	void testPostazioneElencoBagnino() {
		Bagnino bagnino = new Bagnino("Sam");
		int zona = 0;
		Postazione SUT = new ConcretePostazione(bagnino,zona,Flag.NONE);

		assertThat(SUT.formatBagnini()).isEqualTo("Sam è alla postazione 0");
	}

	@Test
	void testEmptyPostazione() {

		Postazione SUT = Postazione.EmptyPostazione;
		assertThat(SUT.formatPostazione()).isEqualTo("postazione non presidiata");
	}

	@Test
	void testBagnignoNomeVuoto() {
		assertThatThrownBy(() ->new Bagnino(""))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("nome vuoto");
	}
	@Test
	void testBagnignoNomeTroppoLungo() {
		assertThatThrownBy(() ->new Bagnino("adasdasdasassasaasdsadadsadsdadsdasdasdasdsadasdasadd"))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("nome troppo lungo");
	}

	@Test
	void testModelContains() {
		Model SUT = new Model();

		Postazione postazione = mock(Postazione.class);
		Bagnino bagnino = new Bagnino("Sam");
		when(postazione.bagnino()).thenReturn(bagnino);

		SUT.arriva(postazione,0);

		assertThat(SUT.contains(bagnino)).isTrue();

	}

	@Test
	void testPostazioneGiaOccupata() {
		Model SUT = new Model();

		Postazione postazione = mock(Postazione.class);
		Bagnino bagnino = new Bagnino("Sam");
		when(postazione.bagnino()).thenReturn(bagnino);

		SUT.arriva(postazione,0);

		assertThatThrownBy(()->SUT.arriva(postazione,0))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("postazione già occupata");

	}
	@Test
	void testBagninoGiaPresente() {
		Model SUT = new Model();

		Postazione postazione = mock(Postazione.class);
		Bagnino bagnino = new Bagnino("Sam");
		when(postazione.bagnino()).thenReturn(bagnino);

		SUT.arriva(postazione,0);

		assertThatThrownBy(()->SUT.arriva(postazione,1))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("bagnino già presente in altra postazione");

	}
	@Test
	void testVaViaNonPresente() {
		Model SUT = new Model();

		assertThatThrownBy(()->SUT.vaVia(1))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Bagnino non presente");

	}

	@Test
	void testZonaCheck() {
		Model SUT = new Model();

		Postazione postazione = mock(Postazione.class);

		assertThatThrownBy(()->SUT.arriva(postazione,-1))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("zona non valida");
	}

	@Test
	void testVaVia() {
		Model SUT = new Model();

		Postazione postazione = mock(Postazione.class);
		Bagnino bagnino = new Bagnino("Sam");
		when(postazione.bagnino()).thenReturn(bagnino);

		int zona = 0;
		SUT.arriva(postazione, zona);

		SUT.vaVia(zona);

		assertThat(SUT.contains(bagnino)).isFalse();
		assertThat(SUT.contains(zona)).isFalse();
	}

	@Test
	void testCambiaFlag() {
		Postazione SUT = new ConcretePostazione(new Bagnino("Sam"),1, Flag.NONE);
		SUT.changeFlag(Flag.ROSSA);

		assertThat(SUT.formatPostazione()).isEqualTo("[1] Sam segnala pericolo elevato");
	}

	@Test
	void testModelSegnala() {
		Model SUT = new Model();

		Postazione postazione = mock(Postazione.class);
		Bagnino bagnino = new Bagnino("Sam");
		when(postazione.bagnino()).thenReturn(bagnino);

		int zona = 0;
		SUT.arriva(postazione, zona);

		SUT.segnala(Flag.ROSSA,zona);

		verify(postazione).changeFlag(Flag.ROSSA);
	}

	@Test
	void testModelGetPostazioni() {
		Model SUT = new Model();

		Postazione postazione = mock(Postazione.class);
		Bagnino bagnino = new Bagnino("Sam");
		when(postazione.bagnino()).thenReturn(bagnino);

		int zona = 0;
		SUT.arriva(postazione, zona);
		Postazione[] postazioni = SUT.getPostazioni();

		assertThat(postazioni.length).isEqualTo(4);
		assertThat(postazioni[0].bagnino()).isEqualTo(bagnino);
	}

	@Test
	void testSegnalaDaUnaPostazioneVuota() {
		Model SUT = new Model();

		assertThatThrownBy(()->SUT.segnala(Flag.ROSSA,1))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("postazione non presidiata");
	}

	@Test
	void testObserver() {
		Observable<Postazione[]> SUT = new ObservableModel();
		Observer<Postazione[]> presenter = mock(DisplayPresenter.class);

		SUT.addObserver(presenter);
		SUT.notifyObservers();

		verify(presenter).update(SUT,SUT.getState());
	}

	@Test
	void testPostazioneVuota() {
		Postazione SUT = Postazione.EmptyPostazione;
		assertThat(SUT.formatPostazione()).isEqualTo("postazione non presidiata");
		assertThat(SUT.formatBagnini()).isEqualTo("");
	}

	@Test
	void testDontConvertToConcretePostazioneEmptyPostazione() {
		Model SUT = new Model();

		Postazione postazione = mock(Postazione.class);
		Bagnino bagnino = new Bagnino("Sam");
		when(postazione.bagnino()).thenReturn(bagnino);

		int zona = 0;
		SUT.arriva(postazione, zona);
		Postazione[] postazioni = SUT.getPostazioni();

		assertThat(postazioni.length).isEqualTo(4);
		assertThat(postazioni[0].bagnino()).isEqualTo(bagnino);
		assertThat(postazioni[1]).isEqualTo(Postazione.EmptyPostazione);

	}
}