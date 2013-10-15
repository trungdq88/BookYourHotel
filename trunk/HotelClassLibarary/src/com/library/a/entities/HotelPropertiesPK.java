/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.a.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Quang Trung
 */
@Embeddable
public class HotelPropertiesPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "WebsiteID")
    private int websiteID;
    @Basic(optional = false)
    @Column(name = "PropertyID")
    private int propertyID;

    public HotelPropertiesPK() {
    }

    public HotelPropertiesPK(int websiteID, int propertyID) {
        this.websiteID = websiteID;
        this.propertyID = propertyID;
    }

    public int getWebsiteID() {
        return websiteID;
    }

    public void setWebsiteID(int websiteID) {
        this.websiteID = websiteID;
    }

    public int getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(int propertyID) {
        this.propertyID = propertyID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) websiteID;
        hash += (int) propertyID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HotelPropertiesPK)) {
            return false;
        }
        HotelPropertiesPK other = (HotelPropertiesPK) object;
        if (this.websiteID != other.websiteID) {
            return false;
        }
        if (this.propertyID != other.propertyID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.library.a.entities.HotelPropertiesPK[ websiteID=" + websiteID + ", propertyID=" + propertyID + " ]";
    }
    
}
