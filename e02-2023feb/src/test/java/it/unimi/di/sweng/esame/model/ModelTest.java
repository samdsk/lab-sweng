package it.unimi.di.sweng.esame.model;

import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.Observer;
import it.unimi.di.sweng.esame.presenters.DisplayPresenter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ModelTest {
	@Test
	void testCanzone() {
		Model SUT = new Model();
		SUT.readFile();

		assertThat(SUT.containsCanzone("IT")).isTrue();
	}

	@Test
	void testObserver() {
		Observer<List<Canzone>> presenter = mock(DisplayPresenter.class);
		Observable<List<Canzone>> SUT = new Model();

		SUT.addObserver(presenter);
		SUT.notifyObservers();

		verify(presenter).update(SUT,SUT.getState());
	}

	@Test
	void testModelGetState() {
		Model SUT = new Model();
		SUT.readFile();
		List<Canzone> state = SUT.getState();

		assertThat(state.size()).isEqualTo(16);
		assertThat(state).
				extracting(Canzone::formatClassifica)
				.contains(
						"Australia 0",
						"Italia 0",
						"Paesi Bassi 0"
				);

	}

	@Test
	void testModelGetCountries() {
		Model SUT = new Model();
		SUT.readFile();
		List<String> countries = SUT.getCountries();

		assertThat(countries.size()).isEqualTo(16);
		assertThat(countries).contains("Australia","Italia","Svezia");
	}


	@Test
	void testVoteByCode() {
		Model SUT = new Model();
		SUT.readFile();

		SUT.vote("IT",5);

		List<Canzone> list = SUT.getState();
		assertThat(list)
				.extracting(Canzone::formatClassifica).
				contains("Italia [5] 5");
	}
	@Test
	void testVoteByInvalidCode() {
		Model SUT = new Model();
		SUT.readFile();

		assertThatThrownBy(()-> SUT.vote("PP",5))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Invalid vote code: PP");
	}

	@Test
	void testVoteAndNotifyObservers() {
		Model SUT = new Model();
		DisplayPresenter presenter = mock(DisplayPresenter.class);
		SUT.readFile();
		SUT.addObserver(presenter);
		SUT.vote("IT",5);

		verify(presenter).update(eq(SUT),any());
	}

	@Test
	void testLastVotedVote() {
		Canzone SUT = new Canzone("IT","Italia");
		SUT.vote(5);
		assertThat(SUT.formatClassifica()).isEqualTo("Italia [5] 5");
	}
	@Test
	void testLastVotedVoteZero() {
		Canzone SUT = new Canzone("IT","Italia");
		SUT.vote(5);
		SUT.vote(0);
		assertThat(SUT.formatClassifica()).isEqualTo("Italia 5");
	}

	@Test
	void testHowManyTimesHasBeenVotesAsBest() {
		Canzone SUT = new Canzone("IT","Italia");
		SUT.vote(5);
		SUT.vote(5);
		assertThat(SUT.formatPodio()).isEqualTo("Italia 10 [2]");
	}
	@Test
	void testPodiumFormatForNoFirstCount() {
		Canzone SUT = new Canzone("IT","Italia");
		SUT.vote(4);
		SUT.vote(3);
		assertThat(SUT.formatPodio()).isEqualTo("Italia 7 [0]");
	}
}