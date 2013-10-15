/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.session;

import com.entities.Ward;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Quang Trung
 */
@Stateless
public class WardFacade extends AbstractFacade<Ward> {
    @PersistenceContext(unitName = "HotelWebManagerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WardFacade() {
        super(Ward.class);
    }
    
}
