package it.unimi.di.sweng.facebuk;

import java.util.List;
import java.util.Map;

public class AlphabeticalOrder implements PrintOrderSrategy{
    @Override
    public void order(List<Map.Entry<String, String>> list) {
        list.sort(Map.Entry.comparingByKey());

    }
}
