/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.entities.HotelProperties;

/**
 *
 * @author Quang Trung
 */
@Stateless
public class HotelPropertiesFacade extends AbstractFacade<HotelProperties> {
    @PersistenceContext(unitName = "HotelCrawlerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HotelPropertiesFacade() {
        super(HotelProperties.class);
    }
    
}
