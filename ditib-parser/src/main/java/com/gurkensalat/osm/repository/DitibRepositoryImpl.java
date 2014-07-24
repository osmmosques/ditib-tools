package com.gurkensalat.osm.repository;

import com.gurkensalat.osm.entity.DitibPlace;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;
import static org.apache.commons.io.IOUtils.closeQuietly;

public class DitibRepositoryImpl implements DitibRepository
{
    private final static Logger LOGGER = LoggerFactory.getLogger(DitibRepositoryImpl.class);

    public List<DitibPlace> prettify(File target, File resourceFile)
    {
        List<DitibPlace> result = null;

        try
        {
            Document doc = Jsoup.parse(resourceFile, "UTF-8", "http://www.ditib.de/");

            String pretty = doc.toString();
            int bp = 42;

            File output = new File(target, "parsed-" + resourceFile.getName());
            FileOutputStream fos = new FileOutputStream(output);
            try
            {
                fos.write(pretty.getBytes());
            }
            finally
            {
                closeQuietly(fos);
            }
        }
        catch (IOException ioe)
        {
            LOGGER.error("While parsing {}", resourceFile, ioe);
        }

        result = emptyIfNull(result);

        return result;
    }
}
