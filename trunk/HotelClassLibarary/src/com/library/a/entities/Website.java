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
@Table(name = "Website")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Website.findAll", query = "SELECT w FROM Website w"),
    @NamedQuery(name = "Website.findByWebsiteID", query = "SELECT w FROM Website w WHERE w.websiteID = :websiteID"),
    @NamedQuery(name = "Website.findByWebsiteName", query = "SELECT w FROM Website w WHERE w.websiteName = :websiteName"),
    @NamedQuery(name = "Website.findByHomepage", query = "SELECT w FROM Website w WHERE w.homepage = :homepage"),
    @NamedQuery(name = "Website.findByCrawlerSeed", query = "SELECT w FROM Website w WHERE w.crawlerSeed = :crawlerSeed"),
    @NamedQuery(name = "Website.findByCrawlerMatch", query = "SELECT w FROM Website w WHERE w.crawlerMatch = :crawlerMatch"),
    @NamedQuery(name = "Website.findByCrawlerIDLink", query = "SELECT w FROM Website w WHERE w.crawlerIDLink = :crawlerIDLink"),
    @NamedQuery(name = "Website.findByCrawlerIDFrom", query = "SELECT w FROM Website w WHERE w.crawlerIDFrom = :crawlerIDFrom"),
    @NamedQuery(name = "Website.findByCrawlerIDTo", query = "SELECT w FROM Website w WHERE w.crawlerIDTo = :crawlerIDTo"),
    @NamedQuery(name = "Website.findByCrawlerCheckContent", query = "SELECT w FROM Website w WHERE w.crawlerCheckContent = :crawlerCheckContent"),
    @NamedQuery(name = "Website.findByCheckInParaName", query = "SELECT w FROM Website w WHERE w.checkInParaName = :checkInParaName"),
    @NamedQuery(name = "Website.findByCheckOutParaName", query = "SELECT w FROM Website w WHERE w.checkOutParaName = :checkOutParaName"),
    @NamedQuery(name = "Website.findByDateFormat", query = "SELECT w FROM Website w WHERE w.dateFormat = :dateFormat"),
    @NamedQuery(name = "Website.findByOtherParaNames", query = "SELECT w FROM Website w WHERE w.otherParaNames = :otherParaNames"),
    @NamedQuery(name = "Website.findByRequestMethod", query = "SELECT w FROM Website w WHERE w.requestMethod = :requestMethod"),
    @NamedQuery(name = "Website.findByUseCookie", query = "SELECT w FROM Website w WHERE w.useCookie = :useCookie")})
public class Website implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "WebsiteID")
    private Integer websiteID;
    @Column(name = "WebsiteName")
    private String websiteName;
    @Column(name = "Homepage")
    private String homepage;
    @Column(name = "CrawlerSeed")
    private String crawlerSeed;
    @Column(name = "CrawlerMatch")
    private String crawlerMatch;
    @Column(name = "CrawlerIDLink")
    private String crawlerIDLink;
    @Column(name = "CrawlerIDFrom")
    private Integer crawlerIDFrom;
    @Column(name = "CrawlerIDTo")
    private Integer crawlerIDTo;
    @Column(name = "CrawlerCheckContent")
    private String crawlerCheckContent;
    @Column(name = "CheckInParaName")
    private String checkInParaName;
    @Column(name = "CheckOutParaName")
    private String checkOutParaName;
    @Column(name = "DateFormat")
    private String dateFormat;
    @Column(name = "OtherParaNames")
    private String otherParaNames;
    @Column(name = "RequestMethod")
    private String requestMethod;
    @Column(name = "UseCookie")
    private Integer useCookie;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "website")
    private List<RoomProperties> roomPropertiesList;
    @OneToMany(mappedBy = "websiteID")
    private List<LinkDetail> linkDetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "website")
    private List<HotelProperties> hotelPropertiesList;

    public Website() {
    }

    public Website(Integer websiteID) {
        this.websiteID = websiteID;
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

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getCrawlerSeed() {
        return crawlerSeed;
    }

    public void setCrawlerSeed(String crawlerSeed) {
        this.crawlerSeed = crawlerSeed;
    }

    public String getCrawlerMatch() {
        return crawlerMatch;
    }

    public void setCrawlerMatch(String crawlerMatch) {
        this.crawlerMatch = crawlerMatch;
    }

    public String getCrawlerIDLink() {
        return crawlerIDLink;
    }

    public void setCrawlerIDLink(String crawlerIDLink) {
        this.crawlerIDLink = crawlerIDLink;
    }

    public Integer getCrawlerIDFrom() {
        return crawlerIDFrom;
    }

    public void setCrawlerIDFrom(Integer crawlerIDFrom) {
        this.crawlerIDFrom = crawlerIDFrom;
    }

    public Integer getCrawlerIDTo() {
        return crawlerIDTo;
    }

    public void setCrawlerIDTo(Integer crawlerIDTo) {
        this.crawlerIDTo = crawlerIDTo;
    }

    public String getCrawlerCheckContent() {
        return crawlerCheckContent;
    }

    public void setCrawlerCheckContent(String crawlerCheckContent) {
        this.crawlerCheckContent = crawlerCheckContent;
    }

    public String getCheckInParaName() {
        return checkInParaName;
    }

    public void setCheckInParaName(String checkInParaName) {
        this.checkInParaName = checkInParaName;
    }

    public String getCheckOutParaName() {
        return checkOutParaName;
    }

    public void setCheckOutParaName(String checkOutParaName) {
        this.checkOutParaName = checkOutParaName;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getOtherParaNames() {
        return otherParaNames;
    }

    public void setOtherParaNames(String otherParaNames) {
        this.otherParaNames = otherParaNames;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Integer getUseCookie() {
        return useCookie;
    }

    public void setUseCookie(Integer useCookie) {
        this.useCookie = useCookie;
    }

    @XmlTransient
    public List<RoomProperties> getRoomPropertiesList() {
        return roomPropertiesList;
    }

    public void setRoomPropertiesList(List<RoomProperties> roomPropertiesList) {
        this.roomPropertiesList = roomPropertiesList;
    }

    @XmlTransient
    public List<LinkDetail> getLinkDetailList() {
        return linkDetailList;
    }

    public void setLinkDetailList(List<LinkDetail> linkDetailList) {
        this.linkDetailList = linkDetailList;
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
        hash += (websiteID != null ? websiteID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Website)) {
            return false;
        }
        Website other = (Website) object;
        if ((this.websiteID == null && other.websiteID != null) || (this.websiteID != null && !this.websiteID.equals(other.websiteID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.library.a.entities.Website[ websiteID=" + websiteID + " ]";
    }
    
}
