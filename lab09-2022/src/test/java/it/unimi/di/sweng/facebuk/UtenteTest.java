package it.unimi.di.sweng.facebuk;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class UtenteTest {

	@Test
	void testUtente() {
		Utente SUT = new UtenteConUserName("Mario");
		assertThat(SUT.toString()).isEqualTo("Mario");
	}
	@Test
	void testUtenteNomeCognome() {
		Utente SUT = new UtenteConNomeCognome("Mario","Rossi");
		assertThat(SUT.toString()).isEqualTo("Mario Rossi");
	}

	@Test
	void testListAmici() {
		Utente SUT = new UtenteConUserName("mario");

		SUT.addFriend(new UtenteConUserName("sam91"));
		SUT.addFriend(new UtenteConUserName("marco24"));

		assertThat(SUT.friendsToString()).isEqualTo("mario : [sam91, marco24]");
	}

	@Test
	void testFriendsIterator() {
		Utente SUT = new UtenteConUserName("mario");
		Utente f1 = new UtenteConUserName("sam91");
		Utente f2 = new UtenteConUserName("marco24");

		SUT.addFriend(f1);
		SUT.addFriend(f2);

		assertThat(SUT).containsExactly(f1,f2);
	}

	@Test
	void testHoManyFriends() {
		Utente SUT = new UtenteConUserName("mario");
		Utente f1 = new UtenteConUserName("sam91");
		Utente f2 = new UtenteConUserName("marco24");
		Utente f3 = new UtenteConUserName("anna3453");

		SUT.addFriend(f1);
		SUT.addFriend(f2);
		SUT.addFriend(f2);
		SUT.addFriend(f3);

		assertThat(SUT.friendsCount()).isEqualTo(3);
	}

	@Test
	void testAggiungiAmicoBidirezionale() {
		Utente SUT1 = new UtenteConUserName("mario");
		Utente SUT2 = new UtenteConUserName("sam");

		SUT1.addFriend(SUT2);

		assertThat(SUT1.isFriend(SUT2)).isEqualTo(true);
		assertThat(SUT2.isFriend(SUT1)).isEqualTo(true);
	}
}
