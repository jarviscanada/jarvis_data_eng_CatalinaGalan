package ca.jrvs.apps.stockquote.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public class JsonParser {

  /**
   * Convert a Java Object into JSON string
   *
   * @param object
   * @param prettyJson
   * @param includeNullValues
   * @return JSON string
   * @throws JsonProcessingException
   */
  public static String toJson(Object object, boolean prettyJson, boolean includeNullValues)
      throws JsonProcessingException {

    ObjectMapper mapper = new ObjectMapper();
    if (!includeNullValues) {
      mapper.setSerializationInclusion(Include.NON_NULL);
    }
    if (prettyJson) {
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    return mapper.writeValueAsString(object);
  }

  /**
   * Parse JSON string into Java Object
   *
   * @param json  JSON string
   * @param clazz object class
   * @param <T>   Type
   * @return Object
   * @throws IOException
   */
  public static <T> T toObjectFromJson(String json, Class<T> clazz) throws IOException {

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
    return (T) mapper.readValue(json, clazz);
  }
}
