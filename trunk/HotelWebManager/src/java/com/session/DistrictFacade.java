/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.session;

import com.entities.District;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Quang Trung
 */
@Stateless
public class DistrictFacade extends AbstractFacade<District> {
    @PersistenceContext(unitName = "HotelWebManagerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DistrictFacade() {
        super(District.class);
    }
    
}
