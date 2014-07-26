package com.gurkensalat.osm.repository;

import com.gurkensalat.osm.entity.DitibPlace;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "place", path = "place")
public interface DitibPlaceRepository extends PagingAndSortingRepository<DitibPlace, Long>
{
    List<DitibPlace> findByKey(@Param("key") String key);

    List<DitibPlace> findByName(@Param("name") String name);
}
