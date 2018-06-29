package com.pyra.weatherforecast;

import com.pyra.weatherforecast.data.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class WeatherScreen extends JFrame {
  
  // Data
  private Weather cityWeather;
  private Forecast cityForecast;
  
  // Top-level container
  private JTabbedPane parentTab = new JTabbedPane();
  private JPanel weatherTab = new JPanel();
  private JScrollPane forecastTab = new JScrollPane(
      JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
  private JPanel forecastContainer = new JPanel();
  private GroupLayout lmw;
  private GroupLayout lmf;
  // Weather panel elements
  private JLabel cityName;
  private JLabel weatherCondition;
  private JLabel temperature;
  private JLabel temperatureLo;
  private JLabel temperatureHi;
  private JLabel pressure;
  private JLabel humidity;
  private JLabel wind;
  
  public WeatherScreen(City city) {
    // Setting initial window settings
    setBackground(Color.WHITE);
    setMinimumSize(new Dimension(500,500));
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setVisible(true);
    
    // Initializing data
    cityWeather = new Weather();
    cityForecast = new Forecast(city.clone());
    cityWeather.setCity(city.clone());
    
    // Set city name as this window title
    this.setTitle(cityWeather.getCity().getName());
    
    // Setting up GUI elements
    lmw = new GroupLayout(weatherTab);
    weatherTab.setLayout(lmw);
    parentTab.addTab("Weather", weatherTab);
    parentTab.addTab("3-Hourly Forecast", forecastTab);
    forecastTab.setViewportView(forecastContainer);
    forecastTab.getVerticalScrollBar().setUnitIncrement(10);
    forecastContainer.setLayout(new GridLayout(0,1));
    setContentPane(parentTab);
    weatherCondition = new JLabel("Loading...");
    cityName = new JLabel(cityWeather.getCity().getName());
    temperature = new JLabel();
    temperatureLo = new JLabel();
    temperatureHi = new JLabel();
    pressure = new JLabel();
    humidity = new JLabel();
    wind = new JLabel();
    
    // Refresh each tab
    setupWeatherTab();
    refreshForecastTab();
    
    // Grabs weather data
    refreshWeather();
    // Grabs forecast data
    refreshForecast();
  }
  
  private void setupWeatherTab() {
    lmw.setAutoCreateGaps(true);
    lmw.setAutoCreateContainerGaps(true);
    lmw.setHorizontalGroup(
        lmw.createSequentialGroup()
          .addGroup(lmw.createParallelGroup(GroupLayout.Alignment.LEADING)
              .addComponent(weatherCondition)
              .addComponent(cityName)
              .addGroup(lmw.createSequentialGroup()
                  .addComponent(temperatureLo)
                  .addComponent(temperature)
                  .addComponent(temperatureHi)
               )
              .addGroup(lmw.createSequentialGroup()
                  .addComponent(pressure)
                  .addComponent(humidity)
              )
              .addComponent(wind)
          )
    );
    lmw.setVerticalGroup(
        lmw.createSequentialGroup()
          .addComponent(weatherCondition)
          .addComponent(cityName)
          .addGroup(lmw.createParallelGroup(GroupLayout.Alignment.BASELINE)
              .addComponent(temperatureLo)
              .addComponent(temperature)
              .addComponent(temperatureHi)
          )
          .addGroup(lmw.createParallelGroup(GroupLayout.Alignment.BASELINE)
              .addComponent(pressure)
              .addComponent(humidity)
          )
          .addComponent(wind)
          
    );
    weatherTab.revalidate();
    weatherTab.repaint();
  }
  
  private void clearWeatherTab() {
    weatherCondition.setText("");
    temperatureLo.setText("");
    temperature.setText("");
    temperatureHi.setText("");
    pressure.setText("");
    humidity.setText("");
    wind.setText("");
    weatherTab.revalidate();
    weatherTab.repaint();
  }
  
  private void refreshWeatherTab() {
    weatherCondition.setText(Weather.weatherCodeToString(cityWeather));
    temperatureLo.setText("Low : " + cityWeather.getTempMin() + " K");
    temperature.setText(cityWeather.getTemp() + " K");
    temperatureHi.setText("High : " + cityWeather.getTempMax() + " K");
    pressure.setText("Pressure : " + cityWeather.getPressure() + " hPa");
    humidity.setText("Humidity : " + cityWeather.getHumidity() + "%");
    wind.setText("Wind : " + cityWeather.getWindSpeed() + " m/s");
    if (cityWeather.getWindHeading() >= 0) {
      wind.setText(wind.getText() + " (" + cityWeather.getWindHeading() + " deg.)");
    }
    weatherTab.revalidate();
    weatherTab.repaint();
  }
  
  private void refreshForecastTab() {
    clearForecastTab();
    if (cityForecast.getForecast().size() <= 0) {
      JLabel temp = new JLabel("Forecast data not available");
      forecastContainer.add(temp);
      forecastContainer.revalidate();
      forecastContainer.repaint();
    }
    else {
      for (Weather elm : cityForecast.getForecast()) {
        forecastContainer.add(new ForecastElement(elm));
      }
    }
    forecastContainer.revalidate();
    forecastContainer.repaint();
  }
  
  private void clearForecastTab() {
    forecastContainer.removeAll();
    forecastContainer.revalidate();
    forecastContainer.repaint();
  }
  
  public void refreshWeather() {
    WeatherGrabber wg = new WeatherGrabber(cityWeather.getCity().getId());
    wg.grabWeather(cityWeather);
    if (wg.getStatus() == 200) {
      refreshWeatherTab();
    } else {
      clearWeatherTab();
      weatherCondition.setText("An error occured...");
      cityName.setText("Code : " + wg.getStatus());
      weatherTab.revalidate();
      weatherTab.repaint();
    }
  }
  
  public void refreshForecast() {
    WeatherGrabber wg = new WeatherGrabber(cityForecast.getCity().getId());
    wg.grabForecast(cityForecast);
    if (wg.getStatus() == 200) {
      refreshForecastTab();
    } else {
      clearForecastTab();
    }
  }
 

  private class ForecastElement extends JPanel {
    
    private JLabel time;
    private JLabel weatherCondition;
    private JLabel temperature;
    
    public ForecastElement(Weather in) {
      setPreferredSize(new Dimension(326,70));
      setBorder(BorderFactory.createLineBorder(Color.BLACK));
      // Time label
      time = new JLabel(in.getTimestamp().toString());
      // Forecasted weather label
      weatherCondition = new JLabel(Weather.weatherCodeToString(in));
      // Temperature label
      temperature = new JLabel(in.getTemp() + " K");
      // Layout manager
      GroupLayout lm = new GroupLayout(this);
      this.setLayout(lm);
      lm.setAutoCreateGaps(true);
      lm.setAutoCreateContainerGaps(true);
      lm.setHorizontalGroup(
          lm.createSequentialGroup()
            .addGroup(lm.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(time)
                .addGroup(lm.createSequentialGroup()
                    .addComponent(weatherCondition)
                    .addComponent(temperature)
                )
            )
      );
      lm.setVerticalGroup(
          lm.createSequentialGroup()
            .addComponent(time)
            .addGroup(lm.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(weatherCondition)
                .addComponent(temperature)
            )
      );
    }
    
  }
  
}
