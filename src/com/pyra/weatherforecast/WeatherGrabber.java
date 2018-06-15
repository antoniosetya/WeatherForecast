package com.pyra.weatherforecast;

// import httpUrlConnection

public class WeatherGrabber {

  private String url;
  //private <JSONDataStruct> data;
  
  public WeatherGrabber() {
    this.url = "";
  }
  
  public WeatherGrabber(final String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
  
  public void getWeather() {
    
  }
  
  public void process() {
    // Planned to throw exception on connection errors
  }
  
}
