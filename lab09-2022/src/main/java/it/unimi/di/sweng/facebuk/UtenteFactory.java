package it.unimi.di.sweng.facebuk;

public class UtenteFactory {
	static Utente createUtenteConUsername(String username){
		return new UtenteConUserName(username);
	}
	static Utente createUtenteConNomeCognome(String name, String surname){
		return new UtenteConNomeCognome(name,surname);
	}
}
