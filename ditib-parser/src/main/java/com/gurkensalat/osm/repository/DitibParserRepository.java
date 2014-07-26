package com.gurkensalat.osm.repository;

import com.gurkensalat.osm.entity.DitibParsedPlace;

import java.io.File;
import java.util.List;

public interface DitibParserRepository
{
    List<DitibParsedPlace> parse(File resourceFile);

    void prettify(File target, File resourceFile);
}
