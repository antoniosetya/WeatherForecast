package com.pyra.weatherforecast.data;

public class City {
  
  private String id;
  private String name;
  private String country;
  private double lat;
  private double lon;
  
  public City() {
    id = "";
    name = "";
    country = "";
    lat = 0;
    lon = 0;
  }
  
  public City(String id) {
    this.id = id;
    name = "";
    country = "";
    lat = 0;
    lon = 0;
  }
  
  public City(String id, String name) {
    this.id = id;
    this.name = name;
    country = "";
    lat = 0;
    lon = 0;
  }
  
  public City(String id, String name, String country) {
    this.id = id;
    this.name = name;
    this.country = country;
    lat = 0;
    lon = 0;
  }
  
  public City(String id, String name, String country, double lat, double lon) {
    this.id = id;
    this.name = name;
    this.country = country;
    this.lat = lat;
    this.lon = lon;
  }
  
  public String getId() {
    return id;
  }
  
  public String getName() {
    return name;
  }
  
  public String getCountry() {
    return country;
  }
  
  public double getLat() {
    return lat;
  }
  
  public double getLon() {
    return lon;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public void setCountry(String country) {
    this.country = country;
  }
  
  public void setLat(double lat) {
    this.lat = lat;
  }
  
  public void setLon(double lon) {
    this.lon = lon;
  }
  
  @Override
  public String toString() {
    return "(" + id + ") " + name + ", " + country + " (" + lat + "," + lon + ")";
  }
}
