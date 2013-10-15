/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.session;

import com.entities.Rating;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Quang Trung
 */
@Stateless
public class RatingFacade extends AbstractFacade<Rating> {
    @PersistenceContext(unitName = "HotelWebManagerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RatingFacade() {
        super(Rating.class);
    }
    
}
