package com.gurkensalat.osm.repository;

import com.gurkensalat.osm.SimpleConfiguration;
import com.gurkensalat.osm.entity.DitibPlace;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@Rollback
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
        // assertEquals(savedPlace.getId(), new Long(1));
        // id comparision does not work anymore with non-transactional tests...
    }

    @Test
    public void testQueryByBbox()
    {
        List<DitibPlace> result = ditibPlaceRepository.findByBbox(1, 2, 3, 4);

        assertNotNull(result);
    }

    @Test
    // @Transactional // Must not be transactional
    @Transactional(Transactional.TxType.NEVER)
    public void testIndvalidateByCountryCode()
    {
        DitibPlace placeDE = new DitibPlace("DE");
        placeDE.setValid(true);
        placeDE.getAddress().setCountry("DE");
        placeDE = ditibPlaceRepository.save(placeDE);

        DitibPlace placeTR = new DitibPlace("TR");
        placeTR.setValid(true);
        placeTR.getAddress().setCountry("TR");
        placeTR = ditibPlaceRepository.save(placeTR);

        // Reload and check validity
        placeDE = ditibPlaceRepository.findOne(placeDE.getId());
        assertTrue("Place should have been valid", placeDE.isValid());

        placeTR = ditibPlaceRepository.findOne(placeTR.getId());
        assertTrue("Place should have been valid", placeTR.isValid());

        // Invalidate part of the entries
        ditibPlaceRepository.invalidateByCountryCode("TR");

        // Reload and check validity, again...
        placeDE = ditibPlaceRepository.findOne(placeDE.getId());
        assertTrue("Place should have been valid", placeDE.isValid());

        placeTR = ditibPlaceRepository.findOne(placeTR.getId());
        assertFalse("Place should NOT have been valid", placeTR.isValid());
    }
}
