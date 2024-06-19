package ru.trading.techanalisis;

import ru.trading.data.Candle;
import ru.trading.data.StockData;

public class TechAnalis {
    static final int COUNT_DAYS = 120;
    private static StockData stockData;
    private static Range range = findMinMaxPrices();
    private static Range findMinMaxPrices(){
        if (stockData.isEmpty()){
            return null;
        }
        float minPrice = stockData.get(0).getCandle().getMinPrice();
        float maxPrice = stockData.get(0).getCandle().getMaxPrice();
        int minIndex = 0;
        int maxIndex = 0;
        for (int i = 1; i < stockData.size() && i < COUNT_DAYS; i++){
            Candle candle = stockData.get(i).getCandle();
            if(candle.getMinPrice() < minPrice){
                minPrice = candle.getMinPrice();
                minIndex = i;
            }
            if(candle.getMaxPrice() > maxPrice){
                maxPrice = candle.getMaxPrice();
                maxIndex = i;
            }
        }
        return new Range(minPrice, minIndex, maxPrice, maxIndex);
    }

    private static class Range{
        private float minPrice;
        private float maxPrice;
        private int maxIndex;
        private int minIndex;
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
static final float ONE_LEVEL_FIB = 0.786f;
static final float TWO_LEVEL_FIB = 0.618f;
static final float THREE_LEVEL_FIB = 0.5f;
static final float FOUR_LEVEL_FIB = 0.382f;
static final float FIVE_LEVEL_FIB = 0.236f;
    private float[] buildingFibonacciLevels(){
        float delta = range.getMaxPrice() - range.getMinPrice();
        float[] fibonacciLevels = new float[7];
        fibonacciLevels [0] = range.minPrice;
        fibonacciLevels [6] = range.maxPrice;
        fibonacciLevels [1] = delta * ONE_LEVEL_FIB + range.minPrice;
        fibonacciLevels [2] = delta * TWO_LEVEL_FIB + range.minPrice;
        fibonacciLevels [3] = delta * THREE_LEVEL_FIB + range.minPrice;
        fibonacciLevels [4] = delta * FOUR_LEVEL_FIB + range.minPrice;
        fibonacciLevels [5] = delta * FIVE_LEVEL_FIB + range.minPrice;
        return fibonacciLevels;
    }

    private boolean isTrend(){
        return range.maxIndex - range.minIndex > 0;
    }

    private float[] entryPoints(){
        float[] entryPoints = new float[14];
        float[] fibLevels = buildingFibonacciLevels();
        for (int i = 0; i < entryPoints.length; i++){
            if (i % 2 == 0){
                entryPoints[i] = fibLevels[i/2];
            } else{
                entryPoints[i] = fibLevels[(i-1)/2] * 0.015f;
            }
        }
        return entryPoints;
    }

}
