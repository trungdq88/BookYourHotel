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
import jpa.entities.Region;
import jpa.entities.Room;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpa.controller.exceptions.NonexistentEntityException;
import jpa.controller.exceptions.PreexistingEntityException;
import jpa.controller.exceptions.RollbackFailureException;
import jpa.entities.Hotel;

/**
 *
 * @author Quang Trung
 */
public class HotelJpaController implements Serializable {

    public HotelJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Hotel hotel) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (hotel.getRoomList() == null) {
            hotel.setRoomList(new ArrayList<Room>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Region regionID = hotel.getRegionID();
            if (regionID != null) {
                regionID = em.getReference(regionID.getClass(), regionID.getRegionID());
                hotel.setRegionID(regionID);
            }
            List<Room> attachedRoomList = new ArrayList<Room>();
            for (Room roomListRoomToAttach : hotel.getRoomList()) {
                roomListRoomToAttach = em.getReference(roomListRoomToAttach.getClass(), roomListRoomToAttach.getRoomID());
                attachedRoomList.add(roomListRoomToAttach);
            }
            hotel.setRoomList(attachedRoomList);
            em.persist(hotel);
            if (regionID != null) {
                regionID.getHotelList().add(hotel);
                regionID = em.merge(regionID);
            }
            for (Room roomListRoom : hotel.getRoomList()) {
                Hotel oldHotelIDOfRoomListRoom = roomListRoom.getHotelID();
                roomListRoom.setHotelID(hotel);
                roomListRoom = em.merge(roomListRoom);
                if (oldHotelIDOfRoomListRoom != null) {
                    oldHotelIDOfRoomListRoom.getRoomList().remove(roomListRoom);
                    oldHotelIDOfRoomListRoom = em.merge(oldHotelIDOfRoomListRoom);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findHotel(hotel.getHotelID()) != null) {
                throw new PreexistingEntityException("Hotel " + hotel + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Hotel hotel) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Hotel persistentHotel = em.find(Hotel.class, hotel.getHotelID());
            Region regionIDOld = persistentHotel.getRegionID();
            Region regionIDNew = hotel.getRegionID();
            List<Room> roomListOld = persistentHotel.getRoomList();
            List<Room> roomListNew = hotel.getRoomList();
            if (regionIDNew != null) {
                regionIDNew = em.getReference(regionIDNew.getClass(), regionIDNew.getRegionID());
                hotel.setRegionID(regionIDNew);
            }
            List<Room> attachedRoomListNew = new ArrayList<Room>();
            for (Room roomListNewRoomToAttach : roomListNew) {
                roomListNewRoomToAttach = em.getReference(roomListNewRoomToAttach.getClass(), roomListNewRoomToAttach.getRoomID());
                attachedRoomListNew.add(roomListNewRoomToAttach);
            }
            roomListNew = attachedRoomListNew;
            hotel.setRoomList(roomListNew);
            hotel = em.merge(hotel);
            if (regionIDOld != null && !regionIDOld.equals(regionIDNew)) {
                regionIDOld.getHotelList().remove(hotel);
                regionIDOld = em.merge(regionIDOld);
            }
            if (regionIDNew != null && !regionIDNew.equals(regionIDOld)) {
                regionIDNew.getHotelList().add(hotel);
                regionIDNew = em.merge(regionIDNew);
            }
            for (Room roomListOldRoom : roomListOld) {
                if (!roomListNew.contains(roomListOldRoom)) {
                    roomListOldRoom.setHotelID(null);
                    roomListOldRoom = em.merge(roomListOldRoom);
                }
            }
            for (Room roomListNewRoom : roomListNew) {
                if (!roomListOld.contains(roomListNewRoom)) {
                    Hotel oldHotelIDOfRoomListNewRoom = roomListNewRoom.getHotelID();
                    roomListNewRoom.setHotelID(hotel);
                    roomListNewRoom = em.merge(roomListNewRoom);
                    if (oldHotelIDOfRoomListNewRoom != null && !oldHotelIDOfRoomListNewRoom.equals(hotel)) {
                        oldHotelIDOfRoomListNewRoom.getRoomList().remove(roomListNewRoom);
                        oldHotelIDOfRoomListNewRoom = em.merge(oldHotelIDOfRoomListNewRoom);
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
                Integer id = hotel.getHotelID();
                if (findHotel(id) == null) {
                    throw new NonexistentEntityException("The hotel with id " + id + " no longer exists.");
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
            Hotel hotel;
            try {
                hotel = em.getReference(Hotel.class, id);
                hotel.getHotelID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hotel with id " + id + " no longer exists.", enfe);
            }
            Region regionID = hotel.getRegionID();
            if (regionID != null) {
                regionID.getHotelList().remove(hotel);
                regionID = em.merge(regionID);
            }
            List<Room> roomList = hotel.getRoomList();
            for (Room roomListRoom : roomList) {
                roomListRoom.setHotelID(null);
                roomListRoom = em.merge(roomListRoom);
            }
            em.remove(hotel);
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

    public List<Hotel> findHotelEntities() {
        return findHotelEntities(true, -1, -1);
    }

    public List<Hotel> findHotelEntities(int maxResults, int firstResult) {
        return findHotelEntities(false, maxResults, firstResult);
    }

    private List<Hotel> findHotelEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Hotel.class));
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

    public Hotel findHotel(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Hotel.class, id);
        } finally {
            em.close();
        }
    }

    public int getHotelCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Hotel> rt = cq.from(Hotel.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
