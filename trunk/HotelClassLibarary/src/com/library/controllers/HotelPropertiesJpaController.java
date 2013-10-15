/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.controllers;

import com.library.a.entities.HotelProperties;
import com.library.a.entities.HotelPropertiesPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.library.a.entities.Website;
import com.library.a.entities.Properties;
import com.library.controllers.exceptions.NonexistentEntityException;
import com.library.controllers.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ThuHoa
 */
public class HotelPropertiesJpaController implements Serializable {

    public HotelPropertiesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HotelProperties hotelProperties) throws PreexistingEntityException, Exception {
        if (hotelProperties.getHotelPropertiesPK() == null) {
            hotelProperties.setHotelPropertiesPK(new HotelPropertiesPK());
        }
        hotelProperties.getHotelPropertiesPK().setWebsiteID(hotelProperties.getWebsite().getWebsiteID());
        hotelProperties.getHotelPropertiesPK().setPropertyID(hotelProperties.getProperties().getPropertyID());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Website website = hotelProperties.getWebsite();
            if (website != null) {
                website = em.getReference(website.getClass(), website.getWebsiteID());
                hotelProperties.setWebsite(website);
            }
            Properties properties = hotelProperties.getProperties();
            if (properties != null) {
                properties = em.getReference(properties.getClass(), properties.getPropertyID());
                hotelProperties.setProperties(properties);
            }
            em.persist(hotelProperties);
            if (website != null) {
                website.getHotelPropertiesList().add(hotelProperties);
                website = em.merge(website);
            }
            if (properties != null) {
                properties.getHotelPropertiesList().add(hotelProperties);
                properties = em.merge(properties);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHotelProperties(hotelProperties.getHotelPropertiesPK()) != null) {
                throw new PreexistingEntityException("HotelProperties " + hotelProperties + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HotelProperties hotelProperties) throws NonexistentEntityException, Exception {
        hotelProperties.getHotelPropertiesPK().setWebsiteID(hotelProperties.getWebsite().getWebsiteID());
        hotelProperties.getHotelPropertiesPK().setPropertyID(hotelProperties.getProperties().getPropertyID());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HotelProperties persistentHotelProperties = em.find(HotelProperties.class, hotelProperties.getHotelPropertiesPK());
            Website websiteOld = persistentHotelProperties.getWebsite();
            Website websiteNew = hotelProperties.getWebsite();
            Properties propertiesOld = persistentHotelProperties.getProperties();
            Properties propertiesNew = hotelProperties.getProperties();
            if (websiteNew != null) {
                websiteNew = em.getReference(websiteNew.getClass(), websiteNew.getWebsiteID());
                hotelProperties.setWebsite(websiteNew);
            }
            if (propertiesNew != null) {
                propertiesNew = em.getReference(propertiesNew.getClass(), propertiesNew.getPropertyID());
                hotelProperties.setProperties(propertiesNew);
            }
            hotelProperties = em.merge(hotelProperties);
            if (websiteOld != null && !websiteOld.equals(websiteNew)) {
                websiteOld.getHotelPropertiesList().remove(hotelProperties);
                websiteOld = em.merge(websiteOld);
            }
            if (websiteNew != null && !websiteNew.equals(websiteOld)) {
                websiteNew.getHotelPropertiesList().add(hotelProperties);
                websiteNew = em.merge(websiteNew);
            }
            if (propertiesOld != null && !propertiesOld.equals(propertiesNew)) {
                propertiesOld.getHotelPropertiesList().remove(hotelProperties);
                propertiesOld = em.merge(propertiesOld);
            }
            if (propertiesNew != null && !propertiesNew.equals(propertiesOld)) {
                propertiesNew.getHotelPropertiesList().add(hotelProperties);
                propertiesNew = em.merge(propertiesNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                HotelPropertiesPK id = hotelProperties.getHotelPropertiesPK();
                if (findHotelProperties(id) == null) {
                    throw new NonexistentEntityException("The hotelProperties with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(HotelPropertiesPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HotelProperties hotelProperties;
            try {
                hotelProperties = em.getReference(HotelProperties.class, id);
                hotelProperties.getHotelPropertiesPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hotelProperties with id " + id + " no longer exists.", enfe);
            }
            Website website = hotelProperties.getWebsite();
            if (website != null) {
                website.getHotelPropertiesList().remove(hotelProperties);
                website = em.merge(website);
            }
            Properties properties = hotelProperties.getProperties();
            if (properties != null) {
                properties.getHotelPropertiesList().remove(hotelProperties);
                properties = em.merge(properties);
            }
            em.remove(hotelProperties);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HotelProperties> findHotelPropertiesEntities() {
        return findHotelPropertiesEntities(true, -1, -1);
    }

    public List<HotelProperties> findHotelPropertiesEntities(int maxResults, int firstResult) {
        return findHotelPropertiesEntities(false, maxResults, firstResult);
    }

    private List<HotelProperties> findHotelPropertiesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HotelProperties.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public HotelProperties findHotelProperties(HotelPropertiesPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HotelProperties.class, id);
        } finally {
            em.close();
        }
    }

    public int getHotelPropertiesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HotelProperties> rt = cq.from(HotelProperties.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
