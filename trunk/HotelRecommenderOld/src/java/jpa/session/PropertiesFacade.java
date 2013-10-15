/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.entities.Properties;

/**
 *
 * @author Quang Trung
 */
@Stateless
public class PropertiesFacade extends AbstractFacade<Properties> {
    @PersistenceContext(unitName = "HotelCrawlerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PropertiesFacade() {
        super(Properties.class);
    }
    
}
