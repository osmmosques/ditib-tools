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
        assertEquals("ditibCode mismatch", "GERMERING", place.getDitibCode());
        assertEquals("Name mismatch", "Türkisch Islamische Kultur Verein e.V.", place.getName());
        assertEquals("Phone mismatch", "+49 / 89 / 37914346", place.getPhone());
        assertEquals("Street name mismatch", "Münchener Str.", place.getStreet());
        assertEquals("Street number mismatch", "13a", place.getStreetNumber());
        assertEquals("Fax mismatch", "+49 / 89 / 37914346", place.getFax());
        assertEquals("PostCode mismatch", "82110", place.getPostcode());
        assertEquals("City mismatch", "GERMERING", place.getCity());
    }

    @Test
    public void parseThreePlacesData() throws IOException
    {
        File file = new File("src/test/resources/ditib-three-places.html");

        List<DitibParsedPlace> result = testable.parse(file);

        assertNotNull(result);
        assertEquals(3, result.size());

        DitibParsedPlace place = result.get(0);
        assertEquals("ditibCode 1 mismatch", "KONZ", place.getDitibCode());
        assertEquals("Name 1 mismatch", "DITIB Türkisch Islamische Kultur Verein e.V.", place.getName());
        assertEquals("Phone 1 mismatch", "+49 / 6501 / 2914", place.getPhone());
        assertEquals("Street 1 name mismatch", "Dammstr.", place.getStreet());
        assertEquals("Street 1 number mismatch", "2", place.getStreetNumber());
        assertEquals("Fax 1 mismatch", "+49 / 6501 / 2293", place.getFax());
        assertEquals("PostCode 1 mismatch", "54329", place.getPostcode());
        assertEquals("City 1 mismatch", "KONZ", place.getCity());

        place = result.get(1);
        assertEquals("ditibCode 2 mismatch", "KORNWESTHEIM", place.getDitibCode());
        assertEquals("Name 2 mismatch", "DITIB Türkisch Islamische Kultur Verein e.V.", place.getName());
        assertEquals("Phone 2 mismatch", "+49 / 711 / 8821471", place.getPhone());
        assertEquals("Street 2 name mismatch", "Sigelstr.", place.getStreet());
        assertEquals("Street 2 number mismatch", "44", place.getStreetNumber());
        assertEquals("Fax 2 mismatch", "+49 / 711 / 8821472", place.getFax());
        assertEquals("PostCode 2 mismatch", "70806", place.getPostcode());
        assertEquals("City 2 mismatch", "KORNWESTHEIM", place.getCity());

        place = result.get(2);
        assertEquals("ditibCode 3 mismatch", "M&Uuml;NCHEN-Untersendling", place.getDitibCode());
        assertEquals("Name 3 mismatch", "DITIM-Türkisch Islamischen Kultur Zentrum e.V.", place.getName());
        assertEquals("Phone 3 mismatch", "+49 / 89 / 720 59 342", place.getPhone());
        assertEquals("Street 3 name mismatch", "Schanzenbachstr.", place.getStreet());
        assertEquals("Street 3 number mismatch", "1", place.getStreetNumber());
        assertEquals("Fax 3 mismatch", "+49 / 89 / 720 69 838", place.getFax());
        assertEquals("PostCode 2 mismatch", "81371", place.getPostcode());
        assertEquals("City 3 mismatch", "MÜNCHEN-Untersendling", place.getCity());
    }

    @Test
    public void prettifyGermeringData() throws IOException
    {
        File file = new File("src/test/resources/ditib-germering.html");

        testable.prettify(new File("target"), file);
    }

    @Test
    public void prettifyThreePlacesData() throws IOException
    {
        File file = new File("src/test/resources/ditib-three-places.html");

        testable.prettify(new File("target"), file);
    }
}
