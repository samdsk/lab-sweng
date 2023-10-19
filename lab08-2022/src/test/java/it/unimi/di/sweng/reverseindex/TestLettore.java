package it.unimi.di.sweng.reverseindex;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class TestLettore {
	@Test
	void testLettoreNormale() {
		String input = "sotto la panca\n"+"la capra campa";
		Lettore SUT = new LettoreNormale();
		SUT.leggiInput(new StringReader(input));

		assertThat(SUT.getDocumentCount()).isEqualTo(2);
	}

	@Test
	void testLettoreEliminaPunteggiatura() {
		String input = "sotto, la panca!";

		Lettore SUT = new LettoreEliminaPunteggiatura();
		SUT.leggiInput(new StringReader(input));

		assertThat(SUT.getDocument(0)).isEqualTo("sotto la panca");

	}

	@Test
	void testStopWord() {
		String input = "sotto, la panca!";
		Lettore SUT = new LettoreEliminaPunteggiatura();

		SUT.setStopWords(List.of("la"));
		SUT.leggiInput(new StringReader(input));

		assertThat(SUT.getDocument(0)).isEqualTo("sotto panca");

	}

	@Test
	void testLettoreIterable() {
		String input = "sotto la panca\nla capra campa";
		Lettore SUT = new LettoreNormale();
		SUT.leggiInput(new StringReader(input));

		assertThat(SUT).containsExactly("sotto la panca","la capra campa");
	}

}
