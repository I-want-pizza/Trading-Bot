package ru.trading.techanalisis;

import ru.trading.data.Candle;
import ru.trading.data.StockData;


public class TechAnalis {
    private static final int COUNT_DAYS = 360;
    private static final float ONE_LEVEL_FIB = 0.786f;
    private static final float TWO_LEVEL_FIB = 0.618f;
    private static final float THREE_LEVEL_FIB = 0.5f;
    private static final float FOUR_LEVEL_FIB = 0.382f;
    private static final float FIVE_LEVEL_FIB = 0.236f;
    private static final float ENTRY_POINT_OFFSET = 0.015f;
    private final Range range;

    public TechAnalis(StockData stockData) {
        this.range = findMinMaxPrices(stockData);
    }

    private static Range findMinMaxPrices(StockData stockData) {
        if (stockData.isEmpty()) {
            return null;
        }
        float minPrice = 99999f;
        float maxPrice = -9999f;
        int minIndex = 0;
        int maxIndex = 0;
        for (int i = 1; i < stockData.size() && i < COUNT_DAYS; i++) {
            Candle candle = stockData.get(i).getCandle();
            if (candle.getMinPrice() < minPrice) {
                minPrice = candle.getMinPrice();
                minIndex = i;
            }
            if (candle.getMaxPrice() > maxPrice) {
                maxPrice = candle.getMaxPrice();
                maxIndex = i;
            }
        }
        return new Range(minPrice, minIndex, maxPrice, maxIndex);
    }

    private float[] buildingFibonacciLevels() {
        if (range == null) {
            return new float[0]; // Возвращаем пустой массив, если range не определен
        }
        float delta = range.getMaxPrice() - range.getMinPrice();
        float[] fibonacciLevels = new float[7];
        fibonacciLevels[6] = range.getMinPrice();
        fibonacciLevels[0] = range.getMaxPrice();
        fibonacciLevels[1] = delta * ONE_LEVEL_FIB + range.getMinPrice();
        fibonacciLevels[2] = delta * TWO_LEVEL_FIB + range.getMinPrice();
        fibonacciLevels[3] = delta * THREE_LEVEL_FIB + range.getMinPrice();
        fibonacciLevels[4] = delta * FOUR_LEVEL_FIB + range.getMinPrice();
        fibonacciLevels[5] = delta * FIVE_LEVEL_FIB + range.getMinPrice();
        return fibonacciLevels;
    }

    public boolean isTrend() {
        if (range == null) {
            return false;
        }
        return range.getMaxIndex() - range.getMinIndex() > 0;
    }

    public float[] entryPoints() {
        float[] fibLevels = buildingFibonacciLevels();
        float[] entryPoints = new float[fibLevels.length * 2];
        for (int i = 0; i < fibLevels.length; i++) {
            entryPoints[i * 2] = fibLevels[i];
            entryPoints[i * 2 + 1] = fibLevels[i] * (1 + ENTRY_POINT_OFFSET);
        }
        return entryPoints;
    }

    private static class Range {
        private final float minPrice;
        private final float maxPrice;
        private final int maxIndex;
        private final int minIndex;

        public Range(float minPrice, int minIndex, float maxPrice, int maxIndex) {
            this.minPrice = minPrice;
            this.minIndex = minIndex;
            this.maxPrice = maxPrice;
            this.maxIndex = maxIndex;
        }

        public float getMinPrice() {
            return minPrice;
        }

        public int getMinIndex() {
            return minIndex;
        }

        public float getMaxPrice() {
            return maxPrice;
        }

        public int getMaxIndex() {
            return maxIndex;
        }
    }
}