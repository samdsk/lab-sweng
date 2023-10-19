package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;

public record Posizione(@NotNull String tratto, int posizione) {
	public Posizione {
		if (posizione < 0) throw new IllegalArgumentException("Posizione deve essere positiva");
	}

	public String formatDisplay() {
		return String.format("%s al Km %d",tratto,posizione);
	}
}
