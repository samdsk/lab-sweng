package it.unimi.di.sweng.reverseindex;

import java.util.*;

public class IndexListLength implements OrderStrategy {
	@Override
	public List<Map.Entry<String, List<Integer>>> order(Set<Map.Entry<String, List<Integer>>> entries) {
		List<Map.Entry<String, List<Integer>>> list = new ArrayList<>(entries);
		Comparator<Map.Entry<String, List<Integer>>> keyOrder =
				(Map.Entry<String, List<Integer>> o1, Map.Entry<String, List<Integer>> o2) ->
						o2.getValue().size() - o1.getValue().size();

		list.sort(keyOrder);

		return list;
	}
}
