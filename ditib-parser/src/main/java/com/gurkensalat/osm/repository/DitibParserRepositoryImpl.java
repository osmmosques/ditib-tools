package com.gurkensalat.osm.repository;

import com.gurkensalat.osm.entity.DitibParsedPlace;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;
import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.lang3.StringUtils.indexOfAny;
import static org.apache.commons.lang3.StringUtils.stripToEmpty;

@Repository
public class DitibParserRepositoryImpl implements DitibParserRepository
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DitibParserRepositoryImpl.class);

    private final String CSS_PATH = "td.body_text2";

    public List<DitibParsedPlace> parse(File resourceFile)
    {
        List<DitibParsedPlace> result = new ArrayList<DitibParsedPlace>();

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

                    Elements rows = element.select("tbody > tr");
                    if (isNotEmpty(rows))
                    {
                        DitibParsedPlace place = new DitibParsedPlace();

                        Element row0 = safeGetElement(rows, 0);
                        place = extractPlaceCode(row0, place);

                        Element row1 = safeGetElement(rows, 1);
                        place = extractPlaceName(row1, place);
                        place = extractPhoneNumber(row1, place);

                        Element row2 = safeGetElement(rows, 2);
                        place = extractPlaceStreetNameAndNumber(row2, place);
                        place = extractFaxNumber(row2, place);

                        Element row3 = safeGetElement(rows, 3);
                        place = extractPostcodeAndCity(row3, place);

                        Element row4 = safeGetElement(rows, 4);
                        place = extractFoo4(row4, place);

                        place.setDitibCode(stripToEmpty(place.getDitibCode()));
                        place.setName(stripToEmpty(place.getName()));

                        place.setStreet(stripToEmpty(place.getStreet()));
                        place.setStreetNumber(stripToEmpty(place.getStreetNumber()));
                        place.setPostcode(stripToEmpty(place.getPostcode()));
                        place.setCity(stripToEmpty(place.getCity()));

                        place.setPhone(stripToEmpty(place.getPhone()));
                        place.setFax(stripToEmpty(place.getFax()));

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

    protected DitibParsedPlace extractPlaceCode(Element block, DitibParsedPlace place)
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

    protected DitibParsedPlace extractPlaceName(Element block, DitibParsedPlace place)
    {
        LOGGER.debug("extractPlaceName()");
        LOGGER.debug("-------------------------------------------------------");
        LOGGER.debug("{}", block.toString());
        LOGGER.debug("-------------------------------------------------------");

        String data = block.toString();
        place.setName(safeGetText(block, 2));

        return place;
    }

    protected DitibParsedPlace extractPhoneNumber(Element block, DitibParsedPlace place)
    {
        LOGGER.debug("extractPhoneNumber()");
        LOGGER.debug("-------------------------------------------------------");
        LOGGER.debug("{}", block.toString());
        LOGGER.debug("-------------------------------------------------------");

        int elementNumber = 0;
        for (Element element : block.getAllElements())
        {
            String data = element.toString();
            LOGGER.debug("  {} - '{}'", elementNumber++, data);
        }

        place.setPhone(safeGetText(block, 6));

        return place;
    }

    protected DitibParsedPlace extractPlaceStreetNameAndNumber(Element block, DitibParsedPlace place)
    {
        LOGGER.debug("extractPlaceStreetNameAndNumber()");
        LOGGER.debug("-------------------------------------------------------");
        LOGGER.debug("{}", block.toString());
        LOGGER.debug("-------------------------------------------------------");

        String streetAndNumber = safeGetText(block, 5);

        streetAndNumber = streetAndNumber.replaceAll("\\.", ". ");
        int i = indexOfAny(streetAndNumber, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

        if (i > -1)
        {
            place.setStreet(streetAndNumber.substring(0, i - 1));
            place.setStreetNumber(streetAndNumber.substring(i));
        }
        else
        {
            place.setStreet(streetAndNumber);
            place.setStreetNumber("");
        }

        return place;
    }

    protected DitibParsedPlace extractFaxNumber(Element block, DitibParsedPlace place)
    {
        LOGGER.debug("extractFaxNumber()");
        LOGGER.debug("-------------------------------------------------------");
        LOGGER.debug("{}", block.toString());
        LOGGER.debug("-------------------------------------------------------");

        place.setFax(safeGetText(block, 9));

        return place;
    }

    protected DitibParsedPlace extractPostcodeAndCity(Element block, DitibParsedPlace place)
    {
        LOGGER.debug("extractPostcodeAndCity()");
        LOGGER.debug("-------------------------------------------------------");
        LOGGER.debug("{}", block.toString());
        LOGGER.debug("-------------------------------------------------------");

        place.setPostcode("");
        place.setCity(safeGetText(block, 2));

        int spaceIndex = place.getCity().indexOf(" ");
        if (spaceIndex > -1)
        {
            place.setPostcode(place.getCity().substring(0, spaceIndex));
            place.setCity(place.getCity().substring(spaceIndex + 1));
        }

        return place;
    }

    protected DitibParsedPlace extractFoo4(Element block, DitibParsedPlace place)
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

            String nameBase = resourceFile.getName();
            String nameSuffix = ".html";
            if (nameBase.lastIndexOf(".html") > -1)
            {
                nameBase = nameBase.substring(0, nameBase.lastIndexOf(".html"));
            }

            int hitNumber = 10000;
            for (Element element : selection)
            {
                output = new File(target, "parsed-" + nameBase + "-" + Integer.toString(hitNumber).substring(1) + nameSuffix);
                fos = new FileOutputStream(output);
                try
                {
                    pretty = element.toString();
                    fos.write(pretty.getBytes());
                }
                finally
                {
                    closeQuietly(fos);
                }
                hitNumber++;
            }
        }
        catch (IOException ioe)
        {
            LOGGER.error("While parsing {}", resourceFile, ioe);
        }
    }

    private List<DitibParsedPlace> extractData(Element data)
    {
        List<DitibParsedPlace> result = new ArrayList<DitibParsedPlace>();

        LOGGER.debug("About to extract data from {}", data.toString());

        result = emptyIfNull(result);
        return result;
    }
}
