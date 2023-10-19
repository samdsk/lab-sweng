package it.unimi.di.sweng.reverseindex;

import java.util.List;
import java.util.Map;

public class PrintNormal implements PrintBeautify {
	@Override
	public String beautify(List<Map.Entry<String, List<Integer>>> list) {
		StringBuilder sb = new StringBuilder();

		for(Map.Entry<String,List<Integer>> entry : list){
			String key = entry.getKey();
			sb.append(key).append(' ');
			sb.append(entry.getValue().toString());

			sb.append('\n');
		}

		if(!sb.isEmpty())
			sb.deleteCharAt(sb.length()-1);

		return sb.toString();
	}
}
