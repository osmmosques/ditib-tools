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

    @Column(name = "PHONE", length = 80)
    private String phone;

    @Column(name = "FAX", length = 80)
    private String fax;

    protected DitibPlace()
    {
    }

    public DitibPlace(String key)
    {
        super();
        this.setKey(key);
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

    public String toString()
    {
        return new ToStringBuilder(this).
                append("ditibCode", ditibCode).
                append("name", getName()).
                append("address", getAddress()).
                append("phone", phone).
                append("fax", fax).
                toString();
    }
}
