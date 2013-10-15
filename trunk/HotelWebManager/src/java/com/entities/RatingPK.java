/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

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
public class RatingPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "HotelID")
    private int hotelID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UserID")
    private int userID;

    public RatingPK() {
    }

    public RatingPK(int hotelID, int userID) {
        this.hotelID = hotelID;
        this.userID = userID;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) hotelID;
        hash += (int) userID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RatingPK)) {
            return false;
        }
        RatingPK other = (RatingPK) object;
        if (this.hotelID != other.hotelID) {
            return false;
        }
        if (this.userID != other.userID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.RatingPK[ hotelID=" + hotelID + ", userID=" + userID + " ]";
    }
    
}
