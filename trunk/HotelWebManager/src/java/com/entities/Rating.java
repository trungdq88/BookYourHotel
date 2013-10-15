/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "Rating")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rating.findAll", query = "SELECT r FROM Rating r"),
    @NamedQuery(name = "Rating.findByHotelID", query = "SELECT r FROM Rating r WHERE r.ratingPK.hotelID = :hotelID"),
    @NamedQuery(name = "Rating.findByUserID", query = "SELECT r FROM Rating r WHERE r.ratingPK.userID = :userID"),
    @NamedQuery(name = "Rating.findByRating", query = "SELECT r FROM Rating r WHERE r.rating = :rating"),
    @NamedQuery(name = "Rating.findByCreatedDate", query = "SELECT r FROM Rating r WHERE r.createdDate = :createdDate")})
public class Rating implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RatingPK ratingPK;
    @Column(name = "Rating")
    private Integer rating;
    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @JoinColumn(name = "UserID", referencedColumnName = "UserId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "HotelID", referencedColumnName = "HotelID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Hotel hotel;

    public Rating() {
    }

    public Rating(RatingPK ratingPK) {
        this.ratingPK = ratingPK;
    }

    public Rating(int hotelID, int userID) {
        this.ratingPK = new RatingPK(hotelID, userID);
    }

    public RatingPK getRatingPK() {
        return ratingPK;
    }

    public void setRatingPK(RatingPK ratingPK) {
        this.ratingPK = ratingPK;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ratingPK != null ? ratingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rating)) {
            return false;
        }
        Rating other = (Rating) object;
        if ((this.ratingPK == null && other.ratingPK != null) || (this.ratingPK != null && !this.ratingPK.equals(other.ratingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Rating[ ratingPK=" + ratingPK + " ]";
    }
    
}
