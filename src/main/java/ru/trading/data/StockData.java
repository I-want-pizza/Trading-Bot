package ru.trading.data;

import ru.trading.data.Constants;

import java.util.Calendar;
import java.util.LinkedList;

public class StockData extends LinkedList<Quotation> {
    public StockData() {
        this.scale = DataScale.MINUTE;
    }

    public StockData(DataScale scale) {
        this.scale = scale;
    }

    private final DataScale scale;

    private StockData toInterval(int minInterval, DataScale scale) {
        StockData data = new StockData(scale);
        int size = this.size() / minInterval;
        for (int i = 0; i < size; i++) {
            Quotation m = this.get(i);
            Calendar date = m.getDate();
            float open = m.getCandle().getOpenPrice();
            float max = m.getCandle().getMaxPrice();
            float min = m.getCandle().getMinPrice();
            for (int j = 0; j < minInterval; j++) {
                if (max < this.get(i + j).getCandle().getMaxPrice()) {
                    max = this.get(i + j).getCandle().getMaxPrice();
                }
                if (min > this.get(i + j).getCandle().getMinPrice()) {
                    min = this.get(i + j).getCandle().getMinPrice();
                }
            }
            float close = this.get(minInterval - 1).getCandle().getClosePrice();
            data.add(new Quotation(date, new Candle(open, close, max, min)));
        }
        return data;
    }


    public StockData toWeek() {
        return toInterval(Constants.MinutesInWeek, DataScale.WEEK);
    }

    public StockData toMonth() {
        return toInterval(Constants.MinutesInMonth, DataScale.MONTH);
    }
}
