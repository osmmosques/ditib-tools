package com.gurkensalat.osm.repository;

import com.gurkensalat.osm.SimpleConfiguration;
import com.gurkensalat.osm.entity.DitibPlace;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = SimpleConfiguration.class)
public class DitibPlaceRepositoryTest
{
    @Autowired
    DitibPlaceRepository ditibPlaceRepository;

    private DitibPlace place;

    @Before
    public void setUp()
    {

    }

    @Test
    public void savePlace()
    {
        DitibPlace place = new DitibPlace("DTB-4711");

        assertTrue(place.isNew());

        DitibPlace savedPlace = ditibPlaceRepository.save(place);
        assertNotNull(savedPlace);
        assertFalse(place.isNew());
        assertEquals(savedPlace.getId(), new Long(1));
    }
}
