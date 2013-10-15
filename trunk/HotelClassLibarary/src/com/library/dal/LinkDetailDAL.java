package com.library.dal;

import com.library.a.entities.LinkDetail;
import com.library.a.entities.Website;
import com.library.controllers.LinkDetailJpaController;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class LinkDetailDAL {

    LinkDetailJpaController ldjc = new LinkDetailJpaController(Persistence.createEntityManagerFactory("HotelClassLibraryPU"));
    EntityManager em = ldjc.getEntityManager();

    /**
     * Add new Link Detail
     * @param link LinkDetail Object
     * @return true if success
     */
    public boolean insertLinkDetail(LinkDetail link) {
        try {
            ldjc.create(link);
            return true;
        } catch (Exception e) {
            System.out.println("LinkDetailDAL.insertLinkDetail: " + e.toString());
            return false;
        }

    }
    
    /**
     * Get all Link Detail
     * @return List of Link Detail Object
     */
    public List<LinkDetail> getAllLinkDetails() {
        TypedQuery<LinkDetail> query = em.createNamedQuery("LinkDetail.findAll", LinkDetail.class);
        List<LinkDetail> results = query.getResultList();
        return results;
    }

    public List<LinkDetail> getLinksByWebsite(Website site) {
        TypedQuery<LinkDetail> q = em.createQuery("SELECT ld FROM LinkDetail ld WHERE ld.websiteID.websiteID = ?1", LinkDetail.class);
        q.setParameter(1, site.getWebsiteID());

        List<LinkDetail> results = q.getResultList();
        return results;
        
    }
}
