package it.unimi.di.sweng.esame.model;

import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.Observer;
import it.unimi.di.sweng.esame.presenters.DisplayPresenter;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class ModelTest {
	@Test
	void testSegnalzione() {
		String tratto = "A4";
		int posizione = 4;
		Segnalazione SUT = new Segnalazione(new Posizione(tratto,posizione),"incidente");

		assertThat(SUT.formatDisplay()).isEqualTo("incidente sulla A4 al Km 4");
	}
	@Test
	void testSegnalzionePosizioneMustBePositive() {
		String tratto = "A4";
		int posizione = -4;

		assertThatThrownBy(()->new Segnalazione(new Posizione(tratto,posizione),"incidente"))
				.isInstanceOf(IllegalArgumentException.class).hasMessage("Posizione deve essere positiva");
	}

	@Test
	void testSegnalaRisolto() {
		Segnalazione SUT = new Segnalazione(new Posizione("A4",45),"incidente");

		assertThat(SUT.segnalaComeRisolto().isRisolto()).isTrue();
		assertThat(SUT.isRisolto()).isFalse();
	}

	@Test
	void testPosizione() {
		Posizione SUT = new Posizione("A4",45);
		assertThat(SUT.formatDisplay()).isEqualTo("A4 al Km 45");
	}

	@Test
	void testModel() {
		Model SUT = new Model();
		Segnalazione segnalazione = mock(Segnalazione.class);
		Posizione posizione = new Posizione("A4",45);

		when(segnalazione.getPosizione()).thenReturn(posizione);

		SUT.addSegnalazione(segnalazione);

		assertThat(SUT.contains(posizione)).isTrue();
	}
	@Test
	void testModelDuplicates() {
		Model SUT = new Model();
		Segnalazione segnalazione = mock(Segnalazione.class);
		Posizione posizione = new Posizione("A4",45);

		when(segnalazione.getPosizione()).thenReturn(posizione);

		SUT.addSegnalazione(segnalazione);

		assertThatThrownBy(()-> SUT.addSegnalazione(segnalazione))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("altra segnalazione già presente per questo tratto");
	}

	@Test
	void testContaSegnalazioniNonRisolti() {
		Model SUT = new Model();
		Posizione p1 = new Posizione("A3",45);
		Segnalazione s1 = createMockedSegnalzione(p1,false);

		Posizione p2 = new Posizione("A4",45);
		Segnalazione s2 = createMockedSegnalzione(p2,false);

		SUT.addSegnalazione(s1);
		SUT.addSegnalazione(s2);

		assertThat(SUT.segnalazioniAperti()).isEqualTo(2);

		SUT.risolvi(p2);

		assertThat(SUT.segnalazioniAperti()).isEqualTo(1);
	}

	public static Segnalazione createMockedSegnalzione(@NotNull Posizione p, boolean risolto){
		Segnalazione s = mock(Segnalazione.class);

		when(s.getPosizione()).thenReturn(p);
		when(s.isRisolto()).thenReturn(risolto);
		when(s.formatDisplay()).thenReturn(String.format("incidente sulla %s",p.formatDisplay()));

		return s;
	}

	@Test
	void testObserver() {
		Observable<List<Segnalazione>> SUT = new ObservableModel();
		Observer<List<Segnalazione>> presenter = mock(DisplayPresenter.class);

		SUT.addObserver(presenter);
		SUT.notifyObservers();

		verify(presenter).update(SUT,SUT.getState());

	}

	@Test
	void testGetState() {
		ObservableModel SUT = new ObservableModel();
		Segnalazione s = createMockedSegnalzione(new Posizione("A4",45),false);

		SUT.addSegnalazione(s);

		List<Segnalazione> list = SUT.getState();

		assertThat(list.size()).isEqualTo(1);
		assertThat(list).extracting(Segnalazione::formatDisplay).contains("incidente sulla A4 al Km 45");
	}

	@Test
	void testnotifyObserversOnAddSegnalazione() {
		ObservableModel SUT = new ObservableModel();
		DisplayPresenter presenter = mock(DisplayPresenter.class);
		Segnalazione s = createMockedSegnalzione(new Posizione("A4",45),false);

		SUT.addObserver(presenter);
		SUT.addSegnalazione(s);

		verify(presenter).update(SUT,SUT.getState());
	}
	@Test
	void testnotifyObserversOnRisolvi() {
		ObservableModel SUT = new ObservableModel();
		DisplayPresenter presenter = mock(DisplayPresenter.class);
		Posizione p = new Posizione("A4",45);
		Segnalazione s = createMockedSegnalzione(p,false);

		SUT.addObserver(presenter);
		SUT.addSegnalazione(s);
		verify(presenter).update(SUT,SUT.getState());

		SUT.risolvi(p);
		verify(presenter).update(SUT,SUT.getState());
	}
}