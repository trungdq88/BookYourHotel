package com.library.dal;

import com.library.a.entities.HotelProperties;
import com.library.a.entities.Properties;
import com.library.a.entities.RoomProperties;
import com.library.a.entities.Website;
import com.library.controllers.PropertiesJpaController;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class PropertiesDAL {

    PropertiesJpaController pjc = new PropertiesJpaController(Persistence.createEntityManagerFactory("HotelClassLibraryPU"));
    EntityManager em = pjc.getEntityManager();
    
    
    /**
     * Get all available Properties (both Hotel and Room)
     * @return List of Properties Object
     */
    public List<Properties> getAllProperties() {
        TypedQuery<Properties> query = em.createNamedQuery("Properties.findAll", Properties.class);
        List<Properties> results = query.getResultList();
        return results;
    }

    /**
     * Get all Hotel Properties of specific Website
     * @param web Website Object
     * @return List of Hotel Properties
     */
    public List<HotelProperties> getHotelProperties(Website web) {
        TypedQuery<HotelProperties> q = em.createQuery("SELECT hp FROM Properties p, HotelProperties hp WHERE hp.hotelPropertiesPK.websiteID = ?1 AND hp.hotelPropertiesPK.propertyID = p.propertyID", HotelProperties.class);
        q.setParameter(1, web.getWebsiteID());

        List<HotelProperties> results = q.getResultList();
        return results;
    }
    
    public List<RoomProperties> getRoomProperties(Website web) {
        TypedQuery<RoomProperties> q = em.createQuery("SELECT rp FROM Properties p, RoomProperties rp WHERE rp.roomPropertiesPK.websiteID = ?1 AND rp.roomPropertiesPK.propertyID = p.propertyID", RoomProperties.class);
        q.setParameter(1, web.getWebsiteID());

        List<RoomProperties> results = q.getResultList();
        return results;
    }
    
}
