package ru.trading.data;

public class Candle {
    private float openPrice;
    private float closePrice;
    private float maxPrice;
    private float minPrice;

    public float getVolume() {
        return openPrice - closePrice;
    }

}
