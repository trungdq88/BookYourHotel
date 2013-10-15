/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.dal;

import com.library.b.entities.District;
import com.library.b.entities.Ward;
import com.library.controllers.WardJpaController;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Quang Trung
 */
public class WardDAL {

    private static final WardJpaController ldjc = new WardJpaController(Persistence.createEntityManagerFactory("HotelClassWebLibraryPU"));
    private static final EntityManager em = ldjc.getEntityManager();

    /**
     * Get a ward by id
     *
     * @param wardId
     * @return
     */
    public static Ward getWard(String wardId) {
        TypedQuery<Ward> query = em.createNamedQuery("Ward.findByWardid", Ward.class);
        query.setParameter("wardid", wardId);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Get all Wards
     *
     * @return List of Ward Object
     */
    public static List<Ward> getAllWards() {
        TypedQuery<Ward> query = em.createNamedQuery("Ward.findAll", Ward.class);
        List<Ward> results = query.getResultList();
        return results;
    }

    public static List<Ward> getWardsByDistrictName(String name) {
        TypedQuery<Ward> q = em.createQuery("Select w from Ward w where w.districtid.name = ?1", Ward.class);
        q.setParameter(1, name);

        List<Ward> results = q.getResultList();
        return results;
    }

    public static void main(String[] args) {
        WardDAL dal = new WardDAL();
        List<Ward> wards = dal.getWardsByDistrictName("Ba Đình");
        for (int i = 0; i < wards.size(); i++) {
            System.out.println(wards.get(i).getName());
        }
    }

    public static Ward getWardByName(String ward) {
        TypedQuery<Ward> query = em.createNamedQuery("Ward.findByName", Ward.class);
        query.setParameter("name", ward);
        List<Ward> results = query.getResultList();
        if (results.size() > 0) {
            return results.get(0);
        } else {
            return null;
        }
    }
}
