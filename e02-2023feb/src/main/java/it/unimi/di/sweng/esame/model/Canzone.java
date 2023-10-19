package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;

public class Canzone {

	private final String code;
	private final String country;
	private int total = 0;
	private int lastVote = 0;
	private int firstCount = 0;

	public Canzone(@NotNull String code, @NotNull String country) {

		this.code = code;
		this.country = country;
	}

	public Canzone(@NotNull Canzone canzone) {
		this.code = canzone.code;
		this.country = canzone.country;
		this.total = canzone.total;
		this.lastVote = canzone.lastVote;
		this.firstCount = canzone.firstCount;
	}

	public @NotNull String formatClassifica() {
		if(lastVote != 0)
			return String.format("%s [%d] %d",country,lastVote,total);

		return String.format("%s %d",country,total);
	}

	public @NotNull String formatPodio() {
		if(total==0)
			return "---";
		return String.format("%s %d [%d]",country,total,firstCount);
	}

	public @NotNull String getCountry() {
		return this.country;
	}

	public int totalPoints() {
		return total;
	}

	public void vote(int vote){
		total += vote;
		lastVote = vote;

		if(vote == 5) firstCount++;
	}

	public int getFirstCount() {
		return firstCount;
	}
}
