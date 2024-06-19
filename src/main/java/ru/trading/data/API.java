package ru.trading.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class API {
    private static final String BASE_URL = "https://www.alphavantage.co/query";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(Constants.timePattern);
    public static StockData getHistoricalData(String symbol, Calendar startDate, Calendar endDate) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String apiKey = getApiKey();
        String url = BASE_URL + "?function=TIME_SERIES_DAILY&symbol=" + symbol + "&outputsize=full&apikey=" + apiKey;
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String jsonData = response.body().string();
            JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
            JsonObject timeSeries = jsonObject.getAsJsonObject("Time Series (Daily)");

            StockData candlesticks = new StockData();

            for (String dateTimeStr : timeSeries.keySet()) {
                Calendar dateTime = Calendar.getInstance();
                try {
                    dateTime.setTime(sdf.parse(dateTimeStr));
                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }

//                if (dateTime.before(startDate) || dateTime.after(endDate)) {
//                    continue;
//                }

                JsonObject dayData = timeSeries.getAsJsonObject(dateTimeStr);
                float openPrice = dayData.get("1. open").getAsFloat();
                float closePrice = dayData.get("4. close").getAsFloat();
                float maxPrice = dayData.get("2. high").getAsFloat();
                float minPrice = dayData.get("3. low").getAsFloat();

                candlesticks.add(new Quotation(dateTime, new Candle(openPrice, closePrice, maxPrice, minPrice)));
            }

            return candlesticks;
        }

    }
    public static String getApiKey() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = API.class.getClassLoader().getResourceAsStream(Constants.propertiesFileName)) {
            if (input == null) {
                throw new IOException("Unable to find config.properties");
            }
            properties.load(input);
            return properties.getProperty(Constants.apiProperty);
        }
    }
}
