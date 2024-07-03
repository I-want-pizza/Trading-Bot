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
import java.util.Calendar;
import java.util.Properties;

public class API {
    private static final SimpleDateFormat sdf = new SimpleDateFormat(Constants.timePattern);
    public static StockData getHistoricalData(String symbol, Calendar from, Calendar to) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String api_key = getApiKey();

        String url = Constants.baseApiUrl + Constants.slash + symbol + Constants.apiUrlProperty + api_key;
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println(Constants.requestError + response);
                throw new IOException(Constants.unexpectedCodeError + response);
            }

            String jsonData = response.body().string();
            // System.out.println("Raw JSON response: " + jsonData); // Print raw JSON response
            JsonArray jsonArray = JsonParser.parseString(jsonData).getAsJsonArray();

            StockData candlesticks = new StockData();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                String dateTimeStr = jsonObject.get(Constants.jsonDate).getAsString();
                float openPrice = jsonObject.get(Constants.jsonOpen).getAsFloat();
                float closePrice = jsonObject.get(Constants.jsonClose).getAsFloat();
                float highPrice = jsonObject.get(Constants.jsonMax).getAsFloat();
                float lowPrice = jsonObject.get(Constants.jsonMin).getAsFloat();

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
