package it.unimi.di.sweng.facebuk;

import java.io.Reader;
import java.util.Map;
import java.util.Scanner;

public class usernameReader implements ReaderStrategy {
	@Override
	public void read(Reader reader, Map<String, Utente> users) {
		Scanner scanner = new Scanner(reader);

		while(scanner.hasNext()){
			String username = scanner.next();

			Utente user = users.getOrDefault(username, UtenteFactory.createUtenteConUsername(username));


			while(scanner.hasNext()){
				String friendUsername = scanner.next();
				Utente friend = users.getOrDefault(friendUsername, UtenteFactory.createUtenteConUsername(friendUsername));

				user.addFriend(friend);

				users.putIfAbsent(friend.getUsername(),friend);
			}

			users.putIfAbsent(user.getUsername(),user);
		}
	}
}
