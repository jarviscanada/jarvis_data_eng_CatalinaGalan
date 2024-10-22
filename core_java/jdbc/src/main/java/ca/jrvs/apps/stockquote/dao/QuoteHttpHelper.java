package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.model.Quote;
import ca.jrvs.apps.stockquote.util.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QuoteHttpHelper {

  private String apiKey;
  private OkHttpClient client;

  /**
   * Fetch the latest quote data from Alpha Vantage endpoint
   * @param symbol - ticker for the quote requested
   * @return Quote object with latest data
   * @throws IllegalArgumentException - if no data was given for the symbol
   */
  public Quote fetchQuoteInfo(String symbol) throws IllegalArgumentException {

    Dotenv dotenv = Dotenv.load();
    apiKey = dotenv.get("X_RAPID_API_KEY");
    client = new OkHttpClient();

    Request request = new Request.Builder()
        .url("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + symbol
            + "&datatype=json")
        .header("X-RapidAPI-Key", apiKey)
        .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
        .build();
    try (Response response = client.newCall(request).execute()) {
      String body = response.body().string();
//      System.out.println(body);
      Quote quote = JsonParser.toObjectFromJson(body, Quote.class);
      if (quote != null && symbol.equals(quote.getTicker())) {
        Date date = new Date();
        quote.setTimestamp(Timestamp.from(Instant.now()));
        return quote;
      } else {
        System.out.println("The symbol passed didn't return a valid quote");
        return null;
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) {
    QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper();
    Quote quote = quoteHttpHelper.fetchQuoteInfo("MSFT");
//    System.out.println(quote.toString());
  }
}
