/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Quang Trung
 */
@Entity
@Table(name = "Hotel_Properties")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HotelProperties.findAll", query = "SELECT h FROM HotelProperties h"),
    @NamedQuery(name = "HotelProperties.findByWebsiteID", query = "SELECT h FROM HotelProperties h WHERE h.hotelPropertiesPK.websiteID = :websiteID"),
    @NamedQuery(name = "HotelProperties.findByPropertyID", query = "SELECT h FROM HotelProperties h WHERE h.hotelPropertiesPK.propertyID = :propertyID"),
    @NamedQuery(name = "HotelProperties.findBySelector", query = "SELECT h FROM HotelProperties h WHERE h.selector = :selector"),
    @NamedQuery(name = "HotelProperties.findByResultMode", query = "SELECT h FROM HotelProperties h WHERE h.resultMode = :resultMode"),
    @NamedQuery(name = "HotelProperties.findByAfterRegex", query = "SELECT h FROM HotelProperties h WHERE h.afterRegex = :afterRegex")})
public class HotelProperties implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HotelPropertiesPK hotelPropertiesPK;
    @Size(max = 50)
    @Column(name = "Selector")
    private String selector;
    @Size(max = 50)
    @Column(name = "ResultMode")
    private String resultMode;
    @Size(max = 50)
    @Column(name = "AfterRegex")
    private String afterRegex;
    @JoinColumn(name = "WebsiteID", referencedColumnName = "WebsiteID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Website website;
    @JoinColumn(name = "PropertyID", referencedColumnName = "PropertyID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Properties properties;

    public HotelProperties() {
    }

    public HotelProperties(HotelPropertiesPK hotelPropertiesPK) {
        this.hotelPropertiesPK = hotelPropertiesPK;
    }

    public HotelProperties(int websiteID, int propertyID) {
        this.hotelPropertiesPK = new HotelPropertiesPK(websiteID, propertyID);
    }

    public HotelPropertiesPK getHotelPropertiesPK() {
        return hotelPropertiesPK;
    }

    public void setHotelPropertiesPK(HotelPropertiesPK hotelPropertiesPK) {
        this.hotelPropertiesPK = hotelPropertiesPK;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getResultMode() {
        return resultMode;
    }

    public void setResultMode(String resultMode) {
        this.resultMode = resultMode;
    }

    public String getAfterRegex() {
        return afterRegex;
    }

    public void setAfterRegex(String afterRegex) {
        this.afterRegex = afterRegex;
    }

    public Website getWebsite() {
        return website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hotelPropertiesPK != null ? hotelPropertiesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HotelProperties)) {
            return false;
        }
        HotelProperties other = (HotelProperties) object;
        if ((this.hotelPropertiesPK == null && other.hotelPropertiesPK != null) || (this.hotelPropertiesPK != null && !this.hotelPropertiesPK.equals(other.hotelPropertiesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.HotelProperties[ hotelPropertiesPK=" + hotelPropertiesPK + " ]";
    }
    
}
