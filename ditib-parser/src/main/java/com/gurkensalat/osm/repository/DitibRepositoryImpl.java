package com.gurkensalat.osm.repository;

import com.gurkensalat.osm.entity.DitibPlace;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;
import static org.apache.commons.io.IOUtils.closeQuietly;

public class DitibRepositoryImpl implements DitibRepository
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DitibRepositoryImpl.class);

    private final String CSS_PATH = "td.body_text2";

    public List<DitibPlace> parse(File resourceFile)
    {
        List<DitibPlace> result = new ArrayList<DitibPlace>();

        try
        {
            Document document = Jsoup.parse(resourceFile, "UTF-8", "http://www.ditib.de/");

            Elements selection = document.select(CSS_PATH);
            LOGGER.debug("Found {} matching elements", selection.size());

            if (isNotEmpty(selection))
            {
                int hitNumber = 0;
                for (Element element : selection)
                {
                    LOGGER.debug("-------------------------------------------------------");
                    LOGGER.debug("Found: {}, {}", hitNumber++, element);

                    Elements rows = selection.select("tbody > tr");
                    if (isNotEmpty(rows))
                    {
                        DitibPlace place = new DitibPlace();
                        place = extractPlaceCode(safeGetElement(rows, 0), place);
                        place = extractPlaceName(safeGetElement(rows, 1), place);
                        place = extractPhoneNumber(safeGetElement(rows, 1), place);
                        place = extractFoo2(safeGetElement(rows, 2), place);
                        place = extractFoo3(safeGetElement(rows, 3), place);
                        place = extractFoo4(safeGetElement(rows, 4), place);
                        result.add(place);
                    }
                }
            }
        }
        catch (IOException ioe)
        {
            LOGGER.error("While parsing {}", resourceFile, ioe);
        }

        result = emptyIfNull(result);
        return result;
    }

    protected DitibPlace extractPlaceCode(Element block, DitibPlace place)
    {
        LOGGER.debug("extractPlaceCode()");
        LOGGER.debug("-------------------------------------------------------");
        LOGGER.debug("{}", block.toString());
        LOGGER.debug("-------------------------------------------------------");

        // fsck css, just run indexOf :-(
        String data = block.toString();
        int from = data.lastIndexOf("<strong>");
        int to = data.lastIndexOf("</strong>");

        if (from != -1 && to != -1)
        {
            data = data.substring(from + 8, to);
            data = data.replaceAll("&nbsp;", "");
            place.setDitibCode(data);
        }

        return place;
    }

    protected DitibPlace extractPlaceName(Element block, DitibPlace place)
    {
        LOGGER.debug("extractPlaceName()");
        LOGGER.debug("-------------------------------------------------------");
        LOGGER.debug("{}", block.toString());
        LOGGER.debug("-------------------------------------------------------");

        String data = block.toString();
        place.setName(safeGetText(block, 2));

        return place;
    }

    protected DitibPlace extractPhoneNumber(Element block, DitibPlace place)
    {
        LOGGER.debug("extractPhoneNumber()");
        LOGGER.debug("-------------------------------------------------------");
        LOGGER.debug("{}", block.toString());
        LOGGER.debug("-------------------------------------------------------");

        String data = block.toString();
        // WTF parser, should be 5?
        place.setPhone(safeGetText(block, 6));

        return place;
    }

    protected DitibPlace extractFoo2(Element block, DitibPlace place)
    {
        LOGGER.debug("-------------------------------------------------------");
        LOGGER.debug("extractFoo2()");
        LOGGER.debug("{}", block.toString());
        LOGGER.debug("-------------------------------------------------------");

        return place;
    }

    protected DitibPlace extractFoo3(Element block, DitibPlace place)
    {
        LOGGER.debug("extractFoo3()");
        LOGGER.debug("-------------------------------------------------------");
        LOGGER.debug("{}", block.toString());
        LOGGER.debug("-------------------------------------------------------");

        return place;
    }

    protected DitibPlace extractFoo4(Element block, DitibPlace place)
    {
        LOGGER.debug("extractFoo4()");
        LOGGER.debug("-------------------------------------------------------");
        LOGGER.debug("{}", block.toString());
        LOGGER.debug("-------------------------------------------------------");

        return place;
    }

    protected Element safeGetElement(Elements e, int index)
    {
        Element result = new Element(Tag.valueOf("body"), "no-where");

        if (e != null)
        {
            if (e.size() > index)
            {
                result = e.get(index);
            }
        }

        return result;
    }

    protected String safeGetText(Element element, int index)
    {
        String result = "";

        if (element != null)
        {
            if (element.getAllElements() != null)
            {
                if (element.getAllElements().size() >= index)
                {
                    Element work;
                    work = element.getAllElements().get(index);
                    result = work.text();
                }
            }
        }

        return result;
    }

    public void prettify(File target, File resourceFile)
    {
        try
        {
            Document document = Jsoup.parse(resourceFile, "UTF-8", "http://www.ditib.de/");

            String pretty = document.toString();

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


            Elements selection = document.select(CSS_PATH);
            LOGGER.debug("Found {} matching elements", selection.size());


            int hitNumber = 0;
            for (Element element : selection)
            {
                File output2 = new File(target, "parsed-" + hitNumber + "-" + resourceFile.getName());
                FileOutputStream fos2 = new FileOutputStream(output2);
                try
                {
                    pretty = element.toString();
                    fos2.write(pretty.getBytes());
                }
                finally
                {
                    closeQuietly(fos2);
                }
                hitNumber++;
            }
        }
        catch (IOException ioe)
        {
            LOGGER.error("While parsing {}", resourceFile, ioe);
        }
    }

    private List<DitibPlace> extractData(Element data)
    {
        List<DitibPlace> result = new ArrayList<DitibPlace>();

        LOGGER.debug("About to extract data from {}", data.toString());

        result = emptyIfNull(result);
        return result;
    }
}
