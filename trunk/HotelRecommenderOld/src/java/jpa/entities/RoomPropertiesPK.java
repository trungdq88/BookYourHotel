/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Quang Trung
 */
@Embeddable
public class RoomPropertiesPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "WebsiteID")
    private int websiteID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PropertyID")
    private int propertyID;

    public RoomPropertiesPK() {
    }

    public RoomPropertiesPK(int websiteID, int propertyID) {
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
        if (!(object instanceof RoomPropertiesPK)) {
            return false;
        }
        RoomPropertiesPK other = (RoomPropertiesPK) object;
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
        return "jpa.entities.RoomPropertiesPK[ websiteID=" + websiteID + ", propertyID=" + propertyID + " ]";
    }
    
}
