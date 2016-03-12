package com.gurkensalat.osm.repository;

import com.gurkensalat.osm.entity.DitibParsedPlace;

import java.io.File;
import java.util.List;

public interface DitibParserRepository
{
    @Deprecated
    List<DitibParsedPlace> parse(File resourceFile);

    List<DitibParsedPlace> parseGermany(File resourceFile);

    List<DitibParsedPlace> parseNetherlands(File resourceFile);

    void prettify(File target, File resourceFile);
}
