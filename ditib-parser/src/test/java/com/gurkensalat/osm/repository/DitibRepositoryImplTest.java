package com.gurkensalat.osm.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

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
    public void parseGermeringData()
    {
    }
}
