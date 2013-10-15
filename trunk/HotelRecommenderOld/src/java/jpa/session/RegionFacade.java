/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.entities.Region;

/**
 *
 * @author Quang Trung
 */
@Stateless
public class RegionFacade extends AbstractFacade<Region> {
    @PersistenceContext(unitName = "HotelRecommenderPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegionFacade() {
        super(Region.class);
    }
    
}
