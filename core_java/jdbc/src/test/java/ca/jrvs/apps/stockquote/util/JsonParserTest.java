package ca.jrvs.apps.stockquote.util;

import static org.junit.jupiter.api.Assertions.*;

import ca.jrvs.apps.stockquote.model.Quote;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class JsonParserTest {

  @Test
  void toObjectFromJson() throws IOException {
    String jsonString = "{\n"
        + "  \"Global Quote\": {\n"
        + "    \"01. symbol\": \"MSFT\",\n"
        + "    \"02. open\": \"332.3800\",\n"
        + "    \"03. high\": \"333.8300\",\n"
        + "    \"04. low\": \"326.3600\",\n"
        + "    \"05. price\": \"327.7300\",\n"
        + "    \"06. volume\": \"21085695\",\n"
        + "    \"07. latest trading day\": \"2023-10-13\",\n"
        + "    \"08. previous close\": \"331.1600\",\n"
        + "    \"09. change\": \"-3.4300\",\n"
        + "    \"10. change percent\": \"-1.0358%\"\n"
        + "  }\n"
        + "}";

    Quote quote = JsonParser.toObjectFromJson(jsonString, Quote.class);
    assertNotNull(quote);
    assertEquals(quote.getTicker(), "MSFT");
  }
}