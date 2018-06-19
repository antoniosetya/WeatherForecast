package com.pyra.weatherforecast;

import java.io.IOException;
import java.net.MalformedURLException;

public class Main {

  /* The main method */
  public static void main(String[] args) {
    WeatherGrabber wg = new WeatherGrabber("1642907");
    try {
      wg.grabForecast();
    } catch (MalformedURLException mue) {
      System.out.println("Wrong URL!");
    } catch (IOException ie) {
      System.out.println("Connection error!");
    }
  }
  
  
}