package it.unimi.di.sweng.reverseindex;

import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReverseIndexTest {
	@Test
	void testReverseIndex() {
		Lettore lettore = mock(LettoreNormale.class);
		when(lettore.iterator()).thenReturn(List.of("sotto la panca","la capra campa").iterator());

		ReverseIndex reverseIndex = new ReverseIndexSimple(lettore);

		assertThat(reverseIndex.getWordsCount()).isEqualTo(5);

	}

	@Test
	void testOrderStrategyAlphabeticalOrder() {
		OrderStrategy orderStrategy = new WordsInAlphabetical();

		Map<String,List<Integer>> map = new HashMap<>();
		map.put("la",List.of(0));
		map.put("sotto",List.of(0));
		map.put("panca",List.of(0));

		List<Map.Entry<String,List<Integer>>> list = orderStrategy.order(map.entrySet());

		List<String> words = List.of("la","panca","sotto");

		for (int i = 0; i < list.size(); i++) {
			assertThat(list.get(i).getKey()).isEqualTo(words.get(i));
		}
	}

	@Test
	void testOrderStrategyListSizeOrder() {
		OrderStrategy orderStrategy = new IndexListLength();

		List<List<Integer>> indexes = List.of(List.of(0),List.of(0,1,2),List.of(0,1));

		Map<String,List<Integer>> map = new HashMap<>();
		map.put("la",indexes.get(0));
		map.put("sotto",indexes.get(1));
		map.put("panca",indexes.get(2));

		List<Map.Entry<String,List<Integer>>> list = orderStrategy.order(map.entrySet());


		for (int i = 0; i < list.size(); i++) {
			if(i == 0)
				assertThat(list.get(i).getValue()).isEqualTo(indexes.get(1));
			if(i == 1)
				assertThat(list.get(i).getValue()).isEqualTo(indexes.get(2));
			if(i == 2)
				assertThat(list.get(i).getValue()).isEqualTo(indexes.get(0));

		}
	}

	@Test
	void testReverseIndexPrintOrderAlphabeticle() {
		Lettore lettore = mock(LettoreNormale.class);
		when(lettore.iterator()).thenReturn(List.of("sotto la panca").iterator());

		ReverseIndex reverseIndex = new ReverseIndexSimple(lettore);

		OrderStrategy orderStrategy = new WordsInAlphabetical();

		reverseIndex.setOrderingStrategy(orderStrategy);

		assertThat(reverseIndex.toString()).isEqualTo("la [0]\npanca [0]\nsotto [0]");

	}
	@Test
	void testReverseIndexPrintOrderListLength() {
		Lettore lettore = new LettoreNormale();
		String input = "sotto la panca\nla capra\nla sotto";

		lettore.leggiInput(new StringReader(input));

		ReverseIndex reverseIndex = new ReverseIndexSimple(lettore);

		OrderStrategy orderStrategy = new IndexListLength();
		reverseIndex.setOrderingStrategy(orderStrategy);

		assertThat(reverseIndex.toString()).isEqualTo("la [0, 1, 2]\nsotto [0, 2]\ncapra [1]\npanca [0]");

	}

	@Test
	void testPrintBeautifyAligned() {
		List<List<Integer>> indexes = List.of(List.of(0),List.of(0,1,2),List.of(0,1));

		Map<String,List<Integer>> map = new HashMap<>();
		map.put("la",indexes.get(0));
		map.put("sotto",indexes.get(1));
		map.put("panca",indexes.get(2));

		PrintBeautify printBeautify = new AlignInColumns();

		OrderStrategy orderStrategy = new IndexListLength();

		assertThat(printBeautify.beautify(orderStrategy.order(map.entrySet())))
				.isEqualTo("sotto [0, 1, 2]\npanca  [0, 1]\nla     [0]");
	}

	@Test
	void testPrintBeautifyNormal() {
		Map<String,List<Integer>> map = new HashMap<>();
		map.put("la",List.of(0));
		map.put("sotto",List.of(0));
		map.put("panca",List.of(0));

		PrintBeautify printBeautify = new PrintNormal();

		OrderStrategy orderStrategy = new WordsInAlphabetical();

		assertThat(printBeautify.beautify(orderStrategy.order(map.entrySet())))
				.isEqualTo("la [0]\npanca [0]\nsotto [0]");

	}

	@Test
	void testReverseIndexBeautifyAligned() {
		Lettore lettore = new LettoreNormale();
		String input = "sotto la panca\nla capra\nla sotto";

		lettore.leggiInput(new StringReader(input));

		ReverseIndex reverseIndex = new ReverseIndexSimple(lettore);

		OrderStrategy orderStrategy = new IndexListLength();
		reverseIndex.setOrderingStrategy(orderStrategy);

		PrintBeautify printBeautify = new AlignInColumns();
		reverseIndex.setPrintBeautify(printBeautify);

		assertThat(reverseIndex.toString()).isEqualTo("la    [0, 1, 2]\nsotto  [0, 2]\ncapra  [1]\npanca  [0]");

	}
}