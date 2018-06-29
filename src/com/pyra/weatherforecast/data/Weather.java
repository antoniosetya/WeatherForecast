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
  private double windHeading;
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
  

  public double getWindHeading() {
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

  public void setWindHeading(double windHeading) {
    this.windHeading = windHeading;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
  
  public void fillFromJson(JSONObject data) {
    // Extracting city details
    try {
      JSONObject coord = (JSONObject) data.get("coord");
      double lat = (Double) coord.get("lat");
      double lon = (Double) coord.get("lon");
      String cityId = ((Number) data.get("id")).toString();
      String cityName = (String) data.get("name");
      String cityCountry = (String) ((JSONObject) data.get("sys")).get("country");
      this.city = new City(cityId,cityName,cityCountry,lat,lon);
    } catch (NullPointerException npe) {
      // Just leave the fields blank
      this.city = null;
    }
    // Extracting weather details 
    JSONArray weatherArray = (JSONArray) data.get("weather");
    JSONObject weather = (JSONObject) weatherArray.get(0);
    this.weatherId = ((Number) weather.get("id")).intValue();
    JSONObject main = (JSONObject) data.get("main");
    this.temp = ((Number) main.get("temp")).doubleValue();
    this.pressure = ((Number) main.get("pressure")).doubleValue();
    this.humidity = ((Number) main.get("humidity")).longValue();
    this.tempMin = ((Number) main.get("temp_min")).doubleValue();
    this.tempMax = ((Number) main.get("temp_max")).doubleValue();
    JSONObject wind = null;
    try {
      wind = (JSONObject) data.get("wind");      
    } catch (NullPointerException npe) {
      // No action needed, just leave the fields blank
    }
    if (wind != null) {
      try {
        this.windSpeed = ((Number) wind.get("speed")).doubleValue();        
      } catch (NullPointerException npe) {
        // No action needed, just leave the fields blank
      }
      try {
        this.windHeading = ((Number) wind.get("deg")).doubleValue();
      } catch (NullPointerException npe) {
        // No action needed, just leave the fields blank
      }
    }
    // Extracting time stamp
    this.timestamp = new Date(((Long) data.get("dt")) * 1000);
  }
  
  @Override
  public String toString() {
    String additionalWeather = temp + " | " + pressure + " | " + humidity;
    return city + "\n" + weatherId + "\n" + additionalWeather;
  }
  
  public static String weatherCodeToString(long code) {
    // Thunderstorms group
    if (code == 200) {
      return "Thunderstorm w/ light rain";
    } else if (code == 201) {
      return "Thunderstorm w/ rain";
    } else if (code == 202) {
      return "Thunderstorm w/ heavy rain";
    } else if (code == 210) {
      return "Light thunderstorm";
    } else if (code == 211) {
      return "Thunderstorm";
    } else if (code == 212) {
      return "Heavy thunderstorm";
    } else if (code == 221) {
      return "Ragged thunderstorm";
    } else if (code == 230) {
      return "Thunderstorm w/ light drizzle";
    } else if (code == 231) {
      return "Thunderstorm w/ drizzle";
    } else if (code == 232) {
      return "Thunderstorm w/ heavy drizzle";
    
    // Drizzle group
    } else if (code == 300) {
      return "Light drizzle";
    } else if (code == 301) {
      return "Drizzle";
    } else if (code == 302) {
      return "Heavy drizzle";
    } else if (code == 310) {
      return "Light drizzle rain";
    } else if (code == 311) {
      return "Drizzle rain";
    } else if (code == 312) {
      return "Heavy drizzle rain";
    } else if (code == 313) {
      return "Shower w/ drizzle";
    } else if (code == 314) {
      return "Heavy shower w/ drizzle";
    } else if (code == 321) {
      return "Shower drizzle";
    
    // Rain group
    } else if (code == 500) {
      return "Light rain";
    } else if (code == 501) {
      return "Moderate rain";
    } else if (code == 502) {
      return "Heavy rain";
    } else if (code == 503) {
      return "Very heavy rain";
    } else if (code == 504) {
      return "Extreme rain";
    } else if (code == 511) {
      return "Freezing rain";
    } else if (code == 520) {
      return "Light shower rain";
    } else if (code == 521) {
      return "Shower rain";
    } else if (code == 522) {
      return "Heavy shower rain";
    } else if (code == 531) {
      return "Ragged shower rain";
      
    // Snow group
    } else if (code == 600) {
      return "Light snow";
    } else if (code == 601) {
      return "Snow";
    } else if (code == 602) {
      return "Heavy snow";
    } else if (code == 611) {
      return "Sleet";
    } else if (code == 612) {
      return "Shower sleet";
    } else if (code == 615) {
      return "Light rain w/ snow";
    } else if (code == 616) {
      return "Rain w/ snow";
    } else if (code == 620) {
      return "Light shower snow";
    } else if (code == 621) {
      return "Shower snow";
    } else if (code == 622) {
      return "Heavy shower snow";
      
    // Atmospheric particles group
    } else if (code == 701) {
      return "Mist";
    } else if (code == 711) {
      return "Smoke";
    } else if (code == 721) {
      return "Haze";
    } else if (code == 731) {
      return "Sand, dust whirls";
    } else if (code == 741) {
      return "Fog";
    } else if (code == 751) {
      return "Sand";
    } else if (code == 761) {
      return "Dust";
    } else if (code == 762) {
      return "Volcanic ash";
    } else if (code == 771) {
      return "Squalls";
    } else if (code == 781) {
      return "Tornado";
      
    // Clear and cloudy group
    } else if (code == 800) {
      return "Clear skies";
    } else if (code == 801) {
      return "Few clouds";
    } else if (code == 802) {
      return "Scattered clouds";
    } else if (code == 803) {
      return "Broken clouds";
    } else if (code == 804) {
      return "Overcast clouds";
    
    // Undefined code
    } else {
      return "Undefined";
    }
  }
  
  public static String weatherCodeToString(Weather w) {
    return weatherCodeToString(w.getWeatherId());
  }
}
