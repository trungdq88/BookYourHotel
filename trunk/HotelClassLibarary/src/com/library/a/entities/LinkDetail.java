/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.a.entities;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Quang Trung
 */
@Entity
@Table(name = "LinkDetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LinkDetail.findAll", query = "SELECT l FROM LinkDetail l"),
    @NamedQuery(name = "LinkDetail.findByLinkDetailID", query = "SELECT l FROM LinkDetail l WHERE l.linkDetailID = :linkDetailID"),
    @NamedQuery(name = "LinkDetail.findByUrl", query = "SELECT l FROM LinkDetail l WHERE l.url = :url")})
public class LinkDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "LinkDetailID")
    private Integer linkDetailID;
    @Column(name = "Url")
    private String url;
    @JoinColumn(name = "WebsiteID", referencedColumnName = "WebsiteID")
    @ManyToOne
    private Website websiteID;

    public LinkDetail() {
    }

    public LinkDetail(Integer linkDetailID) {
        this.linkDetailID = linkDetailID;
    }

    public Integer getLinkDetailID() {
        return linkDetailID;
    }

    public void setLinkDetailID(Integer linkDetailID) {
        this.linkDetailID = linkDetailID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Website getWebsiteID() {
        return websiteID;
    }

    public void setWebsiteID(Website websiteID) {
        this.websiteID = websiteID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (linkDetailID != null ? linkDetailID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LinkDetail)) {
            return false;
        }
        LinkDetail other = (LinkDetail) object;
        if ((this.linkDetailID == null && other.linkDetailID != null) || (this.linkDetailID != null && !this.linkDetailID.equals(other.linkDetailID))) {
            return false;
        }
        
        if (this.getUrl().toLowerCase().equals(other.getUrl().toLowerCase())) return true;
        return false;
    }

    @Override
    public String toString() {
        return "com.library.a.entities.LinkDetail[ linkDetailID=" + linkDetailID + " ]";
    }
    
}
