package ru.trading.data;


import java.util.LinkedList;

public class StockData extends LinkedList<Quotation> {
    private DataScale scale;

    public StockData toHour() {
        return null;
    }

    public StockData toDay() {
        return null;
    }

    public StockData toWeek() {
        return null;
    }

    public StockData toMonth() {
        return null;
    }
}
