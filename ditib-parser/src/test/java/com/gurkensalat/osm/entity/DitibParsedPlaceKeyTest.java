package com.gurkensalat.osm.entity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

public class DitibParsedPlaceKeyTest
{
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testKeyDEGermering()
    {
        DitibParsedPlace testable = new DitibParsedPlace();
        testable.setPostcode("82110");
        testable.setStreet("Münchener Str.");
        testable.setStreetNumber("13a");
        DitibParsedPlaceKey key = new DitibParsedPlaceKey(testable);
        assertEquals("de-82110-19-7", key.getKey());
    }

    @Test
    public void testKeyDEKonz()
    {
        DitibParsedPlace testable = new DitibParsedPlace();
        testable.setPostcode("54329");
        testable.setStreet("Dammstr.");
        testable.setStreetNumber("2");
        DitibParsedPlaceKey key = new DitibParsedPlaceKey(testable);
        assertEquals("de-54329-26-0", key.getKey());
    }

    @Test
    public void testKeyDEKornwestheim()
    {
        DitibParsedPlace testable = new DitibParsedPlace();
        testable.setPostcode("70806");
        testable.setStreet("Sigelstr.");
        testable.setStreetNumber("44");
        DitibParsedPlaceKey key = new DitibParsedPlaceKey(testable);
        assertEquals("de-70806-77-4", key.getKey());
    }

    @Test
    public void testKeyDEMünchenUntersendling()
    {
        DitibParsedPlace testable = new DitibParsedPlace();
        testable.setPostcode("81371");
        testable.setStreet("Schanzenbachstr.");
        testable.setStreetNumber("1");
        DitibParsedPlaceKey key = new DitibParsedPlaceKey(testable);
        assertEquals("de-81371-11-9", key.getKey());
    }

    @Test
    public void testNLZoetermeer()
    {
        // HDV ORANJE KULTUR MERKEZI	SCHOOLSTR.50	2712VC	ZOETERMEER
        DitibParsedPlace testable = new DitibParsedPlace();
        testable.setCountry("NL");
        testable.setPostcode("2712VC");
        testable.setStreet("SCHOOLSTR.");
        testable.setStreetNumber("50");
        DitibParsedPlaceKey key = new DitibParsedPlaceKey(testable);
        assertEquals("nl-2712vc-45-1", key.getKey());
    }

    @Test
    public void testNLZaandam()
    {
        // HDV SULTAN AHMET	POELENBURG 156	1504 NH	ZAANDAM	075-6354775
        DitibParsedPlace testable = new DitibParsedPlace();
        testable.setCountry("NL");
        testable.setPostcode("1504 NH");
        testable.setStreet("POELENBURG");
        testable.setStreetNumber("156");
        DitibParsedPlaceKey key = new DitibParsedPlaceKey(testable);
        assertEquals("nl-1504nh-31-6", key.getKey());
    }
}
