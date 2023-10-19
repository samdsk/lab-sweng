package it.unimi.di.sweng.facebuk;

import java.io.Reader;
import java.util.Map;
import java.util.Scanner;

public class NomeCognomeReader implements ReaderStrategy {
	@Override
	public void read(Reader reader, Map<String, Utente> users) {
		Scanner scanner = new Scanner(reader);
		while(scanner.hasNext()){
			String name = scanner.next();
			String surname = scanner.next();
			Utente user = users.getOrDefault(name+" "+surname, UtenteFactory.createUtenteConNomeCognome(name,surname));

			while(scanner.hasNext()){
				String friendName = scanner.next();
				String friendSurname = scanner.next();

				Utente friend = users.getOrDefault(friendName+" "+friendSurname, UtenteFactory.createUtenteConNomeCognome(friendName,friendSurname));

				user.addFriend(friend);

				users.putIfAbsent(friend.toString(),friend);
			}

			users.putIfAbsent(user.toString(),user);
		}
	}
}
