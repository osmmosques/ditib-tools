package com.gurkensalat.osm.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "DITIB_PLACES")
public class DitibPlace extends AbstractPersistable<Long>
{
    @Version
    @Column(name = "VERSION")
    private Integer version;

    @Column(name = "SCORE")
    private double score;

    @Column(name = "LAT")
    private double lat;

    @Column(name = "LON")
    private double lon;

    @Column(name = "D_KEY", length = 80)
    private String key;

    @Column(name = "D_CODE", length = 80)
    private String ditibCode;

    @Column(name = "NAME", length = 80)
    private String name;

    @Column(name = "STREET", length = 80)
    private String street;

    @Column(name = "STREET_NUMBER", length = 10)
    private String streetNumber;

    @Column(name = "POSTCODE", length = 10)
    private String postcode;

    @Column(name = "CITY", length = 80)
    private String city;

    @Column(name = "PHONE", length = 80)
    private String phone;

    @Column(name = "FAX", length = 80)
    private String fax;

    protected DitibPlace()
    {
    }

    public DitibPlace(String key)
    {
        this.key = key;
    }

    public double getScore()
    {
        return score;
    }

    public void setScore(double score)
    {
        this.score = score;
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

    public String getDitibCode()
    {
        return ditibCode;
    }

    public void setDitibCode(String ditibCode)
    {
        this.ditibCode = ditibCode;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }

    public double getLon()
    {
        return lon;
    }

    public void setLon(double lon)
    {
        this.lon = lon;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getVersion()
    {
        return version;
    }

    public void setVersion(Integer version)
    {
        this.version = version;
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
