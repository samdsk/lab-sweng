package it.unimi.di.sweng.reverseindex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReverseIndexSimple implements ReverseIndex {
	private final Map<String, List<Integer>> wordsToIndexes = new HashMap<>();
	private OrderStrategy orderStrategy;
	private PrintBeautify printBeautify = new PrintNormal();

	public ReverseIndexSimple(Lettore lettore) {
		int count = 0;
		for(String phrase : lettore){
			String[] words = phrase.split(" ");
			for(String word : words){

				if(!wordsToIndexes.containsKey(word)){
					wordsToIndexes.put(word,new ArrayList<>());
				}

				if(!wordsToIndexes.get(word).contains(count)){
					wordsToIndexes.get(word).add(count);
				}

			}
			count++;
		}
	}

	@Override
	public int getWordsCount() {
		return wordsToIndexes.size();
	}

	@Override
	public void setOrderingStrategy(OrderStrategy orderStrategy) {
		this.orderStrategy = orderStrategy;
	}

	@Override
	public void setPrintBeautify(PrintBeautify printBeautify) {
		this.printBeautify = printBeautify;
	}

	@Override
	public String toString() {
		List<Map.Entry<String,List<Integer>>> list = orderStrategy.order(wordsToIndexes.entrySet());
		return printBeautify.beautify(list);
	}
}
