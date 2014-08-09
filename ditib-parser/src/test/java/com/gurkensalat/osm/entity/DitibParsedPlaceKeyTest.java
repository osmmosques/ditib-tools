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
    public void testKeyGermering()
    {
        DitibParsedPlace testable = new DitibParsedPlace();
        testable.setPostcode("82110");
        testable.setStreet("Münchener Str.");
        testable.setStreetNumber("13a");
        DitibParsedPlaceKey key = new DitibParsedPlaceKey(testable);
        assertEquals("82110-77-7", key.getKey());
    }

    @Test
    public void testKeyKonz()
    {
        DitibParsedPlace testable = new DitibParsedPlace();
        testable.setPostcode("54329");
        testable.setStreet("Dammstr.");
        testable.setStreetNumber("2");
        DitibParsedPlaceKey key = new DitibParsedPlaceKey(testable);
        assertEquals("54329-98-0", key.getKey());
    }

    @Test
    public void testKeyKornwestheim()
    {
        DitibParsedPlace testable = new DitibParsedPlace();
        testable.setPostcode("70806");
        testable.setStreet("Sigelstr.");
        testable.setStreetNumber("44");
        DitibParsedPlaceKey key = new DitibParsedPlaceKey(testable);
        assertEquals("70806-51-4", key.getKey());
    }

    @Test
    public void testKeyMünchenUntersendling()
    {
        DitibParsedPlace testable = new DitibParsedPlace();
        testable.setPostcode("81371");
        testable.setStreet("Schanzenbachstr.");
        testable.setStreetNumber("1");
        DitibParsedPlaceKey key = new DitibParsedPlaceKey(testable);
        assertEquals("81371-91-9", key.getKey());
    }
}
