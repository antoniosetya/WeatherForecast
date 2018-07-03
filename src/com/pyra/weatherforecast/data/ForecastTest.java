package com.pyra.weatherforecast.data;

import static org.junit.Assert.assertEquals;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

public class ForecastTest {

  @Test
  public void testCtor() {
    City ctest = new City();
    Forecast test = new Forecast(ctest);
    assertEquals(test.getCity(),ctest);
    assertEquals(test.getForecast().size(),0);
  }

  @Test
  public void testAddition() {
    City ctest = new City();
    Forecast test = new Forecast(ctest);
    Weather wtest = new Weather();
    test.addOneForecast(wtest);
    assertEquals(test.getForecast().size(),1);
    assertEquals(test.getForecast().get(0),wtest);
    Weather w2test = new Weather();
    w2test.setWeatherId(500);
    test.addOneForecast(w2test);
    assertEquals(test.getForecast().size(),2);
    assertEquals(test.getForecast().get(1),w2test);
    assertEquals(test.getForecast().get(1).getWeatherId(),500);
  }
  
  @Test
  public void testJson() {
    String jsonTest = "{\"cod\":\"200\",\"message\":0.0097,\"cnt\":2,\"list\":[{\"dt\":1529377200"
        + ",\"main\":{\"temp\":305.5,\"temp_min\":302.371,\"temp_max\":305.5,\"pressure\":1024.71,"
        + "\"sea_level\":1025.16,\"grnd_level\":1024.71,\"humidity\":89,\"temp_kf\":3.13},\"weather"
        + "\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\""
        + "clouds\":{\"all\":12},\"wind\":{\"speed\":3.57,\"deg\":109.001},\"sys\":{\"pod\":\"d\"},"
        + "\"dt_txt\":\"2018-06-19 03:00:00\"},{\"dt\":1529388000,\"main\":{\"temp\":306.11,"
        + "\"temp_min\":303.764,\"temp_max\":306.11,\"pressure\":1022.99,\"sea_level\":1023.5,"
        + "\"grnd_level\":1022.99,\"humidity\":83,\"temp_kf\":2.35},\"weather\":"
        + "[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],"
        + "\"clouds\":{\"all\":12},\"wind\":{\"speed\":3.76,\"deg\":53.0044},\"sys\":"
        + "{\"pod\":\"d\"},\"dt_txt\":\"2018-06-19 06:00:00\"}]}";
    JSONParser parser = new JSONParser();
    JSONObject jsonTestRep = new JSONObject();
    try {
      jsonTestRep = (JSONObject) parser.parse(jsonTest);
    } catch (ParseException pe) {
      pe.printStackTrace();
      // No handling needed, jsonTest is assured to be "parse-error free"
    }
    City ctest = new City();
    Forecast test = new Forecast(ctest);
    test.addFromJson(jsonTestRep);
    assertEquals(test.getForecast().size(),2);
    assertEquals(test.getForecast().get(0).getHumidity(),89);
    assertEquals(test.getForecast().get(1).getHumidity(),83);
  }
  
}
