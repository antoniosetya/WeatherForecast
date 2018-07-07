package com.pyra.weatherforecast.data;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Forecast {

  private City city;
  private ArrayList<Weather> forecast;
    
  /**The main constructor for Forecast.
   * 
   * @param city : The City whose forecast data in this Forecast.
   */
  public Forecast(City city) {
    forecast = new ArrayList<Weather>();
    this.city = city;
  }
  
  /**Gets the city.
   * 
   * @return the City 
   */
  public City getCity() {
    return city;
  }
  
  /**Sets the city.
   * 
   * @param city : the City for this Forecast.
   */
  public void setCity(City city) {
    this.city = city;
  }
  
  /**Returns the ArrayList of Weather(s) that is the forecast data.
   * 
   * @return ArrayList of Weather
   */
  public ArrayList<Weather> getForecast() {
    return forecast;
  }
  
  /**Adds a forecast to this Forecast.
   * 
   * @param in : one forecast data to be added to this Forecast.
   */
  public void addOneForecast(Weather in) {
    forecast.add(in);
  }
  
  /**Clears the forecast data.
   */
  public void clearForecast() {
    forecast.clear();
  }
  
  /**Adds forecast data from JSON representation, sent by OpenWeather.
   * JSONObject class is provided by json-simple library.
   * 
   * @param in : the JSONObject representation from OpenWeather
   */
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
