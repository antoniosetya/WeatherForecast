package com.pyra.weatherforecast;

import com.pyra.weatherforecast.data.City;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Main extends JFrame {

  private static final long serialVersionUID = 2504100948045362609L;
  private JPanel contents;
  private JPanel searchResultContainer;
  private JScrollPane scrollableResultContainer;
  private ArrayList<ResultElement> searchResultView = new ArrayList<ResultElement>();
  private ArrayList<WeatherScreen> weatherWindows = new ArrayList<WeatherScreen>();
  private HashMap<String,Image> weatherIconCache = new HashMap<String,Image>();
  private JTextField searchQuery;
  private JButton searchButton;
  private CitySearcher searcher;
  private Thread cleaner;
  
  /**The Main constructor.
   * It will initialize the search window.
   */
  public Main() {
    // Setting up initial content
    contents = new JPanel();
    contents.setPreferredSize(new Dimension(337,640));
    contents.setBackground(Color.WHITE);
    contents.setLayout(null);
    
    /* Adding elements to content */
    
    // The search bar
    searchQuery = new JTextField(20);
    searchQuery.setBounds(5,5,228,25);
    searchQuery.setToolTipText("Insert search query here...");
    // Execute the search on enter keypress
    searchQuery.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        doSearch();
      }
      
    });
    contents.add(searchQuery);
    
    // The search button
    searchButton = new JButton("Search");
    searchButton.setBounds(238,5,93,25);
    contents.add(searchButton);
    // Button click listener
    searchButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(final MouseEvent me) {
        if (SwingUtilities.isLeftMouseButton(me)) {
          doSearch();
        }
      }
    });
    
    // Result container
    scrollableResultContainer = new JScrollPane(
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollableResultContainer.setBounds(5, 35, 327, 600);
    scrollableResultContainer.getVerticalScrollBar().setUnitIncrement(10);
    scrollableResultContainer.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK));
    searchResultContainer = new JPanel();
    searchResultContainer.setBackground(Color.WHITE);
    searchResultContainer.setLayout(new GridLayout(0,1));
    scrollableResultContainer.setViewportView(searchResultContainer);
    contents.add(scrollableResultContainer);
    
    // Configure window and pack everything together
    getContentPane().add(contents); 
    setResizable(false);
    setTitle("Weather Forecast - Search City");   
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    
    // Initialize searcher
    searcher = CitySearcher.getSearcher();
    
    // Gets a separate thread to clean all additional Window(s) from 
    // weatherWindows once they're closed
    cleaner = new Thread(new Runnable() {
      public void run() {
        while (true) {
          weatherWindows.removeIf(w -> !(w.isAlive()));
          try {
            Thread.sleep(1000);
          } catch (InterruptedException ie) {
            // Carry on, no need to handle cause this is normal
          }
        }
      }
    });
    cleaner.start();
  }
  
  private void doSearch() {
    // Clearing results first
    if (!searchResultView.isEmpty()) {
      searchResultView.clear();
      searchResultContainer.removeAll();
    }
    // If the search query is less than 3 characters
    if (searchQuery.getText().length() <= 3) {
      searchResultView.add(new ResultElement(
          new City("","Please use more than 3 characters...")));
      searchResultContainer.add(searchResultView.get(searchResultView.size() - 1));
    } else {
      // Search with the query
      searcher.setSearchQuery(searchQuery.getText());
      searcher.search();
      for (City result : searcher.getResult()) {
        searchResultView.add(new ResultElement(result));
        searchResultContainer.add(searchResultView.get(searchResultView.size() - 1));
        ResultElement temp = searchResultView.get(searchResultView.size() - 1);
        temp.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseReleased(MouseEvent me) {
            if (SwingUtilities.isLeftMouseButton(me)) {
              WeatherScreen tempScreen = new WeatherScreen(temp.getCity());
              tempScreen.setWeatherIconCache(weatherIconCache);
              weatherWindows.add(tempScreen);
            }
          }
          
          @Override
          public void mouseEntered(MouseEvent me) {
            temp.hovered();
          }
          
          @Override
          public void mouseExited(MouseEvent me) {
            temp.unhovered();
          }
        });
      }
      // If the searcher gets an error...
      if (searcher.getStatusCode() != 0) {
        searchResultView.add(new ResultElement(
            new City("","There's something wrong while loading city database."
                + "Search cannot be performed.")));
        searchResultContainer.add(searchResultView.get(searchResultView.size() - 1));
      // If the searcher doesn't found any results.
      } else if (searchResultView.size() == 0) {
        searchResultView.add(new ResultElement(
            new City("","Cannot found " + searchQuery.getText())));
        searchResultContainer.add(searchResultView.get(searchResultView.size() - 1));
      }
    }
    // Revalidate & repaint UI to reflect changes.
    searchResultContainer.revalidate();
    searchResultContainer.repaint();
  }
  
  private class ResultElement extends JPanel {
    
    private static final long serialVersionUID = -8748314195373864376L;
    private JLabel cityName;
    private JLabel pos;
    private City city;
    
    public ResultElement(City in) {
      this.city = in.clone();
      setPreferredSize(new Dimension(326,70));
      setBorder(BorderFactory.createLineBorder(Color.BLACK));
      // Label for city name
      cityName = new JLabel(in.getName() + ", " + in.getCountry());
      cityName.setForeground(Color.WHITE);
      
      // Label for latitude
      pos = new JLabel("Coordinate : " + in.getLat() + "," + in.getLon());
      pos.setForeground(Color.WHITE);
      // Layout manager
      GroupLayout lm = new GroupLayout(this);
      this.setLayout(lm);
      lm.setAutoCreateGaps(true);
      lm.setAutoCreateContainerGaps(true);
      lm.setHorizontalGroup(
          lm.createSequentialGroup()
            .addGroup(lm.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(cityName)
                .addComponent(pos)
            )
      );
      lm.setVerticalGroup(
          lm.createSequentialGroup()
            .addComponent(cityName)
            .addComponent(pos)
      );
      // Sets initial background color
      unhovered();
    }

    public City getCity() {
      return city;
    }
  
    public void hovered() {
      this.setBackground(Color.DARK_GRAY);
    }
    
    public void unhovered() {
      this.setBackground(Color.GRAY);
    }
  }
  
  /* The main method */
  public static void main(String[] args) {
    Main mainWindow = new Main();
    mainWindow.setVisible(true);
  }
  
  
}