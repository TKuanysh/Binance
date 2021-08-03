package binance.common;

import binance.model.AbsDelta;
import java.util.HashMap;

public class CacheSymbol {
    private static HashMap<String, AbsDelta> instance;

    public static synchronized HashMap<String, AbsDelta> getInstance() {
        if (instance == null) {
            instance = new HashMap<>();
        }
        return instance;
    }
}
