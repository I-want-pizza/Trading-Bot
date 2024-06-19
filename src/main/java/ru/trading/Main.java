package ru.trading;

import ru.trading.data.API;
import ru.trading.data.Constants;
import ru.trading.data.StockData;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.timePattern);
        start.setTime(sdf.parse("2030-01-01"));
        end.setTime(sdf.parse("2001-01-01"));
        StockData data = API.getHistoricalData("IBM", start, end);
        System.out.println(data);
    }
}