package ru.trading.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Quotation {
    private Calendar date;
    private Candle candle;
    private static final SimpleDateFormat sdf = new SimpleDateFormat(Constants.timePattern);
    private static Calendar cal = new GregorianCalendar();
    public Quotation(Calendar date, Candle candle) {
        this.date = date;
        this.candle = candle;

    }
    @Override
    public String toString() {
        sdf.setCalendar(cal);
        return "Quotation{\n" +
                "   date=" + sdf.format(date.getTime()) + ",\n" +
                "   candle=" + candle +
                "\n}";
    }
    public Calendar getDate() {
        return date;
    }
    public Candle getCandle() {
        return candle;
    }

}
