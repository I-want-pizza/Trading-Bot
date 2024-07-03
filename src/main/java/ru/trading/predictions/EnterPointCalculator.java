package ru.trading.predictions;

import ru.trading.data.Quotation;
import ru.trading.data.Stock;
import ru.trading.data.StockData;

public class EnterPointCalculator {
    private static final float EPS = 0.001f;
    private static final float DAY_CANDLE_SEARCH_INTERVAL = 120;
    private static final float[] FIBONACCI_COEFFICIENTS = new float[]
            {0F, 0.236F, 0.382F, 0.5F, 0.618F, 0.786F, 1F, 1.21F, 1.61F};
    private static final float ERROR_COEFFICIENT = 1.02F;

    private static class Extremums {
        private Quotation min;
        private Quotation max;
    }

    public float[] calculateEnterPoints(Stock stock) {
        Extremums extremums = findExtremums(stock);
        float[] fibonacciLevels = calculateFibonacciLevels(extremums);

        float[] enterPoints = new float[fibonacciLevels.length];
        for (int i = 0; i < fibonacciLevels.length; i++) {
            enterPoints[i] = fibonacciLevels[i] * ERROR_COEFFICIENT;
        }

        return enterPoints;
    }

    private float[] calculateFibonacciLevels(Extremums extremums) {
        float[] levels = new float[FIBONACCI_COEFFICIENTS.length];

        for (int i = 0; i < FIBONACCI_COEFFICIENTS.length; i++) {
            levels[i] = (extremums.max.getCandle().getClosePrice() - extremums.min.getCandle().getClosePrice())
                    * FIBONACCI_COEFFICIENTS[i] + extremums.min.getCandle().getClosePrice();

        }

        return levels;
    }

    private Extremums findExtremums(Stock stock) {
        Extremums transmitter = new Extremums();
        StockData quotations = stock.getStockData();

        for (int i = 0; i < Math.max(quotations.size(), DAY_CANDLE_SEARCH_INTERVAL); i++) {
            Quotation quotation = quotations.get(i);
            if (quotation.getCandle().getClosePrice() > transmitter.max.getCandle().getClosePrice()) {
                transmitter.max = quotation;
                continue;
            }

            if (quotation.getCandle().getClosePrice() < transmitter.min.getCandle().getClosePrice()) {
                transmitter.min = quotation;
            }
        }

        return transmitter;
    }
}