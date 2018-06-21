package com.pyra.weatherforecast;

import com.pyra.weatherforecast.data.City;
import java.io.IOException;
import java.net.MalformedURLException;

public class Main {

  /* The main method */
  public static void main(String[] args) {
    Screen window = new Screen();
    CitySearcher searcher = CitySearcher.getSearcher();
    searcher.setSearchQuery("Seat");
    searcher.search();
    for (City c : searcher.getResult()) {
      System.out.println(c);
    }
    /*
    WeatherGrabber wg = new WeatherGrabber("1642907");
    try {
      wg.grabForecast();
    } catch (MalformedURLException mue) {
      System.out.println("Wrong URL!");
    } catch (IOException ie) {
      System.out.println("Connection error!");
    }*/
  }
  
  
}