package com.pyra.weatherforecast.data;

import static org.junit.Assert.assertEquals;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

public class WeatherTest {

  @Test
  public void testDefaultCtor() {
    Weather test = new Weather();
    assertEquals(test.getWeatherId(),800);
    assertEquals(test.getTemp(),273.15,2);
    assertEquals(test.getPressure(),1019,2);
    assertEquals(test.getHumidity(),83,2);
    assertEquals(test.getWindSpeed(),5,2);
    assertEquals(test.getWindHeading(),-100,2);
  }

  @Test
  public void testJsonCtor() {
    String jsonTest = "{\"coord\":\r\n" 
        + "{\"lon\":145.77,\"lat\":-16.92},"
        + "\"weather\":[{\"id\":803}],"
        + "\"main\":{\"temp\":293.25,\"pressure\":1019,"
        + "\"humidity\":83,\"temp_min\":289.82,\"temp_max\":295.37},"
        + "\"wind\":{\"speed\":5.1,\"deg\":150},"
        + "\"dt\":1435658272,\r\n"
        + "\"sys\":{\"country\":\"AU\",\"sunrise\":1435610796,\"sunset\":1435650870},"
        + "\"id\":2172797,"
        + "\"name\":\"Cairns\","
        + "\"cod\":200}";
    JSONParser parser = new JSONParser();
    JSONObject jsonTestRep = new JSONObject();
    try {
      jsonTestRep = (JSONObject) parser.parse(jsonTest);
    } catch (ParseException pe) {
      pe.printStackTrace();
      // No handling needed, jsonTest is assured to be "parse-error free"
    }
    Weather test = new Weather(jsonTestRep);
    assertEquals(test.getWeatherId(),803);
    assertEquals(test.getTemp(),293.25,2);
    assertEquals(test.getTempMax(),295.37,2);
    assertEquals(test.getHumidity(),83);
    assertEquals(test.getPressure(),1019,2);
    assertEquals(test.getTimestamp().getTime(),(((long)1435658272) * ((long) 1000)));
  }
  
  @Test
  public void testSetters() {
    Weather test = new Weather();
    City ctest = new City();
    test.setCity(ctest);
    assertEquals(test.getCity(),ctest);
    test.setWeatherId(500);
    assertEquals(test.getWeatherId(),500);
    test.setPressure(1090);
    assertEquals(test.getPressure(),1090,2);
    test.setHumidity(70);
    assertEquals(test.getHumidity(),70);
    test.setTempAndMaxMin(276, 274.9, 279.8);
    assertEquals(test.getTemp(),276,2);
    assertEquals(test.getTempMin(),274.9,2);
    assertEquals(test.getTempMax(),279.8,2);
    test.setWindSpeed(15);
    assertEquals(test.getWindSpeed(),15,2);
    test.setWindHeading(169.7);
    assertEquals(test.getWindHeading(),169.7,2);
  }
  
}
