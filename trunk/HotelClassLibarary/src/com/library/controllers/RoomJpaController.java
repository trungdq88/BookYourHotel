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
import com.library.b.entities.Hotel;
import com.library.b.entities.Room;
import com.library.b.entities.RoomPrice;
import com.library.controllers.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ThuHoa
 */
public class RoomJpaController implements Serializable {

    public RoomJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Room room) {
        if (room.getRoomPriceList() == null) {
            room.setRoomPriceList(new ArrayList<RoomPrice>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hotel hotelID = room.getHotelID();
            if (hotelID != null) {
                hotelID = em.getReference(hotelID.getClass(), hotelID.getHotelID());
                room.setHotelID(hotelID);
            }
            List<RoomPrice> attachedRoomPriceList = new ArrayList<RoomPrice>();
            for (RoomPrice roomPriceListRoomPriceToAttach : room.getRoomPriceList()) {
                roomPriceListRoomPriceToAttach = em.getReference(roomPriceListRoomPriceToAttach.getClass(), roomPriceListRoomPriceToAttach.getRoomPriceID());
                attachedRoomPriceList.add(roomPriceListRoomPriceToAttach);
            }
            room.setRoomPriceList(attachedRoomPriceList);
            em.persist(room);
            if (hotelID != null) {
                hotelID.getRoomList().add(room);
                hotelID = em.merge(hotelID);
            }
            for (RoomPrice roomPriceListRoomPrice : room.getRoomPriceList()) {
                Room oldRoomIDOfRoomPriceListRoomPrice = roomPriceListRoomPrice.getRoomID();
                roomPriceListRoomPrice.setRoomID(room);
                roomPriceListRoomPrice = em.merge(roomPriceListRoomPrice);
                if (oldRoomIDOfRoomPriceListRoomPrice != null) {
                    oldRoomIDOfRoomPriceListRoomPrice.getRoomPriceList().remove(roomPriceListRoomPrice);
                    oldRoomIDOfRoomPriceListRoomPrice = em.merge(oldRoomIDOfRoomPriceListRoomPrice);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Room room) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Room persistentRoom = em.find(Room.class, room.getRoomID());
            Hotel hotelIDOld = persistentRoom.getHotelID();
            Hotel hotelIDNew = room.getHotelID();
            List<RoomPrice> roomPriceListOld = persistentRoom.getRoomPriceList();
            List<RoomPrice> roomPriceListNew = room.getRoomPriceList();
            if (hotelIDNew != null) {
                hotelIDNew = em.getReference(hotelIDNew.getClass(), hotelIDNew.getHotelID());
                room.setHotelID(hotelIDNew);
            }
            List<RoomPrice> attachedRoomPriceListNew = new ArrayList<RoomPrice>();
            for (RoomPrice roomPriceListNewRoomPriceToAttach : roomPriceListNew) {
                roomPriceListNewRoomPriceToAttach = em.getReference(roomPriceListNewRoomPriceToAttach.getClass(), roomPriceListNewRoomPriceToAttach.getRoomPriceID());
                attachedRoomPriceListNew.add(roomPriceListNewRoomPriceToAttach);
            }
            roomPriceListNew = attachedRoomPriceListNew;
            room.setRoomPriceList(roomPriceListNew);
            room = em.merge(room);
            if (hotelIDOld != null && !hotelIDOld.equals(hotelIDNew)) {
                hotelIDOld.getRoomList().remove(room);
                hotelIDOld = em.merge(hotelIDOld);
            }
            if (hotelIDNew != null && !hotelIDNew.equals(hotelIDOld)) {
                hotelIDNew.getRoomList().add(room);
                hotelIDNew = em.merge(hotelIDNew);
            }
            for (RoomPrice roomPriceListOldRoomPrice : roomPriceListOld) {
                if (!roomPriceListNew.contains(roomPriceListOldRoomPrice)) {
                    roomPriceListOldRoomPrice.setRoomID(null);
                    roomPriceListOldRoomPrice = em.merge(roomPriceListOldRoomPrice);
                }
            }
            for (RoomPrice roomPriceListNewRoomPrice : roomPriceListNew) {
                if (!roomPriceListOld.contains(roomPriceListNewRoomPrice)) {
                    Room oldRoomIDOfRoomPriceListNewRoomPrice = roomPriceListNewRoomPrice.getRoomID();
                    roomPriceListNewRoomPrice.setRoomID(room);
                    roomPriceListNewRoomPrice = em.merge(roomPriceListNewRoomPrice);
                    if (oldRoomIDOfRoomPriceListNewRoomPrice != null && !oldRoomIDOfRoomPriceListNewRoomPrice.equals(room)) {
                        oldRoomIDOfRoomPriceListNewRoomPrice.getRoomPriceList().remove(roomPriceListNewRoomPrice);
                        oldRoomIDOfRoomPriceListNewRoomPrice = em.merge(oldRoomIDOfRoomPriceListNewRoomPrice);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
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

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
            List<RoomPrice> roomPriceList = room.getRoomPriceList();
            for (RoomPrice roomPriceListRoomPrice : roomPriceList) {
                roomPriceListRoomPrice.setRoomID(null);
                roomPriceListRoomPrice = em.merge(roomPriceListRoomPrice);
            }
            em.remove(room);
            em.getTransaction().commit();
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
