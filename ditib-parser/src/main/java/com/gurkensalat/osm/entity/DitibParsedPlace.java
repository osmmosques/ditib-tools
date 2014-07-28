package com.gurkensalat.osm.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DitibParsedPlace
{
    private String ditibCode;

    private String name;

    private String street;

    private String streetNumber;

    private String postcode;

    private String city;

    private String phone;

    private String fax;

    private String url;

    private String email;

    public String getDitibCode()
    {
        return ditibCode;
    }

    public void setDitibCode(String ditibCode)
    {
        this.ditibCode = ditibCode;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getStreetNumber()
    {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber)
    {
        this.streetNumber = streetNumber;
    }

    public String getPostcode()
    {
        return postcode;
    }

    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getFax()
    {
        return fax;
    }

    public void setFax(String fax)
    {
        this.fax = fax;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String toString()
    {
        return new ToStringBuilder(this).
                append(ditibCode).append(name).
                append(street).append(streetNumber).
                append(postcode).append(city).
                append(phone).append(fax).
                toString();
    }
}
