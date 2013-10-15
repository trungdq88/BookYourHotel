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
import jpa.entities.Hotel;
import jpa.entities.Room;

/**
 *
 * @author Quang Trung
 */
public class RoomJpaController implements Serializable {

    public RoomJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Room room) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Hotel hotelID = room.getHotelID();
            if (hotelID != null) {
                hotelID = em.getReference(hotelID.getClass(), hotelID.getHotelID());
                room.setHotelID(hotelID);
            }
            em.persist(room);
            if (hotelID != null) {
                hotelID.getRoomList().add(room);
                hotelID = em.merge(hotelID);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findRoom(room.getRoomID()) != null) {
                throw new PreexistingEntityException("Room " + room + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Room room) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Room persistentRoom = em.find(Room.class, room.getRoomID());
            Hotel hotelIDOld = persistentRoom.getHotelID();
            Hotel hotelIDNew = room.getHotelID();
            if (hotelIDNew != null) {
                hotelIDNew = em.getReference(hotelIDNew.getClass(), hotelIDNew.getHotelID());
                room.setHotelID(hotelIDNew);
            }
            room = em.merge(room);
            if (hotelIDOld != null && !hotelIDOld.equals(hotelIDNew)) {
                hotelIDOld.getRoomList().remove(room);
                hotelIDOld = em.merge(hotelIDOld);
            }
            if (hotelIDNew != null && !hotelIDNew.equals(hotelIDOld)) {
                hotelIDNew.getRoomList().add(room);
                hotelIDNew = em.merge(hotelIDNew);
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
                Integer id = room.getRoomID();
                if (findRoom(id) == null) {
                    throw new NonexistentEntityException("The room with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Room room;
            try {
                room = em.getReference(Room.class, id);
                room.getRoomID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The room with id " + id + " no longer exists.", enfe);
            }
            Hotel hotelID = room.getHotelID();
            if (hotelID != null) {
                hotelID.getRoomList().remove(room);
                hotelID = em.merge(hotelID);
            }
            em.remove(room);
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

    public List<Room> findRoomEntities() {
        return findRoomEntities(true, -1, -1);
    }

    public List<Room> findRoomEntities(int maxResults, int firstResult) {
        return findRoomEntities(false, maxResults, firstResult);
    }

    private List<Room> findRoomEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Room.class));
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

    public Room findRoom(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Room.class, id);
        } finally {
            em.close();
        }
    }

    public int getRoomCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Room> rt = cq.from(Room.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
