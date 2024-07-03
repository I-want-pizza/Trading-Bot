package ru.trading.data;

public interface Constants {
    int MinutesInHour = 60;
    int MinutesInDay = 1440;
    int MinutesInWeek = 10080;
    int MinutesInMonth = 44640;
    String propertiesFileName = "config.properties";
    String apiProperty = "api.key";
    String timePattern = "yyyy-MM-dd";
    String configError = "Unable to find config.properties";
    String requestError = "Request failed: ";
    String unexpectedCodeError = "Unexpected code ";
    String jsonDate = "date";
    String jsonOpen = "open";
    String jsonClose = "close";
    String jsonMax = "high";
    String jsonMin = "low";
    String slash = "/";
    String apiUrlProperty = "?apikey=";
    String baseApiUrl = "https://financialmodelingprep.com/api/v3/historical-chart/1day";

}
