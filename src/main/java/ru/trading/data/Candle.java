package ru.trading.data;

public class Candle {
    private final float openPrice;
    private final float closePrice;
    private final float maxPrice;
    private final float minPrice;
    public Candle(float openPrice, float closePrice, float maxPrice, float minPrice) {
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
    }
    @Override
    public String toString() {
        return "Candle{" +
                "openPrice=" + openPrice +
                ", closePrice=" + closePrice +
                ", maxPrice=" + maxPrice +
                ", minPrice=" + minPrice +
                '}';
    }
    public float getVolume() {
        return openPrice - closePrice;
    }
    public float getOpenPrice() {
        return openPrice;
    }
    public float getClosePrice() {
        return closePrice;
    }
    public float getMaxPrice() {
        return maxPrice;
    }
    public float getMinPrice() {
        return minPrice;
    }
}
