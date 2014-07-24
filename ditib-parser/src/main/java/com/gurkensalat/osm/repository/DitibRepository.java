package com.gurkensalat.osm.repository;

import com.gurkensalat.osm.entity.DitibPlace;

import java.io.File;
import java.util.List;

public interface DitibRepository
{
    List<DitibPlace> prettify(File target, File resourceFile);
}
