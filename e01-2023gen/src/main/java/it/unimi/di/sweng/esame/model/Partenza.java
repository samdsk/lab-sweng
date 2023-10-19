package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;

public record Partenza(@NotNull Treno treno, @NotNull String destinazione, @NotNull java.time.LocalTime orario, int delay)
		implements Comparable<Partenza>{
	@Override
	public String toString() {
		return String.format("%s dest: [%s] orario: [%s] delay: [%s]",treno, destinazione,orario,delay);
	}
	public String viewFormat(){
		return String.format("%s - %s %s %s",treno.id(), destinazione,orario,delay);
	}
	public Partenza changeDelay(int newDelay) {
		return new Partenza(treno,destinazione,orario,newDelay);
	}

	public int compareTo(Partenza partenza2) {
		// suppongo che i ritardi sono in minuti
		return orario.plusMinutes(delay).compareTo(
				partenza2.orario.plusMinutes(partenza2.delay)
		);
	}
}
