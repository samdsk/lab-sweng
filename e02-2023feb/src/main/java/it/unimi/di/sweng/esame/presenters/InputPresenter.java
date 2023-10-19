package it.unimi.di.sweng.esame.presenters;

import it.unimi.di.sweng.esame.model.Model;
import it.unimi.di.sweng.esame.views.NextNationView;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputPresenter implements Presenter {
	private final Model model;
	private final NextNationView view;

	private final List<String> countries;

	public InputPresenter(@NotNull Model model, @NotNull NextNationView view) {
		this.model = model;
		this.view = view;

		view.addHandlers(this);

		countries = model.getCountries();
		countries.sort(String::compareTo);

		setCountryNextName("");
	}

	@Override
	public void action(String country, String voteString) {
		try {
			String[] votes = voteString.split(" ");

			if(votes.length != 5)
				throw new IllegalArgumentException("Invalid number of votes");

			String votingCountryCode = model.getCodeFromCountryName(country);

			if(voteString.contains(votingCountryCode))
				throw new IllegalArgumentException("You cannot vote for yourself");

			findDuplicates(votes);
			checkForInvalidCodes(votes);

			int size = votes.length;

			for (int i = 0; i < votes.length; i++) {
				model.vote(votes[i], size - i);
			}

			view.showSuccess();
		}catch (IllegalArgumentException e){
			view.showError(e.getMessage());
			return;
		}


		setCountryNextName(country);

	}

	private void checkForInvalidCodes(String[] codes){
		for (String code : codes) {
			if(!model.containsCanzone(code))
				throw new IllegalArgumentException("Invalid vote code: "+code);
		}
	}

	private static void findDuplicates(String[] votes)
			throws IllegalArgumentException {
		Map<String,Integer> countVotes = new HashMap<>();

		for (String vote : votes) {
			countVotes.put(vote,countVotes.getOrDefault(vote,0)+1);
		}

		for (Integer value : countVotes.values()) {
			if(value>1)
				throw new IllegalArgumentException("Duplicated votes");
		}
	}

	public void setCountryNextName(@NotNull String currentCountry){
		if(currentCountry.length()<1){
			view.setName(countries.get(0));
			return;
		}

		int index = countries.indexOf(currentCountry);

		if(index<countries.size() - 1){
			view.setName(countries.get(index+1));
		}else{
			view.setName("END OF VOTES");
		}
	}

}
