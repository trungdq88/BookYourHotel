/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities;

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
@Table(name = "Hotel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hotel.findAll", query = "SELECT h FROM Hotel h"),
    @NamedQuery(name = "Hotel.findByHotelID", query = "SELECT h FROM Hotel h WHERE h.hotelID = :hotelID"),
    @NamedQuery(name = "Hotel.findByWebsiteID", query = "SELECT h FROM Hotel h WHERE h.websiteID = :websiteID"),
    @NamedQuery(name = "Hotel.findByWebsiteName", query = "SELECT h FROM Hotel h WHERE h.websiteName = :websiteName"),
    @NamedQuery(name = "Hotel.findByLinkDetailID", query = "SELECT h FROM Hotel h WHERE h.linkDetailID = :linkDetailID"),
    @NamedQuery(name = "Hotel.findByLinkDetailURL", query = "SELECT h FROM Hotel h WHERE h.linkDetailURL = :linkDetailURL"),
    @NamedQuery(name = "Hotel.findByHotelName", query = "SELECT h FROM Hotel h WHERE h.hotelName = :hotelName"),
    @NamedQuery(name = "Hotel.findByHotelAddress", query = "SELECT h FROM Hotel h WHERE h.hotelAddress = :hotelAddress"),
    @NamedQuery(name = "Hotel.findByStars", query = "SELECT h FROM Hotel h WHERE h.stars = :stars"),
    @NamedQuery(name = "Hotel.findByRating", query = "SELECT h FROM Hotel h WHERE h.rating = :rating")})
public class Hotel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HotelID")
    private Integer hotelID;
    @Column(name = "WebsiteID")
    private Integer websiteID;
    @Size(max = 50)
    @Column(name = "WebsiteName")
    private String websiteName;
    @Column(name = "LinkDetailID")
    private Integer linkDetailID;
    @Size(max = 255)
    @Column(name = "LinkDetailURL")
    private String linkDetailURL;
    @Size(max = 50)
    @Column(name = "HotelName")
    private String hotelName;
    @Size(max = 50)
    @Column(name = "HotelAddress")
    private String hotelAddress;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "Description")
    private String description;
    @Column(name = "Stars")
    private Integer stars;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "Images")
    private String images;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Rating")
    private Double rating;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "OtherProperties")
    private String otherProperties;
    @OneToMany(mappedBy = "hotelID")
    private List<Room> roomList;
    @JoinColumn(name = "RegionID", referencedColumnName = "RegionID")
    @ManyToOne
    private Region regionID;

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

    public Integer getWebsiteID() {
        return websiteID;
    }

    public void setWebsiteID(Integer websiteID) {
        this.websiteID = websiteID;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public Integer getLinkDetailID() {
        return linkDetailID;
    }

    public void setLinkDetailID(Integer linkDetailID) {
        this.linkDetailID = linkDetailID;
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

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
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

    @XmlTransient
    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public Region getRegionID() {
        return regionID;
    }

    public void setRegionID(Region regionID) {
        this.regionID = regionID;
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
        return "jpa.entities.Hotel[ hotelID=" + hotelID + " ]";
    }
    
}
