package com.gurkensalat.osm.entity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        testable.setStreet("Münchener Str. 13a");
        DitibParsedPlaceKey key = new DitibParsedPlaceKey(testable);
        assertEquals("58-82110", key.getKey());
    }

    @Test
    public void testKeyKonz()
    {
        DitibParsedPlace testable = new DitibParsedPlace();
        testable.setPostcode("54329");
        testable.setStreet("Dammstr.2");
        DitibParsedPlaceKey key = new DitibParsedPlaceKey(testable);
        assertEquals("36-54329", key.getKey());
    }

    @Test
    public void testKeyKornwestheim()
    {
        DitibParsedPlace testable = new DitibParsedPlace();
        testable.setPostcode("70806");
        testable.setStreet("Sigelstr.44");
        DitibParsedPlaceKey key = new DitibParsedPlaceKey(testable);
        assertEquals("41-70806", key.getKey());
    }

    @Test
    public void testKeyMünchenUntersendling()
    {
        DitibParsedPlace testable = new DitibParsedPlace();
        testable.setPostcode("81371");
        testable.setStreet("Schanzenbachstr. 1");
        DitibParsedPlaceKey key = new DitibParsedPlaceKey(testable);
        assertEquals("48-81371", key.getKey());
    }
}
