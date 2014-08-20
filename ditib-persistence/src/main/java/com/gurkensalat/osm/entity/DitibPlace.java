package com.gurkensalat.osm.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "DITIB_PLACES")
public class DitibPlace extends OsmPlaceBase
{
    @Column(name = "D_CODE", length = 80)
    private String ditibCode;

    @Column(name = "GEOCODED")
    private boolean geocoded;

    protected DitibPlace()
    {
    }

    public DitibPlace(String key)
    {
        super();
        this.setKey(key);
        this.setAddress(new Address());
        this.setContact(new Contact());
    }

    public String getDitibCode()
    {
        return ditibCode;
    }

    public void setDitibCode(String ditibCode)
    {
        this.ditibCode = ditibCode;
    }

    public boolean isGeocoded()
    {
        return geocoded;
    }

    public void setGeocoded(boolean geocoded)
    {
        this.geocoded = geocoded;
    }

    public String toString()
    {
        return new ToStringBuilder(this).
                append("ditibCode", ditibCode).
                append("geocoded", geocoded).
                append("name", getName()).
                append("address", getAddress()).
                append("contact", getContact()).
                toString();
    }
}
