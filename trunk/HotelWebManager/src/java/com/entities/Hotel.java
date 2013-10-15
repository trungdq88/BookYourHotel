/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "Hotel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hotel.findAll", query = "SELECT h FROM Hotel h"),
    @NamedQuery(name = "Hotel.findByHotelID", query = "SELECT h FROM Hotel h WHERE h.hotelID = :hotelID"),
    @NamedQuery(name = "Hotel.findByWebsiteName", query = "SELECT h FROM Hotel h WHERE h.websiteName = :websiteName"),
    @NamedQuery(name = "Hotel.findByLinkDetailURL", query = "SELECT h FROM Hotel h WHERE h.linkDetailURL = :linkDetailURL"),
    @NamedQuery(name = "Hotel.findByHotelName", query = "SELECT h FROM Hotel h WHERE h.hotelName = :hotelName"),
    @NamedQuery(name = "Hotel.findByStar", query = "SELECT h FROM Hotel h WHERE h.star = :star"),
    @NamedQuery(name = "Hotel.findByRating", query = "SELECT h FROM Hotel h WHERE h.rating = :rating"),
    @NamedQuery(name = "Hotel.findByAddress", query = "SELECT h FROM Hotel h WHERE h.address = :address")})
public class Hotel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HotelID")
    private Integer hotelID;
    @Size(max = 250)
    @Column(name = "WebsiteName")
    private String websiteName;
    @Size(max = 255)
    @Column(name = "LinkDetailURL")
    private String linkDetailURL;
    @Size(max = 250)
    @Column(name = "HotelName")
    private String hotelName;
    @Lob
    @Size(max = 1073741823)
    @Column(name = "Description")
    private String description;
    @Column(name = "Star")
    private Integer star;
    @Lob
    @Size(max = 1073741823)
    @Column(name = "ImageLinks")
    private String imageLinks;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Rating")
    private Double rating;
    @Lob
    @Size(max = 1073741823)
    @Column(name = "OtherProperties")
    private String otherProperties;
    @Size(max = 250)
    @Column(name = "Address")
    private String address;
    @OneToMany(mappedBy = "hotelID")
    private List<Room> roomList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
    private List<Rating> ratingList;
    @OneToMany(mappedBy = "hotelID")
    private List<Comment> commentList;
    @JoinColumn(name = "wardid", referencedColumnName = "wardid")
    @ManyToOne
    private Ward wardid;
    @JoinColumn(name = "provinceid", referencedColumnName = "provinceid")
    @ManyToOne
    private Province provinceid;
    @JoinColumn(name = "districtid", referencedColumnName = "districtid")
    @ManyToOne
    private District districtid;

    public Hotel() {
    }

    public Hotel(Integer hotelID) {
        this.hotelID = hotelID;
    }

    public Integer getHotelID() {
        return hotelID;
    }

    public void setHotelID(Integer hotelID) {
        this.hotelID = hotelID;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getLinkDetailURL() {
        return linkDetailURL;
    }

    public void setLinkDetailURL(String linkDetailURL) {
        this.linkDetailURL = linkDetailURL;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(String imageLinks) {
        this.imageLinks = imageLinks;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getOtherProperties() {
        return otherProperties;
    }

    public void setOtherProperties(String otherProperties) {
        this.otherProperties = otherProperties;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @XmlTransient
    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    @XmlTransient
    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    @XmlTransient
    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Ward getWardid() {
        return wardid;
    }

    public void setWardid(Ward wardid) {
        this.wardid = wardid;
    }

    public Province getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(Province provinceid) {
        this.provinceid = provinceid;
    }

    public District getDistrictid() {
        return districtid;
    }

    public void setDistrictid(District districtid) {
        this.districtid = districtid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hotelID != null ? hotelID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hotel)) {
            return false;
        }
        Hotel other = (Hotel) object;
        if ((this.hotelID == null && other.hotelID != null) || (this.hotelID != null && !this.hotelID.equals(other.hotelID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Hotel[ hotelID=" + hotelID + " ]";
    }
    
}
