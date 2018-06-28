package com.pyra.weatherforecast;

import com.pyra.weatherforecast.data.*;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class WeatherScreen extends JFrame {
  
  // Data
  private Weather cityWeather;
  
  // Top-level container
  private JTabbedPane parentTab = new JTabbedPane();
  private JPanel weatherTab = new JPanel();
  private JPanel forecastTab = new JPanel();
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
    this.cityWeather.setCity(city.clone());
    // Set city name as this window title
    this.setTitle(this.cityWeather.getCity().getName());
    // Setting up GUI elements
    lmw = new GroupLayout(weatherTab);
    lmf = new GroupLayout(forecastTab);
    weatherTab.setLayout(lmw);
    forecastTab.setLayout(lmf);
    parentTab.addTab("Weather", weatherTab);
    parentTab.addTab("3-Hourly Forecast", forecastTab);
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
    //Grabs weather data
    refreshWeather();
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
    JLabel temp = new JLabel("Coming, uhh..., soon enough...");
    lmf.setAutoCreateGaps(true);
    lmf.setAutoCreateContainerGaps(true);
    lmf.setHorizontalGroup(
        lmf.createSequentialGroup()
          .addGroup(lmf.createParallelGroup(GroupLayout.Alignment.LEADING)
              .addComponent(temp)
          )
    );
    lmf.setVerticalGroup(
        lmf.createSequentialGroup()
          .addComponent(temp)
    );
    forecastTab.revalidate();
    forecastTab.repaint();
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
  
}
