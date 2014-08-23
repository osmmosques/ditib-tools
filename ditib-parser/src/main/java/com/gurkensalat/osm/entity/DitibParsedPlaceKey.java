package com.gurkensalat.osm.entity;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class DitibParsedPlaceKey
{
    String key;

    private DitibParsedPlaceKey()
    {
    }

    public DitibParsedPlaceKey(DitibParsedPlace ditibParsedPlace)
    {
        this.key = "";

        String postCode = ditibParsedPlace.getPostcode();
        if (isEmpty(postCode))
        {
            postCode = "00000";
        }

        String streetCode = Integer.toString(ditibParsedPlace.getStreet().hashCode() + 1000000000);
        streetCode = streetCode.substring(streetCode.length() - 2);

        String houseNumberCode = Integer.toString(ditibParsedPlace.getStreetNumber().hashCode() + 1000000000);
        houseNumberCode = houseNumberCode.substring(houseNumberCode.length() - 1);

        this.key = "de-" + postCode + "-" + streetCode + "-" + houseNumberCode;
    }

    public String getKey()
    {
        return key;
    }
}
