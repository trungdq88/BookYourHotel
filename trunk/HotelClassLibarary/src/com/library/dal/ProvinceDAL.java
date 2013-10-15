/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.dal;

import com.library.b.entities.Province;
import com.library.controllers.ProvinceJpaController;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author ThuHoa
 */
public class ProvinceDAL {

    private static final ProvinceJpaController ldjc = new ProvinceJpaController(Persistence.createEntityManagerFactory("HotelClassWebLibraryPU"));
    private static final EntityManager em = ldjc.getEntityManager();

    public static Province getProvince(String provinceId) {
        TypedQuery<Province> query = em.createNamedQuery("Province.findByProvinceid", Province.class);
        query.setParameter("provinceid", provinceId);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get all Provinces
     *
     * @return List of Provinces Object
     */
    public static List<Province> getAllProvinces() {
        TypedQuery<Province> query = em.createNamedQuery("Province.findAll", Province.class);
        List<Province> results = query.getResultList();
        return results;
    }

    public static void main(String[] args) {
        List<Province> districts = ProvinceDAL.getAllProvinces();
        for (int i = 0; i < districts.size(); i++) {
            System.out.println(districts.get(i).getName());
        }
    }

    public static Province getProvinceByName(String region) {
        TypedQuery<Province> query = em.createNamedQuery("Province.findByName", Province.class);
        query.setParameter("name", region);
        List<Province> results = query.getResultList();
        if (results.size() > 0) {
            return results.get(0);
        } else {
            return null;
        }
    }

    public static List<Province> getAllProvincesByHotelCount() {
        Query q = em.createNativeQuery("SELECT TOP(12) p.provinceId FROM Province p, Hotel h "
                + "WHERE h.provinceId = p.provinceId "
                + "GROUP BY p.provinceId "
                + "ORDER BY COUNT(h.HotelID) DESC");
        List<String> results = q.getResultList();
        
        List<Province> provinces = new ArrayList<Province>();
        for (String id: results) {
            provinces.add(getProvince(id));
        }
        
        return provinces;
    }
}
