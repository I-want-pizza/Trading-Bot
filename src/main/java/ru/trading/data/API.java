package ru.trading.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class API {
    private static final String BASE_URL = "https://financialmodelingprep.com/api/v3/historical-chart/1day";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(Constants.timePattern);
    public static StockData getHistoricalData(String symbol, Calendar from, Calendar to) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String api_key = getApiKey();

        String url = BASE_URL + "/" + symbol + "?apikey=" + api_key;
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("Request failed: " + response);
                throw new IOException("Unexpected code " + response);
            }

            String jsonData = response.body().string();
            // System.out.println("Raw JSON response: " + jsonData); // Print raw JSON response
            JsonArray jsonArray = JsonParser.parseString(jsonData).getAsJsonArray();

            StockData candlesticks = new StockData();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                String dateTimeStr = jsonObject.get("date").getAsString();
                float openPrice = jsonObject.get("open").getAsFloat();
                float closePrice = jsonObject.get("close").getAsFloat();
                float highPrice = jsonObject.get("high").getAsFloat();
                float lowPrice = jsonObject.get("low").getAsFloat();

                Calendar dateTime = Calendar.getInstance();
                try {
                    dateTime.setTime(sdf.parse(dateTimeStr));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Filter by date range
                if (dateTime.after(from) && dateTime.before(to)) {
                    candlesticks.add(new Quotation(dateTime, new Candle(openPrice, closePrice, highPrice, lowPrice)));
                }
            }

            return candlesticks;
        }
    }
    public static String getApiKey() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = API.class.getClassLoader().getResourceAsStream(Constants.propertiesFileName)) {
            if (input == null) {
                throw new IOException(Constants.configError);
            }
            properties.load(input);
            return properties.getProperty(Constants.apiProperty);
        }
    }
}
