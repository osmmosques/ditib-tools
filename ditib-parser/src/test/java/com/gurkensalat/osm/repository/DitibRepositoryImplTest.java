package com.gurkensalat.osm.repository;

import com.gurkensalat.osm.entity.DitibParsedPlace;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

public class DitibRepositoryImplTest
{
    DitibParserRepositoryImpl testable;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        testable = new DitibParserRepositoryImpl();
    }

    @Test
    public void parseGermeringData() throws IOException
    {
        File file = new File("src/test/resources/ditib-germering.html");

        List<DitibParsedPlace> result = testable.parse(file);

        assertNotNull(result);
        assertEquals(1, result.size());

        DitibParsedPlace place = result.get(0);
        assertNotNull(place);
        assertEquals("GERMERING", place.getDitibCode());
        assertNotNull(place.getName());
        assertNotSame("", place.getName());
        assertNotNull(place.getPhone());
        assertNotSame("", place.getPhone());
    }

    @Test
    public void prettifyGermeringData() throws IOException
    {
        File file = new File("src/test/resources/ditib-germering.html");

        testable.prettify(new File("target"), file);
    }
}
