package it.unimi.di.sweng.reverseindex;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OrderStrategy {
	List<Map.Entry<String, List<Integer>>> order(Set<Map.Entry<String, List<Integer>>> entries);
}
