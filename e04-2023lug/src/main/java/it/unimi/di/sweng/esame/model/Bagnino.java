package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;

public record Bagnino(@NotNull String name) {
	public Bagnino {
		if(name.length()<1)
			throw new IllegalArgumentException("nome vuoto");

		if(name.length()>30)
			throw new IllegalArgumentException("nome troppo lungo");
	}
}
