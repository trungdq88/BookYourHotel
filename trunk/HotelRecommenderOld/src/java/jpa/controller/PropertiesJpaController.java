/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa.entities.RoomProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpa.controller.exceptions.IllegalOrphanException;
import jpa.controller.exceptions.NonexistentEntityException;
import jpa.controller.exceptions.PreexistingEntityException;
import jpa.controller.exceptions.RollbackFailureException;
import jpa.entities.HotelProperties;
import jpa.entities.Properties;

/**
 *
 * @author Quang Trung
 */
public class PropertiesJpaController implements Serializable {

    public PropertiesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Properties properties) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (properties.getRoomPropertiesList() == null) {
            properties.setRoomPropertiesList(new ArrayList<RoomProperties>());
        }
        if (properties.getHotelPropertiesList() == null) {
            properties.setHotelPropertiesList(new ArrayList<HotelProperties>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<RoomProperties> attachedRoomPropertiesList = new ArrayList<RoomProperties>();
            for (RoomProperties roomPropertiesListRoomPropertiesToAttach : properties.getRoomPropertiesList()) {
                roomPropertiesListRoomPropertiesToAttach = em.getReference(roomPropertiesListRoomPropertiesToAttach.getClass(), roomPropertiesListRoomPropertiesToAttach.getRoomPropertiesPK());
                attachedRoomPropertiesList.add(roomPropertiesListRoomPropertiesToAttach);
            }
            properties.setRoomPropertiesList(attachedRoomPropertiesList);
            List<HotelProperties> attachedHotelPropertiesList = new ArrayList<HotelProperties>();
            for (HotelProperties hotelPropertiesListHotelPropertiesToAttach : properties.getHotelPropertiesList()) {
                hotelPropertiesListHotelPropertiesToAttach = em.getReference(hotelPropertiesListHotelPropertiesToAttach.getClass(), hotelPropertiesListHotelPropertiesToAttach.getHotelPropertiesPK());
                attachedHotelPropertiesList.add(hotelPropertiesListHotelPropertiesToAttach);
            }
            properties.setHotelPropertiesList(attachedHotelPropertiesList);
            em.persist(properties);
            for (RoomProperties roomPropertiesListRoomProperties : properties.getRoomPropertiesList()) {
                Properties oldPropertiesOfRoomPropertiesListRoomProperties = roomPropertiesListRoomProperties.getProperties();
                roomPropertiesListRoomProperties.setProperties(properties);
                roomPropertiesListRoomProperties = em.merge(roomPropertiesListRoomProperties);
                if (oldPropertiesOfRoomPropertiesListRoomProperties != null) {
                    oldPropertiesOfRoomPropertiesListRoomProperties.getRoomPropertiesList().remove(roomPropertiesListRoomProperties);
                    oldPropertiesOfRoomPropertiesListRoomProperties = em.merge(oldPropertiesOfRoomPropertiesListRoomProperties);
                }
            }
            for (HotelProperties hotelPropertiesListHotelProperties : properties.getHotelPropertiesList()) {
                Properties oldPropertiesOfHotelPropertiesListHotelProperties = hotelPropertiesListHotelProperties.getProperties();
                hotelPropertiesListHotelProperties.setProperties(properties);
                hotelPropertiesListHotelProperties = em.merge(hotelPropertiesListHotelProperties);
                if (oldPropertiesOfHotelPropertiesListHotelProperties != null) {
                    oldPropertiesOfHotelPropertiesListHotelProperties.getHotelPropertiesList().remove(hotelPropertiesListHotelProperties);
                    oldPropertiesOfHotelPropertiesListHotelProperties = em.merge(oldPropertiesOfHotelPropertiesListHotelProperties);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findProperties(properties.getPropertyID()) != null) {
                throw new PreexistingEntityException("Properties " + properties + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Properties properties) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Properties persistentProperties = em.find(Properties.class, properties.getPropertyID());
            List<RoomProperties> roomPropertiesListOld = persistentProperties.getRoomPropertiesList();
            List<RoomProperties> roomPropertiesListNew = properties.getRoomPropertiesList();
            List<HotelProperties> hotelPropertiesListOld = persistentProperties.getHotelPropertiesList();
            List<HotelProperties> hotelPropertiesListNew = properties.getHotelPropertiesList();
            List<String> illegalOrphanMessages = null;
            for (RoomProperties roomPropertiesListOldRoomProperties : roomPropertiesListOld) {
                if (!roomPropertiesListNew.contains(roomPropertiesListOldRoomProperties)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RoomProperties " + roomPropertiesListOldRoomProperties + " since its properties field is not nullable.");
                }
            }
            for (HotelProperties hotelPropertiesListOldHotelProperties : hotelPropertiesListOld) {
                if (!hotelPropertiesListNew.contains(hotelPropertiesListOldHotelProperties)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HotelProperties " + hotelPropertiesListOldHotelProperties + " since its properties field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<RoomProperties> attachedRoomPropertiesListNew = new ArrayList<RoomProperties>();
            for (RoomProperties roomPropertiesListNewRoomPropertiesToAttach : roomPropertiesListNew) {
                roomPropertiesListNewRoomPropertiesToAttach = em.getReference(roomPropertiesListNewRoomPropertiesToAttach.getClass(), roomPropertiesListNewRoomPropertiesToAttach.getRoomPropertiesPK());
                attachedRoomPropertiesListNew.add(roomPropertiesListNewRoomPropertiesToAttach);
            }
            roomPropertiesListNew = attachedRoomPropertiesListNew;
            properties.setRoomPropertiesList(roomPropertiesListNew);
            List<HotelProperties> attachedHotelPropertiesListNew = new ArrayList<HotelProperties>();
            for (HotelProperties hotelPropertiesListNewHotelPropertiesToAttach : hotelPropertiesListNew) {
                hotelPropertiesListNewHotelPropertiesToAttach = em.getReference(hotelPropertiesListNewHotelPropertiesToAttach.getClass(), hotelPropertiesListNewHotelPropertiesToAttach.getHotelPropertiesPK());
                attachedHotelPropertiesListNew.add(hotelPropertiesListNewHotelPropertiesToAttach);
            }
            hotelPropertiesListNew = attachedHotelPropertiesListNew;
            properties.setHotelPropertiesList(hotelPropertiesListNew);
            properties = em.merge(properties);
            for (RoomProperties roomPropertiesListNewRoomProperties : roomPropertiesListNew) {
                if (!roomPropertiesListOld.contains(roomPropertiesListNewRoomProperties)) {
                    Properties oldPropertiesOfRoomPropertiesListNewRoomProperties = roomPropertiesListNewRoomProperties.getProperties();
                    roomPropertiesListNewRoomProperties.setProperties(properties);
                    roomPropertiesListNewRoomProperties = em.merge(roomPropertiesListNewRoomProperties);
                    if (oldPropertiesOfRoomPropertiesListNewRoomProperties != null && !oldPropertiesOfRoomPropertiesListNewRoomProperties.equals(properties)) {
                        oldPropertiesOfRoomPropertiesListNewRoomProperties.getRoomPropertiesList().remove(roomPropertiesListNewRoomProperties);
                        oldPropertiesOfRoomPropertiesListNewRoomProperties = em.merge(oldPropertiesOfRoomPropertiesListNewRoomProperties);
                    }
                }
            }
            for (HotelProperties hotelPropertiesListNewHotelProperties : hotelPropertiesListNew) {
                if (!hotelPropertiesListOld.contains(hotelPropertiesListNewHotelProperties)) {
                    Properties oldPropertiesOfHotelPropertiesListNewHotelProperties = hotelPropertiesListNewHotelProperties.getProperties();
                    hotelPropertiesListNewHotelProperties.setProperties(properties);
                    hotelPropertiesListNewHotelProperties = em.merge(hotelPropertiesListNewHotelProperties);
                    if (oldPropertiesOfHotelPropertiesListNewHotelProperties != null && !oldPropertiesOfHotelPropertiesListNewHotelProperties.equals(properties)) {
                        oldPropertiesOfHotelPropertiesListNewHotelProperties.getHotelPropertiesList().remove(hotelPropertiesListNewHotelProperties);
                        oldPropertiesOfHotelPropertiesListNewHotelProperties = em.merge(oldPropertiesOfHotelPropertiesListNewHotelProperties);
                    }
                }
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
                Integer id = properties.getPropertyID();
                if (findProperties(id) == null) {
                    throw new NonexistentEntityException("The properties with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Properties properties;
            try {
                properties = em.getReference(Properties.class, id);
                properties.getPropertyID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The properties with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RoomProperties> roomPropertiesListOrphanCheck = properties.getRoomPropertiesList();
            for (RoomProperties roomPropertiesListOrphanCheckRoomProperties : roomPropertiesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Properties (" + properties + ") cannot be destroyed since the RoomProperties " + roomPropertiesListOrphanCheckRoomProperties + " in its roomPropertiesList field has a non-nullable properties field.");
            }
            List<HotelProperties> hotelPropertiesListOrphanCheck = properties.getHotelPropertiesList();
            for (HotelProperties hotelPropertiesListOrphanCheckHotelProperties : hotelPropertiesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Properties (" + properties + ") cannot be destroyed since the HotelProperties " + hotelPropertiesListOrphanCheckHotelProperties + " in its hotelPropertiesList field has a non-nullable properties field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(properties);
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

    public List<Properties> findPropertiesEntities() {
        return findPropertiesEntities(true, -1, -1);
    }

    public List<Properties> findPropertiesEntities(int maxResults, int firstResult) {
        return findPropertiesEntities(false, maxResults, firstResult);
    }

    private List<Properties> findPropertiesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Properties.class));
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

    public Properties findProperties(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Properties.class, id);
        } finally {
            em.close();
        }
    }

    public int getPropertiesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Properties> rt = cq.from(Properties.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
