/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.a.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Quang Trung
 */
@Entity
@Table(name = "Room_Properties")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RoomProperties.findAll", query = "SELECT r FROM RoomProperties r"),
    @NamedQuery(name = "RoomProperties.findByWebsiteID", query = "SELECT r FROM RoomProperties r WHERE r.roomPropertiesPK.websiteID = :websiteID"),
    @NamedQuery(name = "RoomProperties.findByPropertyID", query = "SELECT r FROM RoomProperties r WHERE r.roomPropertiesPK.propertyID = :propertyID"),
    @NamedQuery(name = "RoomProperties.findBySelector", query = "SELECT r FROM RoomProperties r WHERE r.selector = :selector"),
    @NamedQuery(name = "RoomProperties.findByResultMode", query = "SELECT r FROM RoomProperties r WHERE r.resultMode = :resultMode"),
    @NamedQuery(name = "RoomProperties.findByAfterRegex", query = "SELECT r FROM RoomProperties r WHERE r.afterRegex = :afterRegex")})
public class RoomProperties implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RoomPropertiesPK roomPropertiesPK;
    @Column(name = "Selector")
    private String selector;
    @Column(name = "ResultMode")
    private String resultMode;
    @Column(name = "AfterRegex")
    private String afterRegex;
    @JoinColumn(name = "WebsiteID", referencedColumnName = "WebsiteID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Website website;
    @JoinColumn(name = "PropertyID", referencedColumnName = "PropertyID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Properties properties;

    public RoomProperties() {
    }

    public RoomProperties(RoomPropertiesPK roomPropertiesPK) {
        this.roomPropertiesPK = roomPropertiesPK;
    }

    public RoomProperties(int websiteID, int propertyID) {
        this.roomPropertiesPK = new RoomPropertiesPK(websiteID, propertyID);
    }

    public RoomPropertiesPK getRoomPropertiesPK() {
        return roomPropertiesPK;
    }

    public void setRoomPropertiesPK(RoomPropertiesPK roomPropertiesPK) {
        this.roomPropertiesPK = roomPropertiesPK;
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
        hash += (roomPropertiesPK != null ? roomPropertiesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoomProperties)) {
            return false;
        }
        RoomProperties other = (RoomProperties) object;
        if ((this.roomPropertiesPK == null && other.roomPropertiesPK != null) || (this.roomPropertiesPK != null && !this.roomPropertiesPK.equals(other.roomPropertiesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.library.a.entities.RoomProperties[ roomPropertiesPK=" + roomPropertiesPK + " ]";
    }
    
}
