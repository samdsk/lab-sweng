package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.Model;
import it.unimi.di.sweng.esame.views.NextNationView;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class PresenterTest {
	@Test
	void testSetVotingCountryName() {
		Model model = mock(Model.class);
		List<String> countries = new ArrayList<>();
		countries.add("Italia");
		countries.add("Australia");

		when(model.getCountries()).thenReturn(countries);

		NextNationView view = mock(NextNationView.class);

		Presenter SUT = new InputPresenter(model,view);

		verify(view).setName("Australia");
	}
	@Test
	void testSetNextVotingCountryName() {
		Model model = mock(Model.class);
		List<String> countries = new ArrayList<>();
		countries.add("Italia");
		countries.add("Australia");
		countries.add("Francia");

		when(model.getCountries()).thenReturn(countries);

		NextNationView view = mock(NextNationView.class);
		InputPresenter SUT = new InputPresenter(model,view);

		SUT.setCountryNextName("Australia");
		verify(view).setName("Francia");

		SUT.setCountryNextName("Francia");
		verify(view).setName("Italia");

	}
	@Test
	void testSetNextVotingCountryEND_OF_VOTES() {
		Model model = mock(Model.class);
		List<String> countries = new ArrayList<>();
		countries.add("Australia");

		when(model.getCountries()).thenReturn(countries);

		NextNationView view = mock(NextNationView.class);
		InputPresenter SUT = new InputPresenter(model,view);

		SUT.setCountryNextName("Australia");
		verify(view).setName("END OF VOTES");
	}

	@Test
	void testVoteInvalidNumberOfVotes() {
		Model model = getModel();

		NextNationView view = mock(NextNationView.class);
		Presenter SUT = new InputPresenter(model,view);

		SUT.action("Australia","IT");

		verify(view).showError("Invalid number of votes");
	}
	@Test
	void testCannotVoteYourSelf() {
		Model model = getModel();

		when(model.getCodeFromCountryName("Italia")).thenReturn("IT");

		NextNationView view = mock(NextNationView.class);
		Presenter SUT = new InputPresenter(model,view);

		SUT.action("Italia","IT AZ PT AU UK");

		verify(view).showError("You cannot vote for yourself");
	}
	@Test
	void testDuplicateVotes() {
		Model model = getModel();

		when(model.getCodeFromCountryName("Australia")).thenReturn("AU");

		NextNationView view = mock(NextNationView.class);
		Presenter SUT = new InputPresenter(model,view);

		SUT.action("Australia","IT AZ IT NL UK");

		verify(view).showError("Duplicated votes");
	}
	@Test
	void testVote() {
		Model model = getModel();

		when(model.getCodeFromCountryName("Australia")).thenReturn("AU");

		NextNationView view = mock(NextNationView.class);
		Presenter SUT = new InputPresenter(model,view);

		SUT.action("Australia","IT AZ LT NL UK");

		verify(model).vote("IT",5);
		verify(model).vote("AZ",4);
		verify(model).vote("LT",3);
		verify(model).vote("NL",2);
		verify(model).vote("UK",1);

	}
	@Test
	void testVoteInvalidVoteCode() {
		Model model = getModel();

		when(model.getCodeFromCountryName("Australia")).thenReturn("AU");
		doThrow(new IllegalArgumentException("Invalid vote code: PP"))
				.when(model).vote(eq("PP"),anyInt());

		NextNationView view = mock(NextNationView.class);
		Presenter SUT = new InputPresenter(model,view);

		when(model.containsCanzone("PP")).thenReturn(false);

		SUT.action("Australia","IT AZ PP NL UK");

		verify(view).showError("Invalid vote code: PP");

	}


	@NotNull
	private static Model getModel() {
		Model model = mock(Model.class);
		List<String> countries = new ArrayList<>();
		countries.add("Italia");
		countries.add("Australia");
		countries.add("Polonia");
		countries.add("Spagna");
		countries.add("Serbia");

		when(model.getCodeFromCountryName("Australia")).thenReturn("AU");

		when(model.containsCanzone("IT")).thenReturn(true);
		when(model.containsCanzone("AZ")).thenReturn(true);
		when(model.containsCanzone("LT")).thenReturn(true);
		when(model.containsCanzone("NL")).thenReturn(true);
		when(model.containsCanzone("UK")).thenReturn(true);

		when(model.getCountries()).thenReturn(countries);
		return model;
	}

	@Test
	void testResetShowSuccess() {
		Model model = getModel();

		NextNationView view = mock(NextNationView.class);

		InputPresenter SUT = new InputPresenter(model,view);

		SUT.action("Australia","IT AZ LT NL UK");

		verify(view).showSuccess();
	}
	@Test
	void testAbortVotingIfError() {
		Model model = getModel();
		NextNationView view = mock(NextNationView.class);
		InputPresenter SUT = new InputPresenter(model,view);

		SUT.action("Australia","IT AZ PP NL UK");

		verify(model,never()).vote(anyString(),anyInt());
	}

}