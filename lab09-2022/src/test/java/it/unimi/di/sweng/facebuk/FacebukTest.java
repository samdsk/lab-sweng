package it.unimi.di.sweng.facebuk;

import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FacebukTest {
	@Test
	void testFacebukUtenti() {
		Facebuk SUT = new Facebuk();

		Utente u1 = mock(Utente.class);
		when(u1.getUsername()).thenReturn("mario");
		Utente u2 = mock(Utente.class);
		when(u2.getUsername()).thenReturn("sam");

		SUT.addUser(u1);
		SUT.addUser(u2);

		assertThat(SUT.userCount()).isEqualTo(2);
	}

	@Test
	void testAddUsersByString() {
		Facebuk SUT = new Facebuk();

		SUT.leggi(new StringReader("Mario Roberta Luca Filippo"));

		assertThat(SUT.userFriendsToString("Mario")).isEqualTo("Mario : [Roberta, Luca, Filippo]");

	}

	@Test
	void testAddUsersByStringDuplicateUser() {
		Facebuk SUT = new Facebuk();

		SUT.leggi(new StringReader("Mario Roberta Luca Filippo"));
		SUT.leggi(new StringReader("Roberta Roberta Luca Filippo"));

		assertThat(SUT.userFriendsToString("Mario")).isEqualTo("Mario : [Roberta, Luca, Filippo]");
		assertThat(SUT.userFriendsToString("Roberta")).isEqualTo("Roberta : [Mario, Luca, Filippo]");

	}

	@Test
	void testFriendsInCommon() {
		Facebuk SUT = new Facebuk();

		SUT.leggi(new StringReader("Mario Roberta Luca Filippo"));
		SUT.leggi(new StringReader("Roberta Mario Luca Filippo"));

		FriendsInCommonStrategy friendsInCommonStrategy = new NamesOfCommmonFriends();
		SUT.setFriendsInCommonStrategy(friendsInCommonStrategy);

		assertThat(SUT.friendsInCommon("Mario","Roberta")).isEqualTo("[Luca, Filippo]");

	}
	@Test
	void testFriendsInCommonCountStrategy() {
		Facebuk SUT = new Facebuk();

		SUT.leggi(new StringReader("Mario Roberta Luca Filippo"));
		SUT.leggi(new StringReader("Roberta Mario Luca Filippo"));

		FriendsInCommonStrategy friendsInCommonStrategy = new NumberOfCommonFriends();

		SUT.setFriendsInCommonStrategy(friendsInCommonStrategy);

		assertThat(SUT.friendsInCommon("Mario","Roberta")).isEqualTo("2");

	}

	@Test
	void testNomeCognomeScanner() {
		Facebuk SUT = new Facebuk();
		FriendsInCommonStrategy friendsInCommonStrategy = new NumberOfCommonFriends();
		ReaderStrategy readerStrategy = new NomeCognomeReader();

		SUT.setReaderStrategy(readerStrategy);
		SUT.setFriendsInCommonStrategy(friendsInCommonStrategy);

		SUT.leggi(new StringReader("Mario Rossi Roberta Rossi Luca Filippi"));
		SUT.leggi(new StringReader("Roberta Rossi Mario Rossi Luca Filippi"));


		assertThat(SUT.friendsInCommon("Mario Rossi","Roberta Rossi")).isEqualTo("1");
	}
}
