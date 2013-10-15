/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.a.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Quang Trung
 */
@Entity
@Table(name = "Properties")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Properties.findAll", query = "SELECT p FROM Properties p"),
    @NamedQuery(name = "Properties.findByPropertyID", query = "SELECT p FROM Properties p WHERE p.propertyID = :propertyID"),
    @NamedQuery(name = "Properties.findByName", query = "SELECT p FROM Properties p WHERE p.name = :name"),
    @NamedQuery(name = "Properties.findByDescription", query = "SELECT p FROM Properties p WHERE p.description = :description")})
public class Properties implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PropertyID")
    private Integer propertyID;
    @Column(name = "Name")
    private String name;
    @Column(name = "Description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "properties")
    private List<RoomProperties> roomPropertiesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "properties")
    private List<HotelProperties> hotelPropertiesList;

    public Properties() {
    }

    public Properties(Integer propertyID) {
        this.propertyID = propertyID;
    }

    public Integer getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(Integer propertyID) {
        this.propertyID = propertyID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<RoomProperties> getRoomPropertiesList() {
        return roomPropertiesList;
    }

    public void setRoomPropertiesList(List<RoomProperties> roomPropertiesList) {
        this.roomPropertiesList = roomPropertiesList;
    }

    @XmlTransient
    public List<HotelProperties> getHotelPropertiesList() {
        return hotelPropertiesList;
    }

    public void setHotelPropertiesList(List<HotelProperties> hotelPropertiesList) {
        this.hotelPropertiesList = hotelPropertiesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (propertyID != null ? propertyID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Properties)) {
            return false;
        }
        Properties other = (Properties) object;
        if ((this.propertyID == null && other.propertyID != null) || (this.propertyID != null && !this.propertyID.equals(other.propertyID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.library.a.entities.Properties[ propertyID=" + propertyID + " ]";
    }
    
}
