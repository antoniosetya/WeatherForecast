package com.pyra.weatherforecast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherGrabber {

  private static final String apiKey = "962b557f8e216a4abb348130dc4659f0";
  private static final String baseURL = "http://api.openweathermap.org/data/2.5/";
  private String cityId;
  //private <JSONDataStruct> data;
    
  public WeatherGrabber(final String cityId) {
    this.cityId = cityId;
  }

  public String getCityId() {
    return cityId;
  }

  public void setCityId(String cityId) {
    this.cityId = cityId;
  }
  
  public void grabWeather() throws MalformedURLException, IOException {
    // Opening & requesting data to OpenWeather
    URL curUrl = new URL(baseURL + "weather?id=" + cityId + "&appid=" + apiKey);
    HttpURLConnection conn = (HttpURLConnection) curUrl.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("User-Agent","Mozilla/5.0");
    
    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
      // Request OK - read data
      BufferedReader resData = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String temp = resData.readLine();
      StringBuffer jsonString = new StringBuffer();
      while (temp != null) {
        jsonString.append(temp);
        temp = resData.readLine();
      }
      System.out.println(jsonString);
    } else {
      // Request not OK 
      System.out.println("Got " + conn.getResponseCode() + " as response code.");
    }
  }
  
  public void grabForecast() throws MalformedURLException, IOException {
    // Opening & requesting data to OpenWeather
    URL curUrl = new URL(baseURL + "forecast?id=" + cityId + "&appid=" + apiKey);
    HttpURLConnection conn = (HttpURLConnection) curUrl.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("User-Agent","Mozilla/5.0");
    
    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
      // Request OK - read data
      BufferedReader resData = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String temp = resData.readLine();
      StringBuffer jsonString = new StringBuffer();
      while (temp != null) {
        jsonString.append(temp);
        temp = resData.readLine();
      }
      System.out.println(jsonString);
    } else {
      // Request not OK 
      System.out.println("Got " + conn.getResponseCode() + " as response code.");
    }
  }
  
  public void process() {
    
  }
  
}
