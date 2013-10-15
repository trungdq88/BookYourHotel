/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.entities.RoomProperties;

/**
 *
 * @author Quang Trung
 */
@Stateless
public class RoomPropertiesFacade extends AbstractFacade<RoomProperties> {
    @PersistenceContext(unitName = "HotelCrawlerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RoomPropertiesFacade() {
        super(RoomProperties.class);
    }
    
}
