package com.gurkensalat.osm.repository;

import com.gurkensalat.osm.entity.DitibPlace;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "ditibPlace", path = "ditibPlace")
public interface DitibPlaceRepository extends PagingAndSortingRepository<DitibPlace, Long>
{
    List<DitibPlace> findByKey(@Param("key") String key);

    List<DitibPlace> findByName(@Param("name") String name);

    @Query("SELECT p FROM DitibPlace p WHERE :min_lon <= p.lon and p.lon < :max_lon and :min_lat <= p.lat and p.lat <= :max_lat")
    List<DitibPlace> findByBbox(@Param("min_lon") double minLongitude,
                                @Param("min_lat") double minLatitude,
                                @Param("max_lon") double maxLongitude,
                                @Param("max_lat") double maxLatitude);

    @Modifying
    @Transactional
    @Query("update DitibPlace set valid = false")
    void invalidateAll();

    @Modifying
    @Transactional
    @Query("update DitibPlace set valid = false where ADDR_COUNTRY = :addr_country")
    void invalidateByCountryCode(@Param("addr_country") String countryCode);

    @Modifying
    @Transactional
    @Query("delete from DitibPlace where valid = false")
    void deleteAllInvalid();

    @Query("SELECT p FROM DitibPlace p WHERE p.geocoded = false order by p.lastGeocodeAttempt, p.ditibCode")
    List<DitibPlace> geocodingCandidates(Pageable pageable);
}
