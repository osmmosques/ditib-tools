package com.gurkensalat.osm.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;

public class DitibRepositoryImplTest
{
    DitibRepositoryImpl testable;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        testable = new DitibRepositoryImpl();
    }

    @Test
    public void prettifyGermeringData() throws IOException
    {
        File file = new File("src/test/resources/ditib-germering.html");

        testable.prettify(new File("target"), file);
    }
}
