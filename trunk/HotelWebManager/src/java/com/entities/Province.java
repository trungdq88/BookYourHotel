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
@Table(name = "province")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Province.findAll", query = "SELECT p FROM Province p"),
    @NamedQuery(name = "Province.findByProvinceid", query = "SELECT p FROM Province p WHERE p.provinceid = :provinceid"),
    @NamedQuery(name = "Province.findByName", query = "SELECT p FROM Province p WHERE p.name = :name"),
    @NamedQuery(name = "Province.findByType", query = "SELECT p FROM Province p WHERE p.type = :type")})
public class Province implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "provinceid")
    private String provinceid;
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
    @OneToMany(mappedBy = "provinceid")
    private List<Hotel> hotelList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "provinceid")
    private List<District> districtList;

    public Province() {
    }

    public Province(String provinceid) {
        this.provinceid = provinceid;
    }

    public Province(String provinceid, String name, String type) {
        this.provinceid = provinceid;
        this.name = name;
        this.type = type;
    }

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
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

    @XmlTransient
    public List<Hotel> getHotelList() {
        return hotelList;
    }

    public void setHotelList(List<Hotel> hotelList) {
        this.hotelList = hotelList;
    }

    @XmlTransient
    public List<District> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<District> districtList) {
        this.districtList = districtList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (provinceid != null ? provinceid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Province)) {
            return false;
        }
        Province other = (Province) object;
        if ((this.provinceid == null && other.provinceid != null) || (this.provinceid != null && !this.provinceid.equals(other.provinceid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Province[ provinceid=" + provinceid + " ]";
    }
    
}
