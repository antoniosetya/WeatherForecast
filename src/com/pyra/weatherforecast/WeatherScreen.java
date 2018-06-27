package com.pyra.weatherforecast;

import com.pyra.weatherforecast.data.*;
import java.awt.Color;
//import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JFrame;

public class WeatherScreen extends JFrame {
  
  private Weather cityWeather;
  
  public WeatherScreen(City city) {
    setBackground(Color.WHITE);
    setLayout(null);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setVisible(true);
    cityWeather = new Weather();
    this.cityWeather.setCity(city.clone());
    this.setTitle(this.cityWeather.getCity().getName());
    refreshWeather();
  }
  
  public void refreshWeather() {
    WeatherGrabber wg = new WeatherGrabber(cityWeather.getCity().getId());
    try {
      wg.grabWeather();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }
  
}
