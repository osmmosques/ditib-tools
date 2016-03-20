package com.gurkensalat.osm.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "DITIB_PLACES")
public class DitibPlace extends OsmPlaceBase
{
    @Column(name = "D_CODE", length = 80)
    private String ditibCode;

    @Column(name = "GEOCODED")
    private boolean geocoded;

    @Column(name = "LAST_GEOCODE_ATTEMT")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastGeocodeAttemt;

    @Transient
    private String humanReadableLastGeocodeAttemt;


    protected DitibPlace()
    {
    }

    public DitibPlace(String key)
    {
        super();
        this.setKey(key);
        this.setAddress(new Address());
        this.setContact(new Contact());

        if (getCreationTime() == null)
        {
            setCreationTime(DateTime.now());
        }

        if (getModificationTime() == null)
        {
            setModificationTime(DateTime.now());
        }
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

    public DateTime getLastGeocodeAttemt()
    {
        return lastGeocodeAttemt;
    }

    public void setLastGeocodeAttemt(DateTime lastGeocodeAttemt)
    {
        this.lastGeocodeAttemt = lastGeocodeAttemt;
    }

    public String getHumanReadableLastGeocodeAttemt()
    {
        return humanReadableLastGeocodeAttemt;
    }

    public void setHumanReadableLastGeocodeAttemt(String humanReadableLastGeocodeAttemt)
    {
        this.humanReadableLastGeocodeAttemt = humanReadableLastGeocodeAttemt;
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
