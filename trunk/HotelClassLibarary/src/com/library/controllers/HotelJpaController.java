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
import com.library.b.entities.Ward;
import com.library.b.entities.Province;
import com.library.b.entities.District;
import com.library.b.entities.Room;
import java.util.ArrayList;
import java.util.List;
import com.library.b.entities.Rating;
import com.library.b.entities.Comment;
import com.library.b.entities.Hotel;
import com.library.controllers.exceptions.IllegalOrphanException;
import com.library.controllers.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ThuHoa
 */
public class HotelJpaController implements Serializable {

    public HotelJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Hotel hotel) {
        if (hotel.getRoomList() == null) {
            hotel.setRoomList(new ArrayList<Room>());
        }
        if (hotel.getRatingList() == null) {
            hotel.setRatingList(new ArrayList<Rating>());
        }
        if (hotel.getCommentList() == null) {
            hotel.setCommentList(new ArrayList<Comment>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ward wardid = hotel.getWardid();
            if (wardid != null) {
                wardid = em.getReference(wardid.getClass(), wardid.getWardid());
                hotel.setWardid(wardid);
            }
            Province provinceid = hotel.getProvinceid();
            if (provinceid != null) {
                provinceid = em.getReference(provinceid.getClass(), provinceid.getProvinceid());
                hotel.setProvinceid(provinceid);
            }
            District districtid = hotel.getDistrictid();
            if (districtid != null) {
                districtid = em.getReference(districtid.getClass(), districtid.getDistrictid());
                hotel.setDistrictid(districtid);
            }
            List<Room> attachedRoomList = new ArrayList<Room>();
            for (Room roomListRoomToAttach : hotel.getRoomList()) {
                roomListRoomToAttach = em.getReference(roomListRoomToAttach.getClass(), roomListRoomToAttach.getRoomID());
                attachedRoomList.add(roomListRoomToAttach);
            }
            hotel.setRoomList(attachedRoomList);
            List<Rating> attachedRatingList = new ArrayList<Rating>();
            for (Rating ratingListRatingToAttach : hotel.getRatingList()) {
                ratingListRatingToAttach = em.getReference(ratingListRatingToAttach.getClass(), ratingListRatingToAttach.getRatingPK());
                attachedRatingList.add(ratingListRatingToAttach);
            }
            hotel.setRatingList(attachedRatingList);
            List<Comment> attachedCommentList = new ArrayList<Comment>();
            for (Comment commentListCommentToAttach : hotel.getCommentList()) {
                commentListCommentToAttach = em.getReference(commentListCommentToAttach.getClass(), commentListCommentToAttach.getCommentID());
                attachedCommentList.add(commentListCommentToAttach);
            }
            hotel.setCommentList(attachedCommentList);
            em.persist(hotel);
            if (wardid != null) {
                wardid.getHotelList().add(hotel);
                wardid = em.merge(wardid);
            }
            if (provinceid != null) {
                provinceid.getHotelList().add(hotel);
                provinceid = em.merge(provinceid);
            }
            if (districtid != null) {
                districtid.getHotelList().add(hotel);
                districtid = em.merge(districtid);
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
            for (Rating ratingListRating : hotel.getRatingList()) {
                Hotel oldHotelOfRatingListRating = ratingListRating.getHotel();
                ratingListRating.setHotel(hotel);
                ratingListRating = em.merge(ratingListRating);
                if (oldHotelOfRatingListRating != null) {
                    oldHotelOfRatingListRating.getRatingList().remove(ratingListRating);
                    oldHotelOfRatingListRating = em.merge(oldHotelOfRatingListRating);
                }
            }
            for (Comment commentListComment : hotel.getCommentList()) {
                Hotel oldHotelIDOfCommentListComment = commentListComment.getHotelID();
                commentListComment.setHotelID(hotel);
                commentListComment = em.merge(commentListComment);
                if (oldHotelIDOfCommentListComment != null) {
                    oldHotelIDOfCommentListComment.getCommentList().remove(commentListComment);
                    oldHotelIDOfCommentListComment = em.merge(oldHotelIDOfCommentListComment);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Hotel hotel) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hotel persistentHotel = em.find(Hotel.class, hotel.getHotelID());
            Ward wardidOld = persistentHotel.getWardid();
            Ward wardidNew = hotel.getWardid();
            Province provinceidOld = persistentHotel.getProvinceid();
            Province provinceidNew = hotel.getProvinceid();
            District districtidOld = persistentHotel.getDistrictid();
            District districtidNew = hotel.getDistrictid();
            List<Room> roomListOld = persistentHotel.getRoomList();
            List<Room> roomListNew = hotel.getRoomList();
            List<Rating> ratingListOld = persistentHotel.getRatingList();
            List<Rating> ratingListNew = hotel.getRatingList();
            List<Comment> commentListOld = persistentHotel.getCommentList();
            List<Comment> commentListNew = hotel.getCommentList();
            List<String> illegalOrphanMessages = null;
            for (Rating ratingListOldRating : ratingListOld) {
                if (!ratingListNew.contains(ratingListOldRating)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rating " + ratingListOldRating + " since its hotel field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (wardidNew != null) {
                wardidNew = em.getReference(wardidNew.getClass(), wardidNew.getWardid());
                hotel.setWardid(wardidNew);
            }
            if (provinceidNew != null) {
                provinceidNew = em.getReference(provinceidNew.getClass(), provinceidNew.getProvinceid());
                hotel.setProvinceid(provinceidNew);
            }
            if (districtidNew != null) {
                districtidNew = em.getReference(districtidNew.getClass(), districtidNew.getDistrictid());
                hotel.setDistrictid(districtidNew);
            }
            List<Room> attachedRoomListNew = new ArrayList<Room>();
            for (Room roomListNewRoomToAttach : roomListNew) {
                roomListNewRoomToAttach = em.getReference(roomListNewRoomToAttach.getClass(), roomListNewRoomToAttach.getRoomID());
                attachedRoomListNew.add(roomListNewRoomToAttach);
            }
            roomListNew = attachedRoomListNew;
            hotel.setRoomList(roomListNew);
            List<Rating> attachedRatingListNew = new ArrayList<Rating>();
            for (Rating ratingListNewRatingToAttach : ratingListNew) {
                ratingListNewRatingToAttach = em.getReference(ratingListNewRatingToAttach.getClass(), ratingListNewRatingToAttach.getRatingPK());
                attachedRatingListNew.add(ratingListNewRatingToAttach);
            }
            ratingListNew = attachedRatingListNew;
            hotel.setRatingList(ratingListNew);
            List<Comment> attachedCommentListNew = new ArrayList<Comment>();
            for (Comment commentListNewCommentToAttach : commentListNew) {
                commentListNewCommentToAttach = em.getReference(commentListNewCommentToAttach.getClass(), commentListNewCommentToAttach.getCommentID());
                attachedCommentListNew.add(commentListNewCommentToAttach);
            }
            commentListNew = attachedCommentListNew;
            hotel.setCommentList(commentListNew);
            hotel = em.merge(hotel);
            if (wardidOld != null && !wardidOld.equals(wardidNew)) {
                wardidOld.getHotelList().remove(hotel);
                wardidOld = em.merge(wardidOld);
            }
            if (wardidNew != null && !wardidNew.equals(wardidOld)) {
                wardidNew.getHotelList().add(hotel);
                wardidNew = em.merge(wardidNew);
            }
            if (provinceidOld != null && !provinceidOld.equals(provinceidNew)) {
                provinceidOld.getHotelList().remove(hotel);
                provinceidOld = em.merge(provinceidOld);
            }
            if (provinceidNew != null && !provinceidNew.equals(provinceidOld)) {
                provinceidNew.getHotelList().add(hotel);
                provinceidNew = em.merge(provinceidNew);
            }
            if (districtidOld != null && !districtidOld.equals(districtidNew)) {
                districtidOld.getHotelList().remove(hotel);
                districtidOld = em.merge(districtidOld);
            }
            if (districtidNew != null && !districtidNew.equals(districtidOld)) {
                districtidNew.getHotelList().add(hotel);
                districtidNew = em.merge(districtidNew);
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
            for (Rating ratingListNewRating : ratingListNew) {
                if (!ratingListOld.contains(ratingListNewRating)) {
                    Hotel oldHotelOfRatingListNewRating = ratingListNewRating.getHotel();
                    ratingListNewRating.setHotel(hotel);
                    ratingListNewRating = em.merge(ratingListNewRating);
                    if (oldHotelOfRatingListNewRating != null && !oldHotelOfRatingListNewRating.equals(hotel)) {
                        oldHotelOfRatingListNewRating.getRatingList().remove(ratingListNewRating);
                        oldHotelOfRatingListNewRating = em.merge(oldHotelOfRatingListNewRating);
                    }
                }
            }
            for (Comment commentListOldComment : commentListOld) {
                if (!commentListNew.contains(commentListOldComment)) {
                    commentListOldComment.setHotelID(null);
                    commentListOldComment = em.merge(commentListOldComment);
                }
            }
            for (Comment commentListNewComment : commentListNew) {
                if (!commentListOld.contains(commentListNewComment)) {
                    Hotel oldHotelIDOfCommentListNewComment = commentListNewComment.getHotelID();
                    commentListNewComment.setHotelID(hotel);
                    commentListNewComment = em.merge(commentListNewComment);
                    if (oldHotelIDOfCommentListNewComment != null && !oldHotelIDOfCommentListNewComment.equals(hotel)) {
                        oldHotelIDOfCommentListNewComment.getCommentList().remove(commentListNewComment);
                        oldHotelIDOfCommentListNewComment = em.merge(oldHotelIDOfCommentListNewComment);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hotel hotel;
            try {
                hotel = em.getReference(Hotel.class, id);
                hotel.getHotelID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hotel with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Rating> ratingListOrphanCheck = hotel.getRatingList();
            for (Rating ratingListOrphanCheckRating : ratingListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Hotel (" + hotel + ") cannot be destroyed since the Rating " + ratingListOrphanCheckRating + " in its ratingList field has a non-nullable hotel field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ward wardid = hotel.getWardid();
            if (wardid != null) {
                wardid.getHotelList().remove(hotel);
                wardid = em.merge(wardid);
            }
            Province provinceid = hotel.getProvinceid();
            if (provinceid != null) {
                provinceid.getHotelList().remove(hotel);
                provinceid = em.merge(provinceid);
            }
            District districtid = hotel.getDistrictid();
            if (districtid != null) {
                districtid.getHotelList().remove(hotel);
                districtid = em.merge(districtid);
            }
            List<Room> roomList = hotel.getRoomList();
            for (Room roomListRoom : roomList) {
                roomListRoom.setHotelID(null);
                roomListRoom = em.merge(roomListRoom);
            }
            List<Comment> commentList = hotel.getCommentList();
            for (Comment commentListComment : commentList) {
                commentListComment.setHotelID(null);
                commentListComment = em.merge(commentListComment);
            }
            em.remove(hotel);
            em.getTransaction().commit();
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
