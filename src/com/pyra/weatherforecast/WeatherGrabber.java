package com.pyra.weatherforecast;

import com.pyra.weatherforecast.data.Forecast;
import com.pyra.weatherforecast.data.Weather;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WeatherGrabber {

  private static final String apiKey = "962b557f8e216a4abb348130dc4659f0";
  private static final String baseURL = "http://api.openweathermap.org/data/2.5/";
  private String cityId;
  private HttpURLConnection conn;
  private int status;
  private HashMap<String,Image> weatherIconCache;
  private JSONObject data;
    
  /**The main constructor of WeatherGrabber.
   * 
   * @param cityId : the ID of a city whose data will be retrieved.
   */
  public WeatherGrabber(final String cityId) {
    this.cityId = cityId;
    this.weatherIconCache = new HashMap<String,Image>();
    resetStatus();
  }

  /**Returns the city ID of this WeatherGrabber.
   * 
   * @return the ID of the city whose data will be retrieved.
   */
  public String getCityId() {
    return cityId;
  }
  
  /**Returns the status of this WeatherGrabber.
   * Be aware that this may return null when either 
   * grabWeather or grabForecast hasn't been invoked yet.
   * Any negative values means that there's an internal error occured.
   * Other values represent HTTP status codes received from the (OpenWeather) server.
   * 
   * @return the status code of this WeatherGrabber.
   */
  public int getStatus() {
    return status;
  }

  
  /**Sets the city ID whose data will be retrieved by this WeatherGrabber.
   * 
   * @param cityId : the city ID
   */
  public void setCityId(String cityId) {
    this.cityId = cityId;
  }
  
  private void resetStatus() {
    status = 0;
  }
  
  private void makeRequest(char mode) {
    resetStatus();
    String tempString;
    if (mode == 'w')  {
      tempString = baseURL + "weather?id=" + cityId + "&appid=" + apiKey;
    } else { // mode == 'f'
      tempString = baseURL + "forecast?id=" + cityId + "&appid=" + apiKey;
    }
    URL curUrl = null;
    try {
      curUrl = new URL(tempString);
    } catch (MalformedURLException mue) {
      status = -999;
    }
    if (status != -999) {
      try {
        conn = (HttpURLConnection) curUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent","Mozilla/5.0");        
      } catch (IOException ie) {
        status = -998;
      }
    }
  }
  
  /**Grabs weather data from (OpenWeather) server and placed it in "in".
   * 
   * @param in : the Weather variable where the data from server will be stored in.
   */
  public void grabWeather(Weather in) {
    // Opening & requesting data to OpenWeather
    makeRequest('w');
    
    if ((status != -998) && (status != -999)) {
      try {
        status = conn.getResponseCode();
      } catch (IOException ie) {
        status = -998;
      }
      if (status == HttpURLConnection.HTTP_OK) {
        // Request OK - read data
        StringBuffer jsonString = new StringBuffer();
        try {
          BufferedReader resData = new BufferedReader(new InputStreamReader(conn.getInputStream()));
          String temp = resData.readLine();
          while (temp != null) {
            jsonString.append(temp);
            temp = resData.readLine();
          }
        } catch (IOException ie) {
          status = -998;
        }
        // Parsing JSON data
        JSONParser parser = new JSONParser();
        try {
          data = (JSONObject) parser.parse(jsonString.toString());        
        } catch (ParseException pe) {
          status = -997;
        }
        in.fillFromJson(data);
        grabIcon(in);
      } // else, do nothing
    }
  }
  
  /**Grabs forecast data from (OpenWeather) server and placed it in "in".
   * 
   * @param in : the Forecast variable where the data from server will be stored in.
   */
  public void grabForecast(Forecast in) {
    // Opening & requesting data to OpenWeather
    makeRequest('f');
    
    if ((status != -998) && (status != -999)) {
      try {
        status = conn.getResponseCode();
      } catch (IOException ie) {
        status = -998;
      }
      if (status == HttpURLConnection.HTTP_OK) {
        // Request OK - read data
        StringBuffer jsonString = new StringBuffer();
        try {
          BufferedReader resData = new BufferedReader(new InputStreamReader(conn.getInputStream()));
          String temp = resData.readLine();
          while (temp != null) {
            jsonString.append(temp);
            temp = resData.readLine();
          }
        } catch (IOException ie) {
          status = -998;
        }
        // Parsing JSON data
        JSONParser parser = new JSONParser();
        try {
          data = (JSONObject) parser.parse(jsonString.toString());        
        } catch (ParseException pe) {
          System.out.println(pe);
        }
        in.addFromJson(data);
        for (Weather w : in.getForecast()) {
          grabIcon(w);
        }
      }
    }
  }
  
  private void grabIcon(Weather data) {
    String code = Weather.weatherCodeToImageCode(data);
    Image temp = weatherIconCache.get(code);
    if (temp == null) { // if temp is null, grab icon from server
      try {
        URL url = new URL("http://openweathermap.org/img/w/" + code + ".png");
        temp = ImageIO.read(url);
      } catch (MalformedURLException e) {
        e.printStackTrace();
        temp = null;
      } catch (IOException e) {
        e.printStackTrace();
        temp = null;
      }
      weatherIconCache.put(code, temp);
    }
    data.setWeatherIcon(temp);
  }
}
