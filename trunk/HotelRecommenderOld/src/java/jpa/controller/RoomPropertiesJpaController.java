/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import jpa.controller.exceptions.NonexistentEntityException;
import jpa.controller.exceptions.PreexistingEntityException;
import jpa.controller.exceptions.RollbackFailureException;
import jpa.entities.Website;
import jpa.entities.Properties;
import jpa.entities.RoomProperties;
import jpa.entities.RoomPropertiesPK;

/**
 *
 * @author Quang Trung
 */
public class RoomPropertiesJpaController implements Serializable {

    public RoomPropertiesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RoomProperties roomProperties) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (roomProperties.getRoomPropertiesPK() == null) {
            roomProperties.setRoomPropertiesPK(new RoomPropertiesPK());
        }
        roomProperties.getRoomPropertiesPK().setPropertyID(roomProperties.getProperties().getPropertyID());
        roomProperties.getRoomPropertiesPK().setWebsiteID(roomProperties.getWebsite().getWebsiteID());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Website website = roomProperties.getWebsite();
            if (website != null) {
                website = em.getReference(website.getClass(), website.getWebsiteID());
                roomProperties.setWebsite(website);
            }
            Properties properties = roomProperties.getProperties();
            if (properties != null) {
                properties = em.getReference(properties.getClass(), properties.getPropertyID());
                roomProperties.setProperties(properties);
            }
            em.persist(roomProperties);
            if (website != null) {
                website.getRoomPropertiesList().add(roomProperties);
                website = em.merge(website);
            }
            if (properties != null) {
                properties.getRoomPropertiesList().add(roomProperties);
                properties = em.merge(properties);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findRoomProperties(roomProperties.getRoomPropertiesPK()) != null) {
                throw new PreexistingEntityException("RoomProperties " + roomProperties + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RoomProperties roomProperties) throws NonexistentEntityException, RollbackFailureException, Exception {
        roomProperties.getRoomPropertiesPK().setPropertyID(roomProperties.getProperties().getPropertyID());
        roomProperties.getRoomPropertiesPK().setWebsiteID(roomProperties.getWebsite().getWebsiteID());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RoomProperties persistentRoomProperties = em.find(RoomProperties.class, roomProperties.getRoomPropertiesPK());
            Website websiteOld = persistentRoomProperties.getWebsite();
            Website websiteNew = roomProperties.getWebsite();
            Properties propertiesOld = persistentRoomProperties.getProperties();
            Properties propertiesNew = roomProperties.getProperties();
            if (websiteNew != null) {
                websiteNew = em.getReference(websiteNew.getClass(), websiteNew.getWebsiteID());
                roomProperties.setWebsite(websiteNew);
            }
            if (propertiesNew != null) {
                propertiesNew = em.getReference(propertiesNew.getClass(), propertiesNew.getPropertyID());
                roomProperties.setProperties(propertiesNew);
            }
            roomProperties = em.merge(roomProperties);
            if (websiteOld != null && !websiteOld.equals(websiteNew)) {
                websiteOld.getRoomPropertiesList().remove(roomProperties);
                websiteOld = em.merge(websiteOld);
            }
            if (websiteNew != null && !websiteNew.equals(websiteOld)) {
                websiteNew.getRoomPropertiesList().add(roomProperties);
                websiteNew = em.merge(websiteNew);
            }
            if (propertiesOld != null && !propertiesOld.equals(propertiesNew)) {
                propertiesOld.getRoomPropertiesList().remove(roomProperties);
                propertiesOld = em.merge(propertiesOld);
            }
            if (propertiesNew != null && !propertiesNew.equals(propertiesOld)) {
                propertiesNew.getRoomPropertiesList().add(roomProperties);
                propertiesNew = em.merge(propertiesNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                RoomPropertiesPK id = roomProperties.getRoomPropertiesPK();
                if (findRoomProperties(id) == null) {
                    throw new NonexistentEntityException("The roomProperties with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(RoomPropertiesPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            RoomProperties roomProperties;
            try {
                roomProperties = em.getReference(RoomProperties.class, id);
                roomProperties.getRoomPropertiesPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The roomProperties with id " + id + " no longer exists.", enfe);
            }
            Website website = roomProperties.getWebsite();
            if (website != null) {
                website.getRoomPropertiesList().remove(roomProperties);
                website = em.merge(website);
            }
            Properties properties = roomProperties.getProperties();
            if (properties != null) {
                properties.getRoomPropertiesList().remove(roomProperties);
                properties = em.merge(properties);
            }
            em.remove(roomProperties);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RoomProperties> findRoomPropertiesEntities() {
        return findRoomPropertiesEntities(true, -1, -1);
    }

    public List<RoomProperties> findRoomPropertiesEntities(int maxResults, int firstResult) {
        return findRoomPropertiesEntities(false, maxResults, firstResult);
    }

    private List<RoomProperties> findRoomPropertiesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RoomProperties.class));
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

    public RoomProperties findRoomProperties(RoomPropertiesPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RoomProperties.class, id);
        } finally {
            em.close();
        }
    }

    public int getRoomPropertiesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RoomProperties> rt = cq.from(RoomProperties.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
