package binance.model;

import java.util.List;

public class OrderBook {
    private long lastUpdateId;
    private List[] bids;
    private List[] asks;

    public long getLastUpdateId() {
        return lastUpdateId;
    }

    public void setLastUpdateId(long lastUpdateId) {
        this.lastUpdateId = lastUpdateId;
    }

    public List[] getBids() {
        return bids;
    }

    public void setBids(List[] bids) {
        this.bids = bids;
    }

    public List[] getAsks() {
        return asks;
    }

    public void setAsks(List[] asks) {
        this.asks = asks;
    }
}
