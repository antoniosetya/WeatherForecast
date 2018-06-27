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
    System.out.println("Loading file...");
    try {
      dataBuffer = (JSONArray) parser.parse(new FileReader("./city.list.min.json"));
      statusCode = 0;
    } catch (FileNotFoundException fnfe) {
      System.out.println("Cannot find city data(base)!");
      statusCode = 1;
    } catch (IOException ie) {
      System.out.println("Failed to read city data(base)!");
      statusCode = 999;
    } catch (ParseException pe) {
      System.out.println("City data(base) is corrupted!");
      statusCode = 2;
    } finally {
      if (statusCode != 0) {
        dataBuffer = new JSONArray();
      }
    }
    System.out.println("File loaded.");
  } 
  
  public static CitySearcher getSearcher() {
    return searcher;
  }
  
  public ArrayList<City> getResult() {
    return resultBuffer;
  }
  
  public String getSearchQuery() {
    return searchQuery;
  }
  
  public void setSearchQuery(String searchQuery) {
    this.searchQuery = searchQuery.toLowerCase();
  }

  public boolean isRunning() {
    return isRunning;
  }

  public int getStatusCode() {
    return statusCode;
  }
  
  public void search() {
    if (statusCode != 0) {
      System.out.println("City data(base) is not loaded properly!");
    } else if (searchQuery == null || searchQuery.isEmpty()) {
      System.out.println("No search query");
    } else {
      System.out.println("Starting search of " + searchQuery);
      isRunning = true;
      if (!resultBuffer.isEmpty()) {
        resultBuffer.clear();
      }
      for (Object obj : dataBuffer) {
        JSONObject temp = (JSONObject) obj;
        String cityName = (String) temp.get("name");
        if (cityName.toLowerCase().contains(searchQuery)) {
          String cityId = ((Number) temp.get("id")).toString();
          String country = (String) temp.get("country");
          JSONObject coord = (JSONObject) temp.get("coord");
          double lat = ((Number) coord.get("lat")).doubleValue();
          double lon = ((Number) coord.get("lon")).doubleValue();
          resultBuffer.add(new City(cityId,cityName,country,lat,lon));        }
        if (!isRunning) {
          break;
        }
      }
      isRunning = false;
      System.out.println("Search finished");
    }
  }
  
  public void stopSearch() {
    isRunning = false;
  }
}
