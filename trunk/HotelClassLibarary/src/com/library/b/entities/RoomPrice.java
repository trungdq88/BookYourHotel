/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.b.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Quang Trung
 */
@Entity
@Table(name = "RoomPrice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RoomPrice.findAll", query = "SELECT r FROM RoomPrice r"),
    @NamedQuery(name = "RoomPrice.findByRoomPriceID", query = "SELECT r FROM RoomPrice r WHERE r.roomPriceID = :roomPriceID"),
    @NamedQuery(name = "RoomPrice.findByCheckedIn", query = "SELECT r FROM RoomPrice r WHERE r.checkedIn = :checkedIn"),
    @NamedQuery(name = "RoomPrice.findByCheckOut", query = "SELECT r FROM RoomPrice r WHERE r.checkOut = :checkOut"),
    @NamedQuery(name = "RoomPrice.findByPrice", query = "SELECT r FROM RoomPrice r WHERE r.price = :price")})
public class RoomPrice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RoomPriceID")
    private Integer roomPriceID;
    @Column(name = "CheckedIn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkedIn;
    @Column(name = "CheckOut")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkOut;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Price")
    private Double price;
    @JoinColumn(name = "RoomID", referencedColumnName = "RoomID")
    @ManyToOne
    private Room roomID;

    public RoomPrice() {
    }

    public RoomPrice(Integer roomPriceID) {
        this.roomPriceID = roomPriceID;
    }

    public Integer getRoomPriceID() {
        return roomPriceID;
    }

    public void setRoomPriceID(Integer roomPriceID) {
        this.roomPriceID = roomPriceID;
    }

    public Date getCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(Date checkedIn) {
        this.checkedIn = checkedIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Room getRoomID() {
        return roomID;
    }

    public void setRoomID(Room roomID) {
        this.roomID = roomID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomPriceID != null ? roomPriceID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoomPrice)) {
            return false;
        }
        RoomPrice other = (RoomPrice) object;
        if ((this.roomPriceID == null && other.roomPriceID != null) || (this.roomPriceID != null && !this.roomPriceID.equals(other.roomPriceID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.library.b.entities.RoomPrice[ roomPriceID=" + roomPriceID + " ]";
    }
    
}
