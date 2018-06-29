package com.pyra.weatherforecast.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class CityTest {

  @Test
  public void testDefaultCtor() {
    City test = new City();
    assertEquals(test.getId(), "");
    assertEquals(test.getName(), "");
    assertEquals(test.getCountry(), "");
    assertEquals(test.getLat(), 0, 2);
    assertEquals(test.getLon(), 0, 2);
  }
  
  @Test
  public void testCtor1() {
    City test = new City("562835"); 
    assertEquals(test.getId(),"562835");
    assertEquals(test.getLon(), 0, 2);
  }
  
  @Test
  public void testCtor2() {
    City test = new City("562835","Torna");
    assertEquals(test.getId(), "562835");
    assertEquals(test.getName(), "Torna");
    assertEquals(test.getLon(), 0, 2);
  }
  
  @Test
  public void testCtor3() {
    City test = new City("562835","Torna","Alrest");
    assertEquals(test.getId(), "562835");
    assertEquals(test.getName(), "Torna");
    assertEquals(test.getCountry(), "Alrest");
    assertEquals(test.getLon(), 0, 2);
  }

  @Test
  public void testCtor4() {
    City test = new City("562835","Torna","Alrest",7.8432,1.4326);
    assertEquals(test.getId(), "562835");
    assertEquals(test.getName(), "Torna");
    assertEquals(test.getCountry(), "Alrest");
    assertEquals(test.getLat(), 7.8432, 2);
    assertEquals(test.getLon(), 1.4326, 2);
  }
  
  @Test
  public void testIdSetter() {
    City test = new City();
    test.setId("290718");
    assertEquals(test.getId(),"290718");
  }
  
  @Test
  public void testNameSetter() {
    City test = new City();
    test.setName("Nimbasa");
    assertEquals(test.getName(),"Nimbasa");
  }
  
  @Test
  public void testCountrySetter() {
    City test = new City();
    test.setCountry("Unova");
    assertEquals(test.getCountry(),"Unova");
  }
  
  @Test
  public void testCoordinateSetter() {
    City test = new City();
    test.setLat(6.2354);
    test.setLon(9.4781);
    assertEquals(test.getLat(), 6.2354, 2);
    assertEquals(test.getLon(), 9.4781, 2);
  }
  
  @Test
  public void testClone() {
    City test = new City("587443","Hateno","East Hyrule",7.2458,1.9432);
    City cloneOfTest = test.clone();
    assertEquals(test.getId(), cloneOfTest.getId());
    assertEquals(test.getName(), cloneOfTest.getName());
    assertEquals(test.getCountry(), cloneOfTest.getCountry());
    assertEquals(test.getLat(), cloneOfTest.getLat(), 2);
    assertEquals(test.getLon(), cloneOfTest.getLon(), 2);
    assertFalse(test == cloneOfTest);
  }
  
}
