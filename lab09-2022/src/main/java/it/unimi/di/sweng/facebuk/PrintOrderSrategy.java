package it.unimi.di.sweng.facebuk;

import java.util.List;
import java.util.Map;

public interface PrintOrderSrategy {
    void order(List<Map.Entry<String,String>> list);

    static PrintOrderSrategy NO_REORDER = list -> {};
}
