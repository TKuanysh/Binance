package binance.scheduler;

import binance.common.CacheSymbol;
import binance.common.CacheUsdt;
import binance.model.AbsDelta;
import binance.model.OrderBook;
import binance.model.Ticker;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static binance.common.UrlConstants.CRON_EXP;
import static binance.common.Utils.*;

@Component
public class JobTenSecond {
    @Scheduled(cron = CRON_EXP)
    public void execute() {
        System.out.println("Run Job");
        try {
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

            List<Ticker> tickerArray = reqTicker();

            //Q1 BTS TOP
            List<Ticker> btcTop = tickerArray.stream().filter(t -> {
                if (t.getSymbol().length() > 2)
                    return t.getSymbol().substring(t.getSymbol().length() - 3).contains("BTC");
                return false;
            }).sorted().collect(Collectors.toList()).subList(0, 5);

            for (Ticker t : btcTop) {
                System.out.println(t.toString());
            }

            System.out.println("* * * * * * * * * * * * * * * * * * * ");

            //Q2 USDT TOP
            List<Ticker> usdtTop = tickerArray.stream().filter(t -> {
                if (t.getSymbol().length() > 3)
                    return t.getSymbol().substring(t.getSymbol().length() - 4).contains("USDT");
                return false;
            }).sorted((o1, o2) -> {
                Ticker t1 = o1;
                Ticker t2 = o2;
                if (t1.getCount() < t2.getCount()) return 1;
                else if (t1.getCount() == t2.getCount()) return 0;
                return -1;
            }).collect(Collectors.toList()).subList(0, 5);

            //add to Cache
            CacheUsdt.setInstance(usdtTop);

            for (Ticker t : usdtTop) {
                System.out.println(t.toString());
            }

            //Q3
            for (Ticker t : btcTop) {
                List<Double> topNotionalBids = new ArrayList<>();
                List<Double> topNotionalAsks = new ArrayList<>();
                System.out.println("Symbol: " + t.getSymbol());
                OrderBook orderBook = reqOrderBook(t.getSymbol());

                System.out.println("UpdateId: " + orderBook.getLastUpdateId());

                for (List e : orderBook.getBids()) {
                    System.out.println("BIDS:" + Double.parseDouble(e.get(0).toString()) * Double.parseDouble(e.get(1).toString()));
                    topNotionalBids.add(Double.parseDouble(e.get(0).toString()) * Double.parseDouble(e.get(1).toString()));
                }
                for (List e : orderBook.getAsks()) {
                    topNotionalAsks.add(Double.parseDouble(e.get(0).toString()) * Double.parseDouble(e.get(1).toString()));
                }
                Collections.sort(topNotionalBids, Collections.reverseOrder());
                Collections.sort(topNotionalAsks, Collections.reverseOrder());
                if (topNotionalBids.size() > 199) topNotionalBids.subList(0, 200);
                if (topNotionalAsks.size() > 199) topNotionalAsks.subList(0, 200);

                System.out.println("Notinal Value Bids:");
                for (Double topBids : topNotionalBids) {
                    System.out.println(topBids);
                }
                System.out.println("Notinal Value Asks:");
                for (Double topAsks : topNotionalAsks) {
                    System.out.println(topAsks);
                }
            }

            //Q4
            HashMap<String, AbsDelta> delta = CacheSymbol.getInstance();

            for (Ticker t : CacheUsdt.getInstance()) {
                //Q4
                System.out.println("Spread: " + (t.getAskPrice() - t.getBidPrice()));
                AbsDelta prevDelta = delta.get(t.getSymbol());
                double previous = 0d;
                if (prevDelta != null) {
                    previous = prevDelta.getCurrent();
                }

                AbsDelta abs = new AbsDelta();
                abs.setCurrent(t.getAskPrice() - t.getBidPrice());
                abs.setPrevious(previous);
                delta.put(t.getSymbol(), abs);
                //Q5
                System.out.println(t.getSymbol() + " delta: " + absDelta(t.getAskPrice() - t.getBidPrice(), previous));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
