package com.gurkensalat.osm.repository;

import com.gurkensalat.osm.entity.DitibParsedPlace;
import com.gurkensalat.osm.entity.DitibParsedPlaceKey;
import org.apache.commons.lang3.StringUtils;
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
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.replaceOnce;
import static org.apache.commons.lang3.StringUtils.stripToEmpty;

@Repository
public class DitibParserRepositoryImpl implements DitibParserRepository
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DitibParserRepositoryImpl.class);

    private final String DITIB_DE_CSS_PATH = "div.col_5c.content > div.row > div.row";

    // //*[@id="table-3"]
    private final String DIYANET_NL_CSS_PATH = "table#table-3 > tbody > tr";

    public List<DitibParsedPlace> parse(File resourceFile)
    {
        return parseGermany(resourceFile);
    }

    public List<DitibParsedPlace> parseGermany(File resourceFile)
    {
        List<DitibParsedPlace> result = new ArrayList<DitibParsedPlace>();

        try
        {
            Document document = Jsoup.parse(resourceFile, "UTF-8", "http://www.ditib.de/");

            Elements selection = document.select(DITIB_DE_CSS_PATH);
            LOGGER.debug("Found {} matching elements", selection.size());

            if (isNotEmpty(selection))
            {
                int hitNumber = 0;
                for (Element element : selection)
                {
                    LOGGER.debug("-------------------------------------------------------");
                    LOGGER.debug("Found: {}, {}", hitNumber++, element);

                    Elements row = element.select("div.col_4");
                    if (isNotEmpty(row))
                    {
                        DitibParsedPlace place = new DitibParsedPlace();

                        Element nameNode = safeGetElement(row.select("h5"), 0);
                        place = extractPlaceCode(nameNode, place);

                        Elements secondColumn = element.select("div.col_2");
                        if (isNotEmpty(secondColumn))
                        {
                            String[] brElements = secondColumn.html().split("<br>");

                            place = extractPlaceName(safeGetElement(brElements, 1), place);

                            place = extractPlaceStreetNameAndNumber(safeGetElement(brElements, 2), place);

                            place = extractPostcodeAndCity(safeGetElement(brElements, 3), place);

                            // Actually, we have no use yet for the Google Map Link...
                            String gmapLink = safeGetElement(brElements, 4);
                        }

                        Elements thirdColumn = element.select("div.col_5");
                        if (isNotEmpty(thirdColumn))
                        {
                            String[] brElements = thirdColumn.html().split("<br>");

                            place = extractPhoneNumber(safeGetElement(brElements, 1), place);

                            place = extractFaxNumber(safeGetElement(brElements, 2), place);

                            // url?
                            // <a href="http://www.ditib-germering.de" target="_blank"><i class="fa fa-home"></i> Internet</a>
                            String url = safeGetElement(brElements, 3);

                            // email address
                            // <a href="mailto:konstzanzcamii@hotmail.com"> <i class="fa fa-envelope"> </i> E-Mail</a>
                            String email = safeGetElement(brElements, 3);
                        }

                        place.setCountry("DE");

                        place.setDitibCode(new DitibParsedPlaceKey(place).getKey());
                        place.setDitibCode(stripToEmpty(place.getDitibCode()));

                        place.setName(stripToEmpty(place.getName()));

                        place.setStreet(stripToEmpty(place.getStreet()));
                        place.setStreetNumber(stripToEmpty(place.getStreetNumber()));
                        place.setPostcode(stripToEmpty(place.getPostcode()));
                        place.setCity(stripToEmpty(place.getCity()));

                        place.setPhone(stripToEmpty(place.getPhone()));
                        place.setFax(stripToEmpty(place.getFax()));

                        // TODO - an "already-found"-Index of some sort...
                        if (!(isEmpty(place.getName())))
                        {
                            result.add(place);
                        }
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

    public List<DitibParsedPlace> parseNetherlands(File resourceFile)
    {
        List<DitibParsedPlace> result = new ArrayList<DitibParsedPlace>();

        try
        {
            Document document = Jsoup.parse(resourceFile, "UTF-8", "http://www.ditib.de/");

            Elements selection = document.select(DIYANET_NL_CSS_PATH);
            LOGGER.debug("Found {} matching elements", selection.size());

            if (isNotEmpty(selection))
            {
                int hitNumber = 0;
                for (Element row : selection)
                {
                    LOGGER.debug("-------------------------------------------------------");
                    LOGGER.debug("Found: {}, {}", hitNumber++, row);

                    Elements cells = row.select("td");
                    if (isNotEmpty(cells))
                    {
                        DitibParsedPlace place = new DitibParsedPlace();

                        // 0: <td style="height: 20px;" valign="middle"> <p class="basic-paragraph">35</p> </td>
                        Element e0 = safeGetElement(cells, 0);

                        // 1: <td>HDV MESCİD-İ KUBA</td>
                        // Element e1 = safeGetElement(cells, 1);
                        place = extractPlaceName(safeGetInnerText(safeGetElement(cells, 1)), place);
                        if ("Şube Adı / Naam".equals(place.getName()))
                        {
                            place.setName(null);
                        }

                        // 2: <td>GUIDO GEZELLESTR. 52</td>
                        // Element e2 = safeGetElement(cells, 2);
                        place = extractPlaceStreetNameAndNumber(safeGetInnerText(safeGetElement(cells, 2)), place);

                        // 3  <td>2524CM</td>
                        // Element e3 = safeGetElement(cells, 3);
                        place.setPostcode(safeGetInnerText(safeGetElement(cells, 3)));

                        // 4: <td>DEN HAAG</td>
                        // Element e4 = safeGetElement(cells, 4);
                        place.setCity(safeGetInnerText(safeGetElement(cells, 4)));

                        // 5: <td>070-3962637</td>
                        Element e5 = safeGetElement(cells, 5);

                        // 6: <td><a href="http://www.mescidiaksa.eu/"><img class="aligncenter" title="icon_internet" src="http://www.diyanet.nl/wp-content/uploads/2012/06/icon_internet.png" alt="" width="25" height="26"></a></td>
                        Element e6 = safeGetElement(cells, 6);

                        // 7: <td><a href="mailto:hdvdenhaagkuba@vakif.nl"><img class="aligncenter" title="icon_email" src="http://www.diyanet.nl/wp-content/uploads/2012/06/icon_email.png" alt="" width="25" height="26"></a></td>
                        Element e7 = safeGetElement(cells, 7);

                        place.setCountry("NL");

                        place.setDitibCode(new DitibParsedPlaceKey(place).getKey());
                        place.setDitibCode(stripToEmpty(place.getDitibCode()));

                        place.setName(stripToEmpty(place.getName()));

                        place.setStreet(stripToEmpty(place.getStreet()));
                        place.setStreetNumber(stripToEmpty(place.getStreetNumber()));
                        place.setPostcode(stripToEmpty(place.getPostcode()));
                        place.setCity(stripToEmpty(place.getCity()));

                        place.setPhone(stripToEmpty(place.getPhone()));
                        place.setFax(stripToEmpty(place.getFax()));

                        // TODO - an "already-found"-Index of some sort...
                        if (!(isEmpty(place.getName())))
                        {
                            result.add(place);
                        }
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

        String data = stripToEmpty(block.ownText());
        data = data.replaceAll("&nbsp;", "");
        place.setDitibCode(data);

        return place;
    }

    protected DitibParsedPlace extractPlaceName(String block, DitibParsedPlace place)
    {
        LOGGER.debug("extractPlaceName()");
        LOGGER.debug("-------------------------------------------------------");
        LOGGER.debug("{}", block.toString());
        LOGGER.debug("-------------------------------------------------------");

        String data = stripToEmpty(block);
        place.setName(data);

        return place;
    }

    protected DitibParsedPlace extractPhoneNumber(String block, DitibParsedPlace place)
    {
        LOGGER.debug("extractPhoneNumber()");
        LOGGER.debug("-------------------------------------------------------");
        LOGGER.debug("{}", block.toString());
        LOGGER.debug("-------------------------------------------------------");

        int i = block.indexOf("</i>");
        if (i > -1)
        {
            String phone = block.substring(i + 4);
            phone = stripToEmpty(phone);
            if (phone.startsWith("0"))
            {
                phone = replaceOnce(phone, "0", "+49 / ");
            }

            phone = StringUtils.replace(phone, " / ", "/");

            place.setPhone(phone);
        }

        return place;
    }

    protected DitibParsedPlace extractPlaceStreetNameAndNumber(String block, DitibParsedPlace place)
    {
        LOGGER.debug("extractPlaceStreetNameAndNumber()");
        LOGGER.debug("-------------------------------------------------------");
        LOGGER.debug("{}", block.toString());
        LOGGER.debug("-------------------------------------------------------");

        String streetAndNumber = stripToEmpty(block);

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

        place.setStreetNumber(place.getStreetNumber().replaceAll(",", ""));

        return place;
    }

    protected DitibParsedPlace extractFaxNumber(String block, DitibParsedPlace place)
    {
        LOGGER.debug("extractFaxNumber()");
        LOGGER.debug("-------------------------------------------------------");
        LOGGER.debug("{}", block.toString());
        LOGGER.debug("-------------------------------------------------------");

        int i = block.indexOf("</i>");
        if (i > -1)
        {
            String fax = block.substring(i + 4);
            fax = stripToEmpty(fax);
            if (fax.startsWith("0"))
            {
                fax = replaceOnce(fax, "0", "+49 / ");
            }

            fax = StringUtils.replace(fax, " / ", "/");

            place.setFax(fax);
        }

        return place;
    }

    protected DitibParsedPlace extractPostcodeAndCity(String block, DitibParsedPlace place)
    {
        LOGGER.debug("extractPostcodeAndCity()");
        LOGGER.debug("-------------------------------------------------------");
        LOGGER.debug("{}", block.toString());
        LOGGER.debug("-------------------------------------------------------");

        place.setPostcode("");
        place.setCity(stripToEmpty(block));

        int spaceIndex = place.getCity().indexOf(" ");
        if (spaceIndex > -1)
        {
            place.setPostcode(place.getCity().substring(0, spaceIndex));
            place.setCity(place.getCity().substring(spaceIndex + 1));
        }

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

    protected String safeGetElement(String[] array, int index)
    {
        String result = "";

        if (array.length > index)
        {
            result = array[index];
        }

        return result;
    }

    protected String safeGetInnerText(Element e)
    {
        String result = "";

        if (e != null)
        {
            result = stripToEmpty(e.text());
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

            Elements selection = document.select(DITIB_DE_CSS_PATH);
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
