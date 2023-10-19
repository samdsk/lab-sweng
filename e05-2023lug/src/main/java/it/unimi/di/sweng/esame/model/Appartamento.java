package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;

public record Appartamento(@NotNull String codice, @NotNull Tecnico tecnico) {
	public Appartamento {

		if(codice.length()!=4 || !Character.isLetter(codice.charAt(0)))
			throw new IllegalArgumentException("Codice appartamento non valido");

		int letters = 0;
		for (char c : codice.toCharArray()) {
			if(Character.isLetter(c)) letters++;
		}

		if(letters>1)
			throw new IllegalArgumentException("Codice appartamento non valido");
	}
}
