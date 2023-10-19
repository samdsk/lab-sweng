package it.unimi.di.sweng.esame.model;

import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.Observer;
import it.unimi.di.sweng.esame.presenters.DisplayPresenter;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ModelloTest {
	@Test
	void testSegnalzione() {
		Segnalazione SUT = new Segnalazione(
				new Appartamento("A201",Tecnico.FABBRO),
				new Latitudine(1),
				new Longitudine(1),
				LocalTime.parse("07:55:20")
		);

		assertThat(SUT.formatSegnalazione()).isEqualTo(
				"Richiesto un FABBRO all'appartamento A201 alle 07:55:20"
		);
	}
	@Test
	void testSegnalazioneConDistanzaDalAmministratore(){
		Segnalazione SUT = new Segnalazione(
				new Appartamento("A201",Tecnico.FABBRO),
				new Latitudine(45.24),
				new Longitudine(9.5),
				LocalTime.parse("08:52:19")
		);

		assertThat(SUT.formatDistanza()).isEqualTo(
				"Richiesto un FABBRO all'appartamento A201 a 37,22km"
		);
	}

	@Test
	void testModelReadAndGetSegnalzioni() {
		Modello SUT = new Modello();
		SUT.readFile();

		assertThat(SUT.getSegnalzioni()).extracting(Segnalazione::formatSegnalazione).contains(
				"Richiesto un ELETTRICISTA all'appartamento A201 alle 07:55:20",
				"Richiesto un FABBRO all'appartamento P205 alle 08:45:17",
				"Richiesto un IDRAULICO all'appartamento P204 alle 08:48:18",
				"Richiesto un IDRAULICO all'appartamento A203 alle 08:48:21"
		);
	}

	@Test
	void testAppartamentoCodice() {
		assertThatThrownBy(()-> new Appartamento("PP02",Tecnico.FABBRO))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Codice appartamento non valido");
		assertThatThrownBy(()-> new Appartamento("P0002",Tecnico.FABBRO))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Codice appartamento non valido");
		assertThatThrownBy(()-> new Appartamento("0P02",Tecnico.FABBRO))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Codice appartamento non valido");
	}

	@Test
	void testSegnala() {
		Modello SUT = new Modello();
		SUT.readFile();
		LocalTime time =LocalTime.now();
		Appartamento app = new Appartamento("P205",Tecnico.MURATORE);

		SUT.segnala(new Segnalazione(app, new Latitudine(1), new Longitudine(1), time));

		assertThat(SUT.contains(app)).isTrue();
	}
	@Test
	void testNonCiPossonoEssereDueSegnalazioniPerAppartamentoDiStessoTipo() {
		Modello SUT = new Modello();
		SUT.readFile();
		LocalTime time = LocalTime.now();
		Appartamento app = new Appartamento("P205",Tecnico.FABBRO);

		assertThatThrownBy(()-> SUT.segnala(new Segnalazione(app, new Latitudine(1), new Longitudine(1), time)))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("intervento già presente");

	}

	@Test
	void testRisolviSegnalzione() {
		Modello SUT = new Modello();
		SUT.readFile();
		Appartamento app = new Appartamento("P205",Tecnico.FABBRO);
		SUT.risolvi(app);

		assertThat(SUT.contains(app)).isFalse();
	}
	@Test
	void testRisolviSegnalzioneNonValido() {
		Modello SUT = new Modello();
		SUT.readFile();
		Appartamento app = new Appartamento("P001",Tecnico.FABBRO);

		assertThatThrownBy(()-> SUT.risolvi(app))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("intervento non presente");
	}

	@Test
	void testObservableModelNotifyObservers() {
		ObservableModel SUT = new ObservableModel();
		DisplayPresenter presenter = mock(DisplayPresenter.class);

		SUT.addObsever(presenter);

		LocalTime time = LocalTime.now();
		Appartamento app = new Appartamento("P205",Tecnico.MURATORE);

		SUT.segnala(new Segnalazione(app, new Latitudine(1), new Longitudine(1), time));
		verify(presenter,times(1)).update(SUT,SUT.getState());

		SUT.risolvi(app);

		verify(presenter,times(1)).update(SUT,SUT.getState());
	}

}