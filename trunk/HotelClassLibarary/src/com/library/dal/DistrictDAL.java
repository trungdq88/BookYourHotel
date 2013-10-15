/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.dal;

import com.library.a.entities.HotelProperties;
import com.library.b.entities.District;
import com.library.controllers.DistrictJpaController;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author ThuHoa
 */
public class DistrictDAL {

    private static final DistrictJpaController ldjc = new DistrictJpaController(Persistence.createEntityManagerFactory("HotelClassWebLibraryPU"));
    private static final EntityManager em = ldjc.getEntityManager();

    public static District getDistrict(String districtId) {
        TypedQuery<District> query = em.createNamedQuery("District.findByDistrictid", District.class);
        query.setParameter("districtid", districtId);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get all Districts
     *
     * @return List of Districts Object
     */
    public static List<District> getAllDistricts() {
        TypedQuery<District> query = em.createNamedQuery("District.findAll", District.class);
        List<District> results = query.getResultList();
        return results;
    }

    public static List<District> getDistrictsByProvinceId(String id) {

        TypedQuery<District> q = em.createQuery("SELECT d FROM District d WHERE d.provinceid.provinceid = ?1", District.class);
        q.setParameter(1, id);

        List<District> results = q.getResultList();
        return results;
    }

    public static List<District> getDistrictByProvinceName(String name) {
        TypedQuery<District> q = em.createQuery("Select d FROM District d WHERE d.provinceid.name = ?1", District.class);
        q.setParameter(1, name);

        List<District> results = q.getResultList();
        return results;
    }

    public static void main(String[] args) {
        List<District> districts = DistrictDAL.getDistrictByProvinceName("Hà Nội");
        for (District district : districts) {
            System.out.println(district.getName());
        }
    }

    public static District getDistrictByName(String district) {
        TypedQuery<District> query = em.createNamedQuery("District.findByName", District.class);
        query.setParameter("name", district);
        List<District> results = query.getResultList();
        if (results.size() > 0) {
            return results.get(0);
        } else {
            return null;
        }
    }
}
