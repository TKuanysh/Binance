package binance.common;

import binance.http.HttpClient;
import binance.model.OrderBook;
import binance.model.Ticker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import static binance.common.UrlConstants.API_ORDER_BOOK;
import static binance.common.UrlConstants.API_TICKER;
import static java.lang.Math.abs;

public class Utils {

    public static double absDelta(double cur, double prev) {
        if (prev != 0) {
            if (cur == prev) {
                return 0;
            } else return (abs(cur - prev) / prev) * 100.0;
        } else return 0d / 0d;
    }

    public static List<Ticker> reqTicker() {
        List<Ticker> resArray = null;

        try {
            Type tickerListType = new TypeToken<List<Ticker>>() {
            }.getType();
            resArray = new Gson().fromJson(HttpClient.sendRequest(API_TICKER), tickerListType);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resArray;

    }

    public static OrderBook reqOrderBook(String symbol) {
        OrderBook resOrderBook = null;
        try {
            resOrderBook = new Gson().fromJson(HttpClient.sendRequest(API_ORDER_BOOK + symbol), OrderBook.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resOrderBook;
    }


}
