package it.unimi.di.sweng.katamaestro;

import java.util.Map;
import java.util.TreeMap;

public class Vicino implements Observer<String>{
	Map<String,Integer> strumentiCount = new TreeMap<>();
	@Override
	public void update(String state) {
		strumentiCount.put(state,strumentiCount.getOrDefault(state,0)+1);
	}

	public String counts(){
		StringBuilder sb = new StringBuilder();

		for(Map.Entry<String,Integer> instrument : strumentiCount.entrySet()){
			sb.append(instrument.getKey())
					.append(':')
					.append(instrument.getValue())
					.append('\n');
		}

		if(!sb.isEmpty()) sb.deleteCharAt(sb.length()-1);

		return sb.toString();
	}
}
