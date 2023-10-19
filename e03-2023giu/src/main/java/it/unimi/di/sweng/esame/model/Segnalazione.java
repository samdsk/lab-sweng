package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;

public final class Segnalazione {
	private final @NotNull String descrizione;
	private final boolean risolto;
	private final Posizione posizione;

	private Segnalazione(@NotNull Posizione posizione, @NotNull String descrizione, boolean risolto) {
		this.posizione = posizione;
		this.descrizione = descrizione;
		this.risolto = risolto;
	}

	public @NotNull String formatDisplay() {
		return String.format("%s sulla %s", descrizione, posizione.formatDisplay());
	}

	public Segnalazione(@NotNull Posizione posizione,@NotNull String descrizione) {
		this.posizione = posizione;
		this.descrizione = descrizione;
		this.risolto = false;
	}

	public Segnalazione segnalaComeRisolto() {
		return new Segnalazione(posizione,descrizione,true);
	}


	public boolean isRisolto() {
		return this.risolto;
	}

	public Posizione getPosizione() {
		return this.posizione;
	}
}
