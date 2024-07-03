package ru.trading.data;

public class Stock {
    private String name;
    private StockData stockData;

    public Stock(String name, StockData stockData) {
        this.name = name;
        this.stockData = stockData;
    }

    public String getName() {
        return name;
    }

    public StockData getStockData() {
        return stockData;
    }
}
