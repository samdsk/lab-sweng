package it.unimi.di.sweng.reverseindex;

import java.util.List;
import java.util.Map;

public interface PrintBeautify {
	String beautify(List<Map.Entry<String, List<Integer>>> list);
}
