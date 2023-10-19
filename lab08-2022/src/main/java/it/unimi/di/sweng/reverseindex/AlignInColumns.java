package it.unimi.di.sweng.reverseindex;

import java.util.List;
import java.util.Map;

public class AlignInColumns implements PrintBeautify {
	@Override
	public String beautify(List<Map.Entry<String, List<Integer>>> list) {

		StringBuilder sb = new StringBuilder();
		int max = maxLength(list);

		int count = 0;

		for(Map.Entry<String,List<Integer>> entry : list){
			String key = entry.getKey();
			sb.append(key);
			sb.append(" ".repeat(max-key.length() +  (count == 0 ? 1 : 2)));
			sb.append(entry.getValue().toString());

			sb.append('\n');
			count++;
		}

		if(!sb.isEmpty())
			sb.deleteCharAt(sb.length()-1);

		return sb.toString();
	}

	private int maxLength(List<Map.Entry<String, List<Integer>>> list){
		int max = Integer.MIN_VALUE;

		for(Map.Entry<String, List<Integer>> entry : list){
			max = Math.max(entry.getKey().length(),max);
		}

		return max;
	}
}
