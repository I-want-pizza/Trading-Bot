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
        // Getting historical data example
        String symbol = "NVDA"; // Nvidia on US exchange
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();

        // Fetch data from the last year (no holidays)
        from.add(Calendar.DAY_OF_YEAR, -360);
        StockData historicalData = getHistoricalData(symbol, from, to);

//        Printing fetched stock data
//        if (historicalData.isEmpty()) {
//            System.out.println("No data found.");
//        } else {
//            for (Quotation quotation : historicalData) {
//                System.out.println(quotation.getCandle() + " " + quotation.getDate());
//            }
//        }

        // Calculating entry points example
        EnterPointCalculator calculator = new EnterPointCalculator();
        Stock stock = new Stock("NVDA", historicalData);
        System.out.println(Arrays.toString(calculator.calculateEnterPoints(stock)));

        // Calculating exit points example
        TechAnalis techAnalis = new TechAnalis(historicalData);
        float[] points = techAnalis.entryPoints();
        System.out.println(Arrays.toString(points));



    }
}