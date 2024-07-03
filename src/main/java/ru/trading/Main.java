package ru.trading;

import ru.trading.data.*;
import ru.trading.techanalisis.TechAnalis;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import static ru.trading.data.API.getHistoricalData;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        String symbol = "NVDA"; // Apple on US exchange
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();

        // Example: Fetch data from the last 200 days (no holidays)
        from.add(Calendar.DAY_OF_YEAR, -360);

        StockData historicalData = getHistoricalData(symbol, from, to);
        if (historicalData.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (Quotation quotation : historicalData) {
                System.out.println(quotation.getCandle() + " " + quotation.getDate());
            }
        }
        TechAnalis techAnalis = new TechAnalis(historicalData);
        float[] points = techAnalis.entryPoints();
        System.out.println(Arrays.toString(points));

    }
}