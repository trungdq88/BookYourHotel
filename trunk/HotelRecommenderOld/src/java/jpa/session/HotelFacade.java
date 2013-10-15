/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.entities.Hotel;

/**
 *
 * @author Quang Trung
 */
@Stateless
public class HotelFacade extends AbstractFacade<Hotel> {
    @PersistenceContext(unitName = "HotelRecommenderPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HotelFacade() {
        super(Hotel.class);
    }
    
}
