package com.pyra.weatherforecast.data;

public class City implements Cloneable {
  
  private String id;
  private String name;
  private String country;
  private double lat;
  private double lon;
  
  /**The main constructor for City.
   */
  public City() {
    id = "";
    name = "";
    country = "";
    lat = 0;
    lon = 0;
  }
  
  
  /**Alternate constructor for City.
   * 
   * @param id : the City ID, as defined by OpenWeather API
   */
  public City(String id) {
    this.id = id;
    name = "";
    country = "";
    lat = 0;
    lon = 0;
  }
  
  /**Alternate constructor for City.
   * 
   * @param id : the City ID, as defined by OpenWeather API
   * @param name : the City name
   */
  public City(String id, String name) {
    this.id = id;
    this.name = name;
    country = "";
    lat = 0;
    lon = 0;
  }
  
  /**Alternate constructor for City.
   * 
   * @param id : the City ID, as defined by OpenWeather API
   * @param name : the City name
   * @param country : the country where this City is located
   */
  public City(String id, String name, String country) {
    this.id = id;
    this.name = name;
    this.country = country;
    lat = 0;
    lon = 0;
  }
  
  /**Alternate constructor for City.
   * 
   * @param id : the City ID, as defined by OpenWeather API
   * @param name : the City name
   * @param country : the country where this City is located
   * @param lat : Latitute of this City
   * @param lon : Longitude of this City
   */
  public City(String id, String name, String country, double lat, double lon) {
    this.id = id;
    this.name = name;
    this.country = country;
    this.lat = lat;
    this.lon = lon;
  }
  
  /**Gets the ID of this City.
   * 
   * @return a String, the ID
   */
  public String getId() {
    return id;
  }
  
  /**Gets the name of this City.
   * 
   * @return a String, the name
   */
  public String getName() {
    return name;
  }
  
  /**Gets the country of this City.
   * 
   * @return a String, the country
   */
  public String getCountry() {
    return country;
  }
  
  /**Gets the latitude of this City.
   * 
   * @return a double, the latitude
   */
  public double getLat() {
    return lat;
  }
  
  /**Gets the longitude of this City.
   * 
   * @return a double, the longitude
   */
  public double getLon() {
    return lon;
  }
  
  /**Sets the ID of this City. Refer to OpenWeather API for city list and ID/codes.
   * 
   * @param id : the ID for this City
   */
  public void setId(String id) {
    this.id = id;
  }
  
  /**Sets the name of this City. 
   * 
   * @param name : the name for this City
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /**Sets the country of this City.
   * 
   * @param country : the country for this City.
   */
  public void setCountry(String country) {
    this.country = country;
  }
  
  /**Sets the latitude of this City.
   * 
   * @param lat : the latitude for this City
   */
  public void setLat(double lat) {
    this.lat = lat;
  }
  
  /**Sets the longitude of this City.
   * 
   * @param lon : the longitude for this City
   */
  public void setLon(double lon) {
    this.lon = lon;
  }
  
  @Override
  public String toString() {
    return "(" + id + ") " + name + ", " + country + " (" + lat + "," + lon + ")";
  }
  
  @Override
  public City clone() {
    return (new City(id,name,country,lat,lon));
  }
}
