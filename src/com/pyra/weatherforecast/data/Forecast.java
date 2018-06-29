package com.pyra.weatherforecast.data;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Forecast {

  private City city;
  private ArrayList<Weather> forecast;
    
  public Forecast(City city) {
    forecast = new ArrayList<Weather>();
    this.city = city;
  }
  
  public City getCity() {
    return city;
  }
  
  public void setCity(City city) {
    this.city = city;
  }
  
  public ArrayList<Weather> getForecast() {
    return forecast;
  }
  
  public void addOneForecast(Weather in) {
    forecast.add(in);
  }
  
  public void clearForecast() {
    forecast.clear();
  }
  
  public void addFromJson(JSONObject in) {
    if (((String)in.get("cod")).equals("200")) {
      JSONArray forecasts = (JSONArray) in.get("list");
      for (Object temp : forecasts) {
        JSONObject elm = (JSONObject) temp;
        forecast.add(new Weather(elm));
      }
    }
  }
  
}
