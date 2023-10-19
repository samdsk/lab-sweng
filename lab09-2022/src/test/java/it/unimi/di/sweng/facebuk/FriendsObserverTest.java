package it.unimi.di.sweng.facebuk;

import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

public class FriendsObserverTest {
	// tolto perchè rompe il meccanismo pull di observer
//	@Test
//	void testFriendsObserver() {
//		ObservableFacebuk observableFacebuk = new Facebuk();
//		FriendsObserver friendsObserver = mock(FriendsObserver.class);
//
//		observableFacebuk.addObserver(friendsObserver);
//		observableFacebuk.notifyObservers();
//
//		verify(friendsObserver,times(1)).update(new Facebuk());
//	}

// tolto perchè l'ho creato solo per testare dall'interno facebuk gli amici in comuni
//	@Test
//	void testMutualFriends() {
//		Facebuk SUT = new Facebuk();
//
//		SUT.leggi(new StringReader("Mario Roberta Luca Filippo"));
//		SUT.leggi(new StringReader("Roberta Mario Luca Filippo"));
//
//		FriendsInCommonStrategy friendsInCommonStrategy = new NamesOfCommmonFriends();
//		SUT.setFriendsInCommonStrategy(friendsInCommonStrategy);
//
//		assertThat(SUT.friendsInCommon("Mario","Roberta")).isEqualTo("[Luca, Filippo]");
//
//		assertThat(SUT.printMutualFriends(SUT.generateCommonFriends()))
//				.isEqualTo(
//				"""
//						Roberta Luca [Mario]
//						Roberta Filippo [Mario]
//						Roberta Mario [Luca, Filippo]
//						Luca Filippo [Mario, Roberta]
//						Luca Mario [Roberta]
//						Filippo Mario [Roberta]
//						""");
//
//	}

	@Test
	void testObserverChangeState() {
		Facebuk facebuk = new Facebuk();
		FriendsObserver SUT = new FriendsObserver(facebuk);

		facebuk.leggi(new StringReader("Mario Roberta Luca Filippo"));
		facebuk.leggi(new StringReader("Roberta Mario Luca"));

		FriendsInCommonStrategy friendsInCommonStrategy = new NumberOfCommonFriends();
		facebuk.setFriendsInCommonStrategy(friendsInCommonStrategy);

		facebuk.notifyObservers();

		assertThat(SUT.printCommonFriends())
				.isEqualTo(
					"""
						Roberta Luca 1
						Roberta Filippo 1
						Roberta Mario 1
						Luca Filippo 1
						Luca Mario 1
						Filippo Mario 0
						""");

		facebuk.leggi(new StringReader("Roberta Mario Luca Filippo Anna"));

		facebuk.notifyObservers();

		assertThat(SUT.printCommonFriends())
				.isEqualTo(
					"""
						Roberta Luca 1
						Roberta Filippo 1
						Roberta Mario 2
						Roberta Anna 0
						Luca Filippo 2
						Luca Mario 1
						Luca Anna 1
						Filippo Mario 1
						Filippo Anna 1
						Mario Anna 1
							""");

	}
	@Test
	void testObserverPrintStrategy() {
		Facebuk facebuk = new Facebuk();
		FriendsObserver SUT = new FriendsObserver(facebuk);

		facebuk.leggi(new StringReader("Mario Roberta Luca Filippo"));
		facebuk.leggi(new StringReader("Roberta Mario Luca"));

		FriendsInCommonStrategy friendsInCommonStrategy = new NumberOfCommonFriends();
		facebuk.setFriendsInCommonStrategy(friendsInCommonStrategy);
		PrintOrderSrategy printOrderSrategy = new AlphabeticalOrder();

		SUT.setPrintOrderStrategy(printOrderSrategy);

		facebuk.notifyObservers();

		assertThat(SUT.printCommonFriends())
				.isEqualTo(
					"""
						Filippo Mario 0
						Luca Filippo 1
						Luca Mario 1
						Roberta Filippo 1
						Roberta Luca 1
						Roberta Mario 1
						""");
	}
}
