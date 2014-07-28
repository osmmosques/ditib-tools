package com.gurkensalat.osm.entity;

public class DitibParsedPlaceKey
{
    String key;

    private DitibParsedPlaceKey()
    {
    }

    public DitibParsedPlaceKey(DitibParsedPlace ditibParsedPlace)
    {
        this.key = "";

        this.key = Integer.toString(ditibParsedPlace.getStreet().hashCode() + 1000000000);

        this.key = this.key.substring(this.key.length() - 2);

        this.key = this.key + "-";
        this.key = this.key + ditibParsedPlace.getPostcode();
    }

    public String getKey()
    {
        return key;
    }
}
