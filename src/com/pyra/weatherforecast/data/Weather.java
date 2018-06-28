package com.pyra.weatherforecast.data;

import java.util.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Weather {
  
  private City city;
  private int weatherId;
  // Temperatures are in Kelvin (1 K = 272.15 C)
  private double temp;
  private double tempMin;
  private double tempMax;
  // Pressure is in hPa (1 hPa = 100 Pa = 0.001 bar)
  private double pressure;
  // Humidity is in %
  private long humidity;
  // Wind speed in m/s
  private double windSpeed;
  // Wind heading in degrees
  private long windHeading;
  // When this data is taken. Time in UTC
  private Date timestamp;
  
  public Weather() {
    city = new City();
    weatherId = 800;
    temp = 273.15;
    tempMin = 270.10;
    tempMax = 275.12;
    pressure = 1019;
    humidity = 83;
    windSpeed = 5;
    windHeading = -100;
    timestamp = new Date();
  }
  
  public Weather(City city, Date timestamp) {
    this.city = city;
    weatherId = 800;
    temp = 273.15;
    tempMin = 270.10;
    tempMax = 275.12;
    pressure = 1019;
    humidity = 83;
    windSpeed = 5;
    windHeading = -100;
    this.timestamp = timestamp;
  }
  
  public Weather(City city, long timestamp) {
    this.city = city;
    weatherId = 800;
    temp = 273.15;
    tempMin = 270.10;
    tempMax = 275.12;
    pressure = 1019;
    humidity = 83;
    windSpeed = 5;
    windHeading = -100;
    this.timestamp = new Date(timestamp);
  }

  public Weather(JSONObject data) {
    this.fillFromJson(data);
  }
  
  public City getCity() {
    return city;
  }
  
  public int getWeatherId() {
    return weatherId;
  }
  

  public double getTemp() {
    return temp;
  }
  

  public double getTempMin() {
    return tempMin;
  }
  

  public double getTempMax() {
    return tempMax;
  }
  

  public double getPressure() {
    return pressure;
  }
  

  public long getHumidity() {
    return humidity;
  }
  

  public double getWindSpeed() {
    return windSpeed;
  }
  

  public long getWindHeading() {
    return windHeading;
  }
  

  public Date getTimestamp() {
    return timestamp;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public void setWeatherId(int weatherId) {
    this.weatherId = weatherId;
  }
  
  public void setTempAndMaxMin(double temp, double tempMin, double tempMax) {
    this.temp = temp;
    this.tempMin = tempMin;
    this.tempMax = tempMax;
  }

  public void setPressure(long pressure) {
    this.pressure = pressure;
  }

  public void setHumidity(long humidity) {
    this.humidity = humidity;
  }

  public void setWindSpeed(double windSpeed) {
    this.windSpeed = windSpeed;
  }

  public void setWindHeading(long windHeading) {
    this.windHeading = windHeading;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
  
  public void fillFromJson(JSONObject data) {
    // Extracting city details
    JSONObject coord = (JSONObject) data.get("coord");
    double lat = (Double) coord.get("lat");
    double lon = (Double) coord.get("lon");
    String cityId = ((Number) data.get("id")).toString();
    String cityName = (String) data.get("name");
    String cityCountry = (String) ((JSONObject) data.get("sys")).get("country");
    this.city = new City(cityId,cityName,cityCountry,lat,lon);
    // Extracting weather details 
    JSONArray weatherArray = (JSONArray) data.get("weather");
    JSONObject weather = (JSONObject) weatherArray.get(0);
    this.weatherId = ((Number) weather.get("id")).intValue();
    JSONObject main = (JSONObject) data.get("main");
    this.temp = ((Number) main.get("temp")).doubleValue();
    this.pressure = ((Number) main.get("pressure")).doubleValue();
    this.humidity = (Long) main.get("humidity");
    this.tempMin = ((Number) main.get("temp_min")).doubleValue();
    this.tempMax = ((Number) main.get("temp_max")).doubleValue();
    JSONObject wind = null;
    try {
      wind = (JSONObject) data.get("wind");      
    } catch (NullPointerException npe) {
      // No action needed
    }
    if (wind != null) {
      try {
        this.windSpeed = (Double) wind.get("speed");        
      } catch (NullPointerException npe) {
        // No action needed
      }
      try {
        this.windHeading = (Long) wind.get("deg");
      } catch (NullPointerException npe) {
        // No action needed
      }
    }
    // Extracting time stamp
    this.timestamp = new Date((Long) data.get("dt"));
  }
  
  @Override
  public String toString() {
    String additionalWeather = temp + " | " + pressure + " | " + humidity;
    return city + "\n" + weatherId + "\n" + additionalWeather;
  }
  
  public static String weatherCodeToString(long code) {
    long leading = code / 100;
    if (leading == 2) {
      return "Thunderstorm";
    } else if (leading == 3) {
      return "Drizzle";
    } else if (leading == 5) {
      return "Raining";
    } else if (leading == 6) {
      return "Snowing";
    } else if (leading == 7) {
      return "Foggy";
    } else if (leading == 8) {
      return "Clear skies";
    } else if (leading == 9) {
      return "Cloudy";
    } else {
      return "Undefined";
    }
  }
  
  public static String weatherCodeToString(Weather w) {
    return weatherCodeToString(w.getWeatherId());
  }
}
