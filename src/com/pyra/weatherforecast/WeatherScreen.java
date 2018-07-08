package com.pyra.weatherforecast;

import com.pyra.weatherforecast.data.City;
import com.pyra.weatherforecast.data.Forecast;
import com.pyra.weatherforecast.data.Weather;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle;

public class WeatherScreen extends JFrame {
  
  private static final long serialVersionUID = 5171748256390903168L;
  // Data
  private Weather cityWeather; // Weather data
  private Forecast cityForecast; // Forecast data
  private int unitChoice = 0; // 0 for Metric, 1 for Imperial
  private HashMap<String,Image> weatherIconCache; // the cache for weather icons
  private boolean isAlive;
  // Constant String of the degree symbol
  private final String degree = Character.toString((char) 0x00b0);
  
  // Top-level container
  private JTabbedPane parentTab = new JTabbedPane();
  private JPanel weatherTab = new JPanel();
  private JScrollPane forecastTab = new JScrollPane(
      JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
  private JPanel forecastContainer = new JPanel();
  private JMenuBar menubar;
  // Weather panel elements
  private GroupLayout lmw;
  private JLabel cityName;
  private JLabel weatherCondition;
  private JLabel weatherIcon;
  private JLabel temperature;
  private JLabel pressure;
  private JLabel humidity;
  private JLabel lefttime;
  private JLabel righttime;
  private JLabel middletime;
  private JLabel wind;
  
  
  /**The main constructor for WeatherScreen.
   * 
   * @param city is the city displayed by this WeatherScreen
   */
  public WeatherScreen(City city) {
    // Marking this window as active/alive
    this.isAlive = true;
    // Setting initial window settings
    setBackground(Color.WHITE);
    parentTab.setBackground(Color.WHITE);
    setMinimumSize(new Dimension(500,400));
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setVisible(true);
    
    // Initializing data
    cityWeather = new Weather();
    cityForecast = new Forecast(city.clone());
    cityWeather.setCity(city.clone());
    
    // Set city name as this window title
    this.setTitle("Loading...");
    
    // Setting up GUI elements of this window
    menubar = new JMenuBar();
    JMenu units = new JMenu("Units");
    JMenuItem unitMetric = new JMenuItem("Metric (" + degree + "C, m/s, kPa)");
    unitMetric.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        unitChoice = 0;
        refreshAllThreaded();
      }
    });
    
    JMenuItem unitImperial = new JMenuItem("Imperial (" + degree + "F, ft/s, psi)");
    unitImperial.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        unitChoice = 1;
        refreshAllThreaded();
      }
    });
    
    units.add(unitMetric);
    units.add(unitImperial);
    menubar.add(units);
    this.setJMenuBar(menubar);
    
    // Setting up GUI elements of tabs and weather tab
    lmw = new GroupLayout(weatherTab);
    weatherTab.setLayout(lmw);
    parentTab.addTab("Weather", weatherTab);
    parentTab.addTab("3-Hourly Forecast", forecastTab);
    forecastTab.setViewportView(forecastContainer);
    forecastTab.getVerticalScrollBar().setUnitIncrement(10);
    forecastContainer.setLayout(new GridLayout(0,1));
    setContentPane(parentTab);
    weatherCondition = new JLabel();
    weatherIcon = new JLabel();
    cityName = new JLabel(cityWeather.getCity().getName());
    temperature = new JLabel();
    pressure = new JLabel();
    humidity = new JLabel();
    wind = new JLabel();
    lefttime = new JLabel();
    righttime = new JLabel();
    middletime = new JLabel();
    
    // Refresh each tab
    setupWeatherTab();
    refreshWeatherTab();
    refreshForecastTab();
    weatherCondition.setText("Loading...");
    parentTab.revalidate();
    parentTab.repaint();
    
    // Grabs weather data
    Thread weatherThread = new Thread(new Runnable() {
      public void run() {
        refreshWeather();        
      }
    });
    // Grabs forecast data
    Thread forecastThread = new Thread(new Runnable() {
      public void run() {
        refreshForecast();        
      }
    });
    weatherThread.start();
    forecastThread.start();
    
    // Setting up listener for when this window is closing
    this.addWindowListener(new WindowAdapter() {
      
      @Override
      public void windowClosing(WindowEvent we) {
        isAlive = false;
      }
    });
  }
  
  /**Gets the icon cache this WeatherScreen is currently using.
   * 
   * @return the icon cache, a HashMap
   */
  public HashMap<String,Image> getWeatherIconCache() {
    return weatherIconCache;
  }
  
  /**Gets whether this WeatherScreen is still active or not.
   * 
   * @return true if it is still active, false if not
   */
  public boolean isAlive() {
    return isAlive;
  }
  
  /**Sets WeatherScreen to use your supplied cache.
   * 
   * @param cache : the HashMap cache this WeatherScreen will use
   */
  public void setWeatherIconCache(HashMap<String,Image> cache) {
    this.weatherIconCache = cache;
  }
  
  private void setupWeatherTab() {
    // Sets initial settings
    weatherTab.setBackground(Color.WHITE);
    weatherTab.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    // Sets each label text size
    weatherCondition.setFont(new Font(weatherCondition.getFont().getName(),Font.PLAIN,24));
    // Layout manager auto creating gaps between each element 
    lmw.setAutoCreateGaps(true);
    // Setting horizontal layout
    lmw.setHorizontalGroup(
        lmw.createSequentialGroup()
          .addGroup(lmw.createParallelGroup(GroupLayout.Alignment.LEADING)
              .addGroup(lmw.createSequentialGroup()
                  .addGroup(lmw.createParallelGroup(GroupLayout.Alignment.LEADING)
                      .addComponent(weatherCondition)
                      .addComponent(cityName)
                      .addComponent(temperature)
                  )
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                      GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE)
                  .addComponent(weatherIcon)
              )
              .addGroup(lmw.createSequentialGroup()
                  .addComponent(pressure)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                      GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE)
                  .addComponent(humidity)
              )
              .addComponent(wind)
              .addGroup(lmw.createSequentialGroup()
                  .addComponent(lefttime)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                      GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE)
                  .addComponent(righttime)
              )
              .addGroup(lmw.createSequentialGroup()
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                      GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE)
                  .addComponent(middletime)
                  .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                      GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE)

              )
          )
          
    );
    // Setting vertical layout
    lmw.setVerticalGroup(
        lmw.createSequentialGroup()
          .addGroup(lmw.createParallelGroup(GroupLayout.Alignment.BASELINE)
              .addGroup(lmw.createSequentialGroup()
                  .addComponent(weatherCondition)    
                  .addComponent(cityName)
                  .addComponent(temperature)
              )
              .addComponent(weatherIcon)
          )
          .addGroup(lmw.createParallelGroup(GroupLayout.Alignment.BASELINE)
              .addComponent(pressure)
              .addComponent(humidity)
          )
          .addComponent(wind)
          .addGroup(lmw.createParallelGroup(GroupLayout.Alignment.BASELINE)
              .addComponent(lefttime)
              .addComponent(righttime)
          )
          .addComponent(middletime)
          
    );
    weatherTab.revalidate();
    weatherTab.repaint();
  }
  
  private void clearWeatherTab() {
    weatherCondition.setText("");
    temperature.setText("");
    pressure.setText("");
    humidity.setText("");
    wind.setText("");
    weatherTab.revalidate();
    weatherTab.repaint();
  }
  
  private void refreshWeatherTab() {
    double tempVal;
    double pressureVal;
    double windSpeedVal;
    String tempUnit;
    String pressureUnit;
    String windSpeedUnit;
    
    // Calculating/converting units
    if (unitChoice == 0) {
      tempVal = cityWeather.getTemp() - 273.15;
      tempUnit = degree + "C";
      pressureVal = cityWeather.getPressure() * 0.1;
      pressureUnit = "kPa";
      windSpeedVal = cityWeather.getWindSpeed();
      windSpeedUnit = "m/s";
    } else { // unitChoice == 1
      tempVal = (cityWeather.getTemp() * (9 / (double) 5)) - 459.67;
      tempUnit = degree + "F";
      pressureVal = cityWeather.getPressure() * 0.014503773773;
      pressureUnit = "psi";
      windSpeedVal = cityWeather.getWindSpeed() * 3.28084;
      windSpeedUnit = "ft/s";
    }
    
    // Setting labels
    weatherCondition.setText(Weather.weatherCodeToString(cityWeather));
    cityName.setText(cityWeather.getCity().getName() + ", " + cityWeather.getCity().getCountry());
    temperature.setText(String.format("%.2f " + tempUnit, tempVal));
    pressure.setText(String.format("Pressure : %.2f " + pressureUnit,pressureVal));
    humidity.setText("Humidity : " + cityWeather.getHumidity() + "%");
    wind.setText(String.format("Wind : %.2f " + windSpeedUnit,windSpeedVal));
    
    // If wind data is collected, show it
    if (cityWeather.getWindHeading() >= 0) {
      wind.setText(wind.getText() + " (" + cityWeather.getWindHeading() + degree + ")");
    }
    
    // If weather icon is successfully retrieved, show it
    if (cityWeather.getWeatherIcon() != null) {
      weatherIcon.setIcon(new ImageIcon(cityWeather.getWeatherIcon()));      
    }
    
    // If sunrise & sunset data is retrieved. show it
    if (cityWeather.getSunrise() != null && cityWeather.getSunset() != null) {
      lefttime.setText(String.format("Sunrise : " + cityWeather.getSunrise().toString()));
      righttime.setText("Sunset : " + cityWeather.getSunset().toString());
      Date currenttime = new Date();
      // Determining whether now is daytime or nighttime and shows it.
      // If daytime, also shows remaining time of day.
      if (getTimeDifference(currenttime,cityWeather.getSunrise()) > 0 
          && getTimeDifference(cityWeather.getSunset(),currenttime) > 0) {
        middletime.setText("Day. Remaining daylight : " 
            + printTimeDifference(getTimeDifference(cityWeather.getSunset(), new Date())));
      } else {
        middletime.setText("Night");
      }
    }
    
    // Revalidate & repaint, so changes will show to UI.
    weatherTab.revalidate();
    weatherTab.repaint();
  }
  
  private void refreshForecastTab() {
    // Clears the forecast data first
    clearForecastTab();
    // Checks the forecast data
    if (cityForecast.getForecast().size() <= 0) {
      JLabel temp;
      if (this.getTitle() == "Loading...") {
        temp = new JLabel("Forecast data is still loading...");
      } else {
        temp = new JLabel("Forecast data not available");
      }
      forecastContainer.add(temp);
    } else { // There is forecast data to show
      for (Weather elm : cityForecast.getForecast()) {
        forecastContainer.add(new ForecastElement(elm, unitChoice));
      }
    }
    // Revalidate & repaint, so changes will show to UI.
    forecastContainer.revalidate();
    forecastContainer.repaint();
  }
  
  private void clearForecastTab() {
    forecastContainer.removeAll();
    forecastContainer.revalidate();
    forecastContainer.repaint();
  }
    
  private void refreshAllThreaded() {
    // Creating threads
    Thread wthread = new Thread(new Runnable() {
      public void run() {
        refreshWeather();            
      }
    });
    Thread fthread = new Thread(new Runnable() {
      public void run() {
        refreshForecast();            
      }
    });
    // Start the threads
    wthread.start();
    fthread.start();
  }
  
  private void refreshWeather() {
    this.setTitle("Loading...");
    WeatherGrabber wg = new WeatherGrabber(cityWeather.getCity().getId());
    wg.setWeatherIconCache(weatherIconCache);
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
    this.setTitle(cityWeather.getCity().getName() + ", "  + cityWeather.getCity().getCountry());
  }
  
  private void refreshForecast() {
    this.setTitle("Loading...");
    WeatherGrabber wg = new WeatherGrabber(cityForecast.getCity().getId());
    wg.setWeatherIconCache(weatherIconCache);
    wg.grabForecast(cityForecast);
    if (wg.getStatus() == 200) {
      refreshForecastTab();
    } else {
      clearForecastTab();
    }
    this.setTitle(cityForecast.getCity().getName() + ", " + cityForecast.getCity().getCountry());
  }
 
  private long getTimeDifference(Date first, Date second) {
    return (first.getTime() - second.getTime()) / 1000;
  }
  
  private String printTimeDifference(long diff) {
    String hours = " h ";
    String minutes = " m ";
    String seconds = " s";
    
    hours = (diff / 3600) + hours;
    minutes = ((diff % 3600) / 60) + minutes;
    seconds = (diff % 60) + seconds;
    return (hours + minutes + seconds);
  }

  private class ForecastElement extends JPanel {
    
    private static final long serialVersionUID = 3777979755900617018L;
    private JLabel time;
    private JLabel weatherCondition;
    private JLabel weatherIcon;
    private JLabel temperature;
    
    public ForecastElement(Weather in, int unitChoice) {
      setBackground(Color.WHITE);
      setPreferredSize(new Dimension(326,70));
      setBorder(BorderFactory.createLineBorder(Color.BLACK));
      // Time label
      time = new JLabel(in.getTimestamp().toString());
      // Forecasted weather label
      weatherCondition = new JLabel(Weather.weatherCodeToString(in));
      // Temperature label
      String temperatureText;
      if (unitChoice == 0) {
        temperatureText = String.format("%.2f " + degree + "C",(in.getTemp() - 273.15));        
      } else {
        temperatureText = String.format("%.2f " + degree + "F",
            (in.getTemp() * (9 / (double) 5)) - 459.67);
      }
      temperature = new JLabel(temperatureText);
      // Icon
      weatherIcon = new JLabel();
      if (in.getWeatherIcon() != null) {
        weatherIcon.setIcon(new ImageIcon(in.getWeatherIcon()));        
      }
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
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                GroupLayout.PREFERRED_SIZE,Short.MAX_VALUE)
            .addComponent(weatherIcon)
      );
      lm.setVerticalGroup(
          lm.createSequentialGroup()
            .addGroup(lm.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addGroup(lm.createSequentialGroup()
                    .addComponent(time)
                    .addGroup(lm.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(weatherCondition)
                        .addComponent(temperature)
                    )
                )
                .addComponent(weatherIcon)
            ) 
      );
    }
    
  }
  
}
