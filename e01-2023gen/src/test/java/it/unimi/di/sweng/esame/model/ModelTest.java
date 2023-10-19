package it.unimi.di.sweng.esame.model;

import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.Observer;
import it.unimi.di.sweng.esame.presenter.DisplayPresenter;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ModelTest {
	@Test
	void testTreno() {
		Treno SUT = new Treno("TI 2390");
		assertThat(SUT.toString()).isEqualTo("Treno: [TI 2390]");
	}

	@Test
	void testPartenza() {
		Treno treno = mock(Treno.class);
		when(treno.toString()).thenReturn("Treno: [TI 3029]");
		String destinazione = "ALBENGA";
		LocalTime orario = LocalTime.parse("14:32");
		int delay = 23;
		Partenza SUT = new Partenza(treno, destinazione,orario,delay);

		assertThat(SUT.toString()).isEqualTo("Treno: [TI 3029] dest: [ALBENGA] orario: [14:32] delay: [23]");

	}

	@Test
	void testReadFile() {
		Model SUT = new Model();
		SUT.readFile();

		assertThat(SUT.departureCount()).isEqualTo(20);
		assertThat(SUT.containsTrain(new Treno("TI 3029"))).isTrue();
	}

	@Test
	void testChangeDelay() {
		Treno treno = mock(Treno.class);
		when(treno.toString()).thenReturn("Treno: [TI 3029]");
		String destinazione = "ALBENGA";
		LocalTime orario = LocalTime.parse("14:32");
		int delay = 23;

		Partenza SUT = new Partenza(treno, destinazione,orario,delay);
		SUT = SUT.changeDelay(34);
		assertThat(SUT.delay()).isEqualTo(34);
	}

	@Test
	void testRemovePartenzaFromModel() {
		Model SUT = new Model();
		SUT.readFile();
		Treno treno = new Treno("TI 3029");
		SUT.removeTrain(treno);

		assertThat(SUT.departureCount()).isEqualTo(19);
		assertThat(SUT.containsTrain(treno)).isFalse();

	}

	@Test
	void testObserver() {
		Observable<List<Partenza>> SUT = new ObservableModel();
		Observer<List<Partenza>> presenter = mock(DisplayPresenter.class);

		SUT.addObserver(presenter);
		SUT.notifyObservers();

		verify(presenter).update(SUT, SUT.getState());
	}

	@Test
	void testComparePartenza() {
		// suppongo che i ritardi sono in minuti
		LocalTime time = LocalTime.parse("14:32");
		int delay_1 = 34;
		int delay_2 = 10;

		Partenza partenza_1 = new Partenza(
				new Treno("TI 3029"),"ALBENGA",time,delay_1);
		Partenza partenza_2 = new Partenza(
				new Treno("TI 3029"),"ALBENGA",time,delay_2);
		LocalTime time_1_delay = time.plusMinutes(delay_1);
		LocalTime time_2_delay = time.plusMinutes(delay_2);

		assertThat(partenza_1.compareTo(partenza_2)).isEqualTo(
				time_1_delay.compareTo(time_2_delay)
		);
	}

	@Test
	void testSetDelay() {
		Model SUT = new Model();
		Treno treno = new Treno("TI 3029");

		SUT.readFile();

		SUT.setDelay(treno,56);

		assertThat(SUT.getPartenza(treno).delay()).isEqualTo(56);
	}
}