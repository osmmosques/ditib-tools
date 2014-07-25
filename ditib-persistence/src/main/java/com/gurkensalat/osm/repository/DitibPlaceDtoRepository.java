package com.gurkensalat.osm.repository;

import com.gurkensalat.osm.entity.DitibPlaceDto;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "place", path = "place")
public interface DitibPlaceDtoRepository extends PagingAndSortingRepository<DitibPlaceDto, Long>
{
    List<DitibPlaceDto> findByName(@Param("name") String name);
}
