package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;

public enum Flag {
	ROSSA("pericolo elevato"),
	GIALLA("rischio medio, solo nuotatori esperti"),
	VERDE("nuoto sicuro"),
	VIOLA("presenza di meduse"),
	NONE("ancora nulla");


	private final String description;

	Flag(@NotNull String description) {

		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
