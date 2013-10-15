/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.entities.LinkDetail;

/**
 *
 * @author Quang Trung
 */
@Stateless
public class LinkDetailFacade extends AbstractFacade<LinkDetail> {
    @PersistenceContext(unitName = "HotelCrawlerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LinkDetailFacade() {
        super(LinkDetail.class);
    }
    
}
