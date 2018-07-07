package com.pyra.weatherforecast;

import com.pyra.weatherforecast.data.City;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CitySearcher {
  
  private ArrayList<City> resultBuffer = new ArrayList<City>();
  private JSONArray dataBuffer;
  private String searchQuery;
  private boolean isRunning;
  private int statusCode;
  private static CitySearcher searcher = new CitySearcher();
  
  private CitySearcher() {
    JSONParser parser = new JSONParser();
    try {
      dataBuffer = (JSONArray) parser.parse(new FileReader("./city.list.min.json"));
      statusCode = 0;
    } catch (FileNotFoundException fnfe) {
      statusCode = 1;
    } catch (IOException ie) {
      statusCode = 999;
    } catch (ParseException pe) {
      statusCode = 2;
    } finally {
      if (statusCode != 0) {
        dataBuffer = new JSONArray();
      }
    }
  } 
  
  
  /**Getter for the CitySearcher object.
   * The CitySearcher is a singleton object.
   * To use this object, get the object by this method.
   * 
   * @return the CitySearcher object.
   */
  public static CitySearcher getSearcher() {
    return searcher;
  }
  
  
  /**Returns the search results. 
   * If method search() isn't invoked yet, it will returns an empty list.
   * @return the results in ArrayList of City.
   */
  public ArrayList<City> getResult() {
    return resultBuffer;
  }
  
  /**Returns the search query that has been set.
   * @return the placed search query.
   */
  public String getSearchQuery() {
    return searchQuery;
  }
  
  /**Sets the search query for the searcher.
   * @param searchQuery : the desired search query
   */
  public void setSearchQuery(String searchQuery) {
    this.searchQuery = searchQuery.toLowerCase();
  }

  /**Returns true if searcher is currently searching, false if not.
   * 
   * @return true/false
   */
  public boolean isRunning() {
    return isRunning;
  }

  /**Returns the status of this searcher.
   * Code 0 means it's all fine.
   * Code 1 means that the city database (file city.list.min.json) cannot be found.
   * Code 2 and 999 means that there's an error while reading the city database.
   * 
   * @return the status code
   */
  public int getStatusCode() {
    return statusCode;
  }
  
  /**Fires the search.
   * Make sure the query is already set by invoking setSearchQuery("yoursearchquery"),
   * and the city database is properly loaded (the status code is 0). 
   * Grab the results with getResult().
   */
  public void search() {
    if (statusCode == 0 && !(searchQuery == null || searchQuery.isEmpty())) {
      isRunning = true;
      resultBuffer.clear();
      for (Object obj : dataBuffer) {
        JSONObject temp = (JSONObject) obj;
        String cityName = (String) temp.get("name");
        if (cityName.toLowerCase().contains(searchQuery)) {
          String cityId = ((Number) temp.get("id")).toString();
          String country = (String) temp.get("country");
          JSONObject coord = (JSONObject) temp.get("coord");
          double lat = ((Number) coord.get("lat")).doubleValue();
          double lon = ((Number) coord.get("lon")).doubleValue();
          resultBuffer.add(new City(cityId,cityName,country,lat,lon));        
        }
        if (!isRunning) {
          break;
        }
      }
      isRunning = false;
    }
  }
  
  /**Stops the searcher if the searcher is running.
   * 
   */
  public void stopSearch() {
    isRunning = false;
  }
}
