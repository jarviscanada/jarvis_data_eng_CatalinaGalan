package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.util.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QuoteHttpHelper {

  private final String apiKey;
  private final OkHttpClient client;

  public QuoteHttpHelper(String apiKey, OkHttpClient client) {
    this.apiKey = apiKey;
    this.client = client;
  }

  /**
   * Fetch the latest quote data from Alpha Vantage endpoint
   * @param symbol - ticker for the quote requested
   * @return Quote object with latest data
   * @throws IllegalArgumentException - if no data was given for the symbol
   */
  public Quote fetchQuoteInfo(String symbol) throws IllegalArgumentException {

    Request request = new Request.Builder()
        .url("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + symbol
            + "&datatype=json")
        .header("X-RapidAPI-Key", apiKey)
        .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
        .build();

    try (Response response = client.newCall(request).execute()) {
      String body = response.body().string();
      Quote quote = JsonParser.toObjectFromJson(body, Quote.class);
      if (quote != null) {
        quote.setTimestamp(Timestamp.from(Instant.now()));
      }
      return quote;
    } catch (IOException e) {
      throw new IllegalArgumentException("\n Invalid input.");
    }
  }
}
