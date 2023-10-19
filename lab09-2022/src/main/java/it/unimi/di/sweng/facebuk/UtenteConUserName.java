package it.unimi.di.sweng.facebuk;

import org.jetbrains.annotations.NotNull;

public class UtenteConUserName extends Utente {

	public UtenteConUserName(@NotNull String username) {
		super(username);
	}

	@Override
	public String toString() {
		return username;
	}

}
