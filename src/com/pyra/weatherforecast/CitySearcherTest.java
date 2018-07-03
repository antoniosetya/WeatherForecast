package com.pyra.weatherforecast;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CitySearcherTest {

  @Test
  public void testSearch() {
    CitySearcher test = CitySearcher.getSearcher();
    test.setSearchQuery("Jakart");
    test.search();
    assertTrue(test.getResult().size() > 0);
    test.setSearchQuery("odjaiodhwiabfiabwdi");
    test.search();
    assertTrue(test.getResult().size() == 0);
  }

}
