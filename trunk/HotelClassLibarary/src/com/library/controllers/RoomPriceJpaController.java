/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.library.b.entities.Room;
import com.library.b.entities.RoomPrice;
import com.library.controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ThuHoa
 */
public class RoomPriceJpaController implements Serializable {

    public RoomPriceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RoomPrice roomPrice) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Room roomID = roomPrice.getRoomID();
            if (roomID != null) {
                roomID = em.getReference(roomID.getClass(), roomID.getRoomID());
                roomPrice.setRoomID(roomID);
            }
            em.persist(roomPrice);
            if (roomID != null) {
                roomID.getRoomPriceList().add(roomPrice);
                roomID = em.merge(roomID);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RoomPrice roomPrice) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RoomPrice persistentRoomPrice = em.find(RoomPrice.class, roomPrice.getRoomPriceID());
            Room roomIDOld = persistentRoomPrice.getRoomID();
            Room roomIDNew = roomPrice.getRoomID();
            if (roomIDNew != null) {
                roomIDNew = em.getReference(roomIDNew.getClass(), roomIDNew.getRoomID());
                roomPrice.setRoomID(roomIDNew);
            }
            roomPrice = em.merge(roomPrice);
            if (roomIDOld != null && !roomIDOld.equals(roomIDNew)) {
                roomIDOld.getRoomPriceList().remove(roomPrice);
                roomIDOld = em.merge(roomIDOld);
            }
            if (roomIDNew != null && !roomIDNew.equals(roomIDOld)) {
                roomIDNew.getRoomPriceList().add(roomPrice);
                roomIDNew = em.merge(roomIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = roomPrice.getRoomPriceID();
                if (findRoomPrice(id) == null) {
                    throw new NonexistentEntityException("The roomPrice with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RoomPrice roomPrice;
            try {
                roomPrice = em.getReference(RoomPrice.class, id);
                roomPrice.getRoomPriceID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The roomPrice with id " + id + " no longer exists.", enfe);
            }
            Room roomID = roomPrice.getRoomID();
            if (roomID != null) {
                roomID.getRoomPriceList().remove(roomPrice);
                roomID = em.merge(roomID);
            }
            em.remove(roomPrice);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RoomPrice> findRoomPriceEntities() {
        return findRoomPriceEntities(true, -1, -1);
    }

    public List<RoomPrice> findRoomPriceEntities(int maxResults, int firstResult) {
        return findRoomPriceEntities(false, maxResults, firstResult);
    }

    private List<RoomPrice> findRoomPriceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RoomPrice.class));
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

    public RoomPrice findRoomPrice(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RoomPrice.class, id);
        } finally {
            em.close();
        }
    }

    public int getRoomPriceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RoomPrice> rt = cq.from(RoomPrice.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
