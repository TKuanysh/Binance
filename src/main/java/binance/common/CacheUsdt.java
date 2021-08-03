package binance.common;

import binance.model.Ticker;

import java.util.ArrayList;
import java.util.List;

public class CacheUsdt {

    private static List<Ticker> instance;

    public static synchronized List<Ticker> getInstance() {
        if (instance == null) {
            instance = new ArrayList<>();
        }
        return instance;
    }

    public static void setInstance(List<Ticker> instance) {
        CacheUsdt.instance = instance;
    }
}
