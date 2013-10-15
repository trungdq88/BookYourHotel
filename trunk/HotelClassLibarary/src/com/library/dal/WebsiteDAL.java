package com.library.dal;

import com.library.a.entities.Website;
import com.library.controllers.WebsiteJpaController;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class WebsiteDAL {
    WebsiteJpaController wjc = new WebsiteJpaController(Persistence.createEntityManagerFactory("HotelClassLibraryPU"));
    EntityManager em = wjc.getEntityManager();

    /**
     * Get all websites
     * @return List of Website Object
     */
    public List<Website> getAllWebsites() {
        TypedQuery<Website> query = em.createNamedQuery("Website.findAll", Website.class);
        List<Website> results = query.getResultList();
        return results;
    }
    
    /**
     * Get a website by ID
     * @param id Website ID
     * @return Website
     */
    public Website getWebsiteByID(int id) {
        TypedQuery<Website> query = em.createNamedQuery("Website.findByWebsiteID", Website.class).setParameter("websiteID", id);
        Website result = query.getSingleResult();
        return result; 
    }
}
