package it.unimi.di.sweng.facebuk;

import org.jetbrains.annotations.NotNull;

public class UtenteConNomeCognome extends Utente {
	public UtenteConNomeCognome(@NotNull String name,@NotNull String surname) {
		super(createUsername(name,surname));
		this.name = name;
		this.surname = surname;
	}

	@Override
	public String toString() {
		return name+" "+surname;
	}
}
