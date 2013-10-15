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
@Table(name = "district")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "District.findAll", query = "SELECT d FROM District d"),
    @NamedQuery(name = "District.findByDistrictid", query = "SELECT d FROM District d WHERE d.districtid = :districtid"),
    @NamedQuery(name = "District.findByName", query = "SELECT d FROM District d WHERE d.name = :name"),
    @NamedQuery(name = "District.findByType", query = "SELECT d FROM District d WHERE d.type = :type"),
    @NamedQuery(name = "District.findByLocation", query = "SELECT d FROM District d WHERE d.location = :location")})
public class District implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "districtid")
    private String districtid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "location")
    private String location;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "districtid")
    private List<Ward> wardList;
    @OneToMany(mappedBy = "districtid")
    private List<Hotel> hotelList;
    @JoinColumn(name = "provinceid", referencedColumnName = "provinceid")
    @ManyToOne(optional = false)
    private Province provinceid;

    public District() {
    }

    public District(String districtid) {
        this.districtid = districtid;
    }

    public District(String districtid, String name, String type, String location) {
        this.districtid = districtid;
        this.name = name;
        this.type = type;
        this.location = location;
    }

    public String getDistrictid() {
        return districtid;
    }

    public void setDistrictid(String districtid) {
        this.districtid = districtid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @XmlTransient
    public List<Ward> getWardList() {
        return wardList;
    }

    public void setWardList(List<Ward> wardList) {
        this.wardList = wardList;
    }

    @XmlTransient
    public List<Hotel> getHotelList() {
        return hotelList;
    }

    public void setHotelList(List<Hotel> hotelList) {
        this.hotelList = hotelList;
    }

    public Province getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(Province provinceid) {
        this.provinceid = provinceid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (districtid != null ? districtid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof District)) {
            return false;
        }
        District other = (District) object;
        if ((this.districtid == null && other.districtid != null) || (this.districtid != null && !this.districtid.equals(other.districtid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.District[ districtid=" + districtid + " ]";
    }
    
}
