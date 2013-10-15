/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Quang Trung
 */
@Entity
@Table(name = "Room")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Room.findAll", query = "SELECT r FROM Room r"),
    @NamedQuery(name = "Room.findByRoomID", query = "SELECT r FROM Room r WHERE r.roomID = :roomID"),
    @NamedQuery(name = "Room.findByRoomName", query = "SELECT r FROM Room r WHERE r.roomName = :roomName"),
    @NamedQuery(name = "Room.findByCapicity", query = "SELECT r FROM Room r WHERE r.capicity = :capicity")})
public class Room implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RoomID")
    private Integer roomID;
    @Size(max = 250)
    @Column(name = "RoomName")
    private String roomName;
    @Column(name = "Capicity")
    private Integer capicity;
    @Lob
    @Size(max = 1073741823)
    @Column(name = "ImageLinks")
    private String imageLinks;
    @Lob
    @Size(max = 1073741823)
    @Column(name = "Description")
    private String description;
    @Lob
    @Size(max = 1073741823)
    @Column(name = "OtherProperties")
    private String otherProperties;
    @JoinColumn(name = "HotelID", referencedColumnName = "HotelID")
    @ManyToOne
    private Hotel hotelID;
    @OneToMany(mappedBy = "roomID")
    private List<RoomPrice> roomPriceList;

    public Room() {
    }

    public Room(Integer roomID) {
        this.roomID = roomID;
    }

    public Integer getRoomID() {
        return roomID;
    }

    public void setRoomID(Integer roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getCapicity() {
        return capicity;
    }

    public void setCapicity(Integer capicity) {
        this.capicity = capicity;
    }

    public String getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(String imageLinks) {
        this.imageLinks = imageLinks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOtherProperties() {
        return otherProperties;
    }

    public void setOtherProperties(String otherProperties) {
        this.otherProperties = otherProperties;
    }

    public Hotel getHotelID() {
        return hotelID;
    }

    public void setHotelID(Hotel hotelID) {
        this.hotelID = hotelID;
    }

    @XmlTransient
    public List<RoomPrice> getRoomPriceList() {
        return roomPriceList;
    }

    public void setRoomPriceList(List<RoomPrice> roomPriceList) {
        this.roomPriceList = roomPriceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomID != null ? roomID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Room)) {
            return false;
        }
        Room other = (Room) object;
        if ((this.roomID == null && other.roomID != null) || (this.roomID != null && !this.roomID.equals(other.roomID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Room[ roomID=" + roomID + " ]";
    }
    
}
