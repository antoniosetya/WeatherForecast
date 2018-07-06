package com.pyra.weatherforecast.data;

import java.awt.Image;
import java.util.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Weather {
  
  private City city;
  // weatherId represents weather code that represents current weather in that City
  private int weatherId;
  // An image that represents current weather condition
  private Image weatherIcon;
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
  // Sunrise and sunset time (in UTC)
  private Date sunrise;
  private Date sunset;
  // When this data is taken. Time in UTC
  private Date timestamp;
  
  /**The default/main constructor of Weather.
   */
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
  
  /**Alternate constructor for Weather.
   * 
   * @param city : the City whose weather data is being represented by this Weather. 
   * @param timestamp : java.util.Date, representing when the data in this Weather is taken.
   */
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
  
  /**Alternate constructor for Weather.
   * 
   * @param city : the City whose weather data is being represented by this Weather. 
   * @param timestamp : when the data in this Weather is taken, in "epoch" time.
   */
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

  /**Alternate constructor for Weather.
   * 
   * @param data : a JSONObject data that represents JSON taken from OpenWeather server.
   *               See json-simple library for details. 
   */
  public Weather(JSONObject data) {
    this.fillFromJson(data);
  }
  
  /**Returns the location of this Weather.
   * 
   * @return a City representation of the location of this Weather.
   */
  public City getCity() {
    return city;
  }
  
  /**Returns an image that represents current weather condition.
   * 
   * @return java.awt.Image 
   */
  public Image getWeatherIcon() {
    return weatherIcon;
  }
  
  
  /**Returns a code that represents current weather condition.
   * To see what it means, either look at the static method weatherCodeToString, or
   * consult the OpenWeather API. 
   * 
   * @return an integer which is the code.
   */
  public int getWeatherId() {
    return weatherId;
  }
  

  /**Returns the temperature measured (in Kelvin).
   * 
   * @return a double which is the temperature.
   */
  public double getTemp() {
    return temp;
  }
  

  /**Returns the minimum temperature measured in a day (in Kelvin).
   * 
   * @return a double which is the minimum temperature
   */
  public double getTempMin() {
    return tempMin;
  }
  
  /**Returns the maximum temperature measured in a day (in Kelvin).
   * 
   * @return a double which is the maximum temperature
   */
  public double getTempMax() {
    return tempMax;
  }
  
  /**Returns the pressure measured (in hPa, hectopascal).
   * 
   * @return a double which is the pressure
   */
  public double getPressure() {
    return pressure;
  }
  

  /**Returns the humidity measured (in %).
   * 
   * @return a long which is the humidity
   */
  public long getHumidity() {
    return humidity;
  }
  

  /**Returns the wind speed measured (in m/s).
   * 
   * @return a double which is the wind speed.
   */
  public double getWindSpeed() {
    return windSpeed;
  }
  

  /**Returns where the wind is heading (in degrees).
   * 
   * @return a double which is the wind heading.
   */
  public double getWindHeading() {
    return windHeading;
  }

  /**Returns the time of sunrise today in that location.
   * 
   * @return a java.util.Date which is the sunrise time.
   */
  public Date getSunrise() {
    return sunrise;
  }
  
  /**Returns the time of sunset today in that location.
   * 
   * @return a java.util.Date which is the sunset time.
   */
  public Date getSunset() {
    return sunset;
  }

  
  /**Returns the time this data is taken.
   * 
   * @return a java.util.Date which is when this data is taken.
   */
  public Date getTimestamp() {
    return timestamp;
  }

  /**Sets the City.
   * 
   * @param city represents location of this Weather.
   */
  public void setCity(City city) {
    this.city = city;
  }
  
  /**Sets the code that represents current weather conditions.
   * 
   * @param weatherId is the code.
   */
  public void setWeatherId(int weatherId) {
    this.weatherId = weatherId;
  }
  
  /**Sets the image that represents current weather conditions.
   * 
   * @param weatherIcon is the image, anything that extends Image is accepted
   */
  public <T extends Image> void setWeatherIcon(T weatherIcon) {
    this.weatherIcon = weatherIcon;
  }
  
  
  /**Sets the temperature. Temperatures are in Kelvin.
   * Just make sure that tempMin < temp < tempMax, this thing doesn't do that...
   * 
   * @param temp is the current temperature
   * @param tempMin is the minimum temperature recorded
   * @param tempMax is the maximum temperature recorded
   */
  public void setTempAndMaxMin(double temp, double tempMin, double tempMax) {
    this.temp = temp;
    this.tempMin = tempMin;
    this.tempMax = tempMax;
  }

  /**Sets the pressure. Pressure is in hPa (hectopascal).
   * 
   * @param pressure is the current pressure recorded
   */
  public void setPressure(long pressure) {
    this.pressure = pressure;
  }

  /**Sets the humidity. Humidity is in %.
   * 
   * @param humidity is the current humidity recorded
   */
  public void setHumidity(long humidity) {
    this.humidity = humidity;
  }

  /**Sets the wind speed. Wind speed in m/s.
   * 
   * @param windSpeed is the current wind speed recorded
   */
  public void setWindSpeed(double windSpeed) {
    this.windSpeed = windSpeed;
  }

  /**Sets the wind heading/direction. Wind heading in degrees.
   * 
   * @param windHeading is the current wind direction recorded
   */
  public void setWindHeading(double windHeading) {
    this.windHeading = windHeading;
  }

  /**Sets the sunrise time. See documentation of java.util.Date for details of Date.
   * 
   * @param sunrise is today sunrise time, in java.util.Date
   */
  public void setSunrise(Date sunrise) {
    this.sunrise = sunrise;
  }
  
  /**Sets the sunset time. See documentation of java.util.Date for details of Date.
   * 
   * @param sunset is today sunset time, in java.util.Date
   */
  public void setSunset(Date sunset) {
    this.sunset = sunset;
  }
  
  /**Sets the time when this data is taken. See documentation of java.util.Date for details of Date.
   * 
   * @param timestamp is the time, in java.util.Date
   */
  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
  
  
  /**Fills this Weather's fields from a JSON representation of data.
   * JSON format follows the format from OpenWeather servers, then converted
   * into a JSONObject. See json-simple documentation for more info on JSONObject.
   * If any of the fields is missing form the JSON data, the fields will be left blank.
   * 
   * @param data is the JSON data
   */
  public void fillFromJson(JSONObject data) {
    // Extracting city details
    JSONObject sys = (JSONObject) data.get("sys");
    try {
      JSONObject coord = (JSONObject) data.get("coord");
      double lat = ((Number) coord.get("lat")).doubleValue();
      double lon = ((Number) coord.get("lon")).doubleValue();
      String cityId = ((Number) data.get("id")).toString();
      String cityName = (String) data.get("name");
      String cityCountry = (String) sys.get("country");
      this.city = new City(cityId,cityName,cityCountry,lat,lon);
    } catch (NullPointerException npe) {
      // Just leave the fields blank
      this.city = null;
    }
    // Extracting sunrise and sunset time
    try {
      this.sunrise = new Date(((Long) sys.get("sunrise")) * 1000);
      this.sunset = new Date(((Long) sys.get("sunset")) * 1000);
    } catch (NullPointerException npe) {
      // Just leave the fields blank
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
  
  /**Converts the code into it's corresponding text weather conditions.
   * Look at OpenWeather API documentation for the code list.
   * 
   * @param code is the code that will be converted.
   * @return a String that is human-readable text weather conditions.
   */
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
  
  
  /**This is another weatherCodeToString method that takes Weather instead of just the code.
   * This method will automatically retrieves the weather code.
   * 
   * @param w is the Weather which the code will be read from.
   * @return a String that is human-readable text weather conditions.
   */
  public static String weatherCodeToString(Weather w) {
    return weatherCodeToString(w.getWeatherId());
  }
  
  /**Converts the weather code into an image code.
   * The image code can be consulted to the OpenWeather servers to retrieve the image that
   * represents the weather condition.
   * 
   * @param w is the Weather which the code will be read from
   * @return the image code
   */
  public static String weatherCodeToImageCode(Weather w) {
    int leading = w.getWeatherId() / 100;
    if (leading == 2) {
      return "11d";
    } else if (leading == 3) {
      return "09d";
    } else if (leading == 5) {
      int remainder = w.getWeatherId() % 100;
      if (remainder <= 10) {
        return "10d";
      } else if (remainder == 11) {
        return "13d";
      } else { // remainder > 11
        return "09d";
      }
    } else if (leading == 6) {
      return "13d";
    } else if (leading == 8) {
      int remainder = w.getWeatherId() % 10;
      if (remainder == 0) {
        return "01d";
      } else if (remainder == 1) {
        return "02d";
      } else if (remainder == 2) {
        return "03d";
      } else {
        return "04d";
      }
    } else { // code leading is 7 or not of the above
      return "50d";
    }
    
  }
}
