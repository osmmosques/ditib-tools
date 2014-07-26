package com.gurkensalat.osm.entity;

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

    @Column(name = "LAT")
    private double lat;

    @Column(name = "LON")
    private double lon;

    @Column(name = "KEY", length = 80)
    private String key;

    @Column(name = "NAME", length = 80)
    private String name;

    protected DitibPlace()
    {
    }

    public DitibPlace(String key)
    {
        this.key = key;
    }

    public String getKey()
    {
        return key;
    }

    public double getLat()
    {
        return lat;
    }

    public double getLon()
    {
        return lon;
    }

    public String getName()
    {
        return name;
    }

    public Integer getVersion()
    {
        return version;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }

    public void setLon(double lon)
    {
        this.lon = lon;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setVersion(Integer version)
    {
        this.version = version;
    }
}
