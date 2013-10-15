/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.entities.Website;

/**
 *
 * @author Quang Trung
 */
@Stateless
public class WebsiteFacade extends AbstractFacade<Website> {
    @PersistenceContext(unitName = "HotelCrawlerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WebsiteFacade() {
        super(Website.class);
    }
    
}
