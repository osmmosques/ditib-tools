package com.gurkensalat.osm.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

public class DitibParsedPlaceKey
{
    String key;

    private DitibParsedPlaceKey()
    {
    }

    public DitibParsedPlaceKey(DitibParsedPlace ditibParsedPlace)
    {
        this.key = "";

        // TODO country should come from the value object
        String country = trimToEmpty(ditibParsedPlace.getCountry());
        if (isEmpty(country))
        {
            country = "DE";
        }

        country = country.toLowerCase(Locale.US);

        String postCode = trimToEmpty(ditibParsedPlace.getPostcode());
        if (isEmpty(postCode))
        {
            postCode = "00000";
        }

        // Postcodes in Netherlands have spaces and alphanumerics in them
        postCode = postCode.replaceAll(" ", "");
        postCode = postCode.toLowerCase(Locale.US);

        String street = trimToEmpty(ditibParsedPlace.getStreet()).toLowerCase(Locale.US);
        String streetCode = Integer.toString(street.hashCode() + 1000000000);
        streetCode = streetCode.substring(streetCode.length() - 2);

        String houseNumber = trimToEmpty(ditibParsedPlace.getStreetNumber()).toLowerCase(Locale.US);
        String houseNumberCode = Integer.toString(houseNumber.hashCode() + 1000000000);
        houseNumberCode = houseNumberCode.substring(houseNumberCode.length() - 1);

        this.key = country + "-" + postCode + "-" + streetCode + "-" + houseNumberCode;
    }

    public String getKey()
    {
        return key;
    }
}
