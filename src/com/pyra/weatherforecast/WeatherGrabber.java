package com.pyra.weatherforecast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class WeatherGrabber {

  private static final String apiKey = "962b557f8e216a4abb348130dc4659f0";
  private static final String baseURL = "http://api.openweathermap.org/data/2.5/";
  private String cityId;
  private HttpURLConnection conn;
  private JSONObject data;
    
  public WeatherGrabber(final String cityId) {
    this.cityId = cityId;
  }

  public String getCityId() {
    return cityId;
  }

  public void setCityId(String cityId) {
    this.cityId = cityId;
  }
  
  private void makeRequest(char mode) throws MalformedURLException, IOException {
    String tempString;
    if (mode == 'w')  {
      tempString = baseURL + "weather?id=" + cityId + "&appid=" + apiKey;
    } else { // mode == 'f'
      tempString = baseURL + "forecast?id=" + cityId + "&appid=" + apiKey;
    }
    URL curUrl = new URL(tempString);
    conn = (HttpURLConnection) curUrl.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("User-Agent","Mozilla/5.0");
  }
  
  public void grabWeather() throws MalformedURLException, IOException {
    // Opening & requesting data to OpenWeather
    makeRequest('w');
    
    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
      // Request OK - read data
      BufferedReader resData = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String temp = resData.readLine();
      StringBuffer jsonString = new StringBuffer();
      while (temp != null) {
        jsonString.append(temp);
        temp = resData.readLine();
      }
      // Parsing JSON data
      JSONParser parser = new JSONParser();
      try {
        data = (JSONObject) parser.parse(jsonString.toString());        
      } catch (ParseException pe) {
        System.out.println(pe);
      }
      System.out.println(data.get("name"));
      System.out.println(data.get("weather"));
    } else {
      // Request not OK 
      System.out.println("Got " + conn.getResponseCode() + " as response code.");
    }
  }
  
  public void grabForecast() throws MalformedURLException, IOException {
    // Opening & requesting data to OpenWeather
    makeRequest('f');
    
    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
      // Request OK - read data
      BufferedReader resData = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String temp = resData.readLine();
      StringBuffer jsonString = new StringBuffer();
      while (temp != null) {
        jsonString.append(temp);
        temp = resData.readLine();
      }
      // Parsing JSON data
      JSONParser parser = new JSONParser();
      try {
        data = (JSONObject) parser.parse(jsonString.toString());        
      } catch (ParseException pe) {
        System.out.println(pe);
      }
      System.out.println(data.get("cnt"));
      System.out.println(((JSONArray)data.get("list")).get(0));
    } else {
      // Request not OK 
      System.out.println("Got " + conn.getResponseCode() + " as response code.");
    }
  }
  
}
