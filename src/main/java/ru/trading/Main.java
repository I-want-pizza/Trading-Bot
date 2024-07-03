package ru.trading;

import ru.trading.data.*;
import ru.trading.techanalisis.TechAnalis;
import ru.trading.data.API;
import ru.trading.data.Constants;
import ru.trading.data.Stock;
import ru.trading.data.StockData;
import ru.trading.predictions.EnterPointCalculator;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import static ru.trading.data.API.getHistoricalData;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
//        String symbol = "NVDA"; // Apple on US exchange
//        Calendar from = Calendar.getInstance();
//        Calendar to = Calendar.getInstance();
//
//        // Example: Fetch data from the last 200 days (no holidays)
//        from.add(Calendar.DAY_OF_YEAR, -360);
//
//        StockData historicalData = getHistoricalData(symbol, from, to);
//        if (historicalData.isEmpty()) {
//            System.out.println("No data found.");
//        } else {
//            for (Quotation quotation : historicalData) {
//                System.out.println(quotation.getCandle() + " " + quotation.getDate());
//            }
//        }
//        TechAnalis techAnalis = new TechAnalis(historicalData);
//        float[] points = techAnalis.entryPoints();
//        System.out.println(Arrays.toString(points));

        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.timePattern);
        start.setTime(sdf.parse("2030-01-01"));
        end.setTime(sdf.parse("2001-01-01"));
        StockData data = API.getHistoricalData("IBM", start, end);
        System.out.println(data);

        EnterPointCalculator calculator = new EnterPointCalculator();

        Stock stock = new Stock("IBM", data);
        System.out.println(Arrays.toString(calculator.calculateEnterPoints(stock)));

    }
}