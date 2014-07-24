package com.gurkensalat.osm.repository;

import com.gurkensalat.osm.entity.DitibPlace;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DitibRepositoryImpl implements DitibRepository
{
    public List<DitibPlace> parse(File resourceFile)
    {
        return new ArrayList<DitibPlace>();
    }
}
