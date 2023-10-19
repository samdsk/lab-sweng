package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;

public enum Descrizione {
	INCIDENTE("incidente"),
	RIDUZIONE_DELLA_CARREGGIATA("riduzione della carreggiata"),
	TRAFFICO_INTENSO("traffico intenso");

	private final String descrizione;

	Descrizione(@NotNull String descrizione) {

		this.descrizione = descrizione;
	}

	@Override
	public String toString() {
		return descrizione;
	}
}
