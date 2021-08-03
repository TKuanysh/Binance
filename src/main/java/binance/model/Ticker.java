package binance.model;

public class Ticker implements Comparable {
    private String symbol;
    private double volume;
    private int count;
    private double bidPrice;
    private double askPrice;

    public Ticker(String symbol, double volume, int count) {
        this.symbol = symbol;
        this.volume = volume;
        this.count = count;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getVolume() {
        return volume;
    }

    public int getCount() {
        return count;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public double getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(double askPrice) {
        this.askPrice = askPrice;
    }

    @Override
    public String toString() {
        return "model.Ticker [symbol=" + symbol + ", volume=" + volume + ", count=" + count + ", bidPrice=" + bidPrice + ", askPrice=" + askPrice + "]";
    }

    @Override
    public int compareTo(Object o) {
        Ticker t = (Ticker) o;
        if (this.volume < t.volume) return 1;
        else if (this.volume == t.volume) return 0;
        return -1;
    }
}
