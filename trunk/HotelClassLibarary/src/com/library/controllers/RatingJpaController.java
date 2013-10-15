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
import com.library.b.entities.User;
import com.library.b.entities.Hotel;
import com.library.b.entities.Rating;
import com.library.b.entities.RatingPK;
import com.library.controllers.exceptions.NonexistentEntityException;
import com.library.controllers.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ThuHoa
 */
public class RatingJpaController implements Serializable {

    public RatingJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rating rating) throws PreexistingEntityException, Exception {
        if (rating.getRatingPK() == null) {
            rating.setRatingPK(new RatingPK());
        }
        rating.getRatingPK().setUserID(rating.getUser().getUserId());
        rating.getRatingPK().setHotelID(rating.getHotel().getHotelID());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user = rating.getUser();
            if (user != null) {
                user = em.getReference(user.getClass(), user.getUserId());
                rating.setUser(user);
            }
            Hotel hotel = rating.getHotel();
            if (hotel != null) {
                hotel = em.getReference(hotel.getClass(), hotel.getHotelID());
                rating.setHotel(hotel);
            }
            em.persist(rating);
            if (user != null) {
                user.getRatingList().add(rating);
                user = em.merge(user);
            }
            if (hotel != null) {
                hotel.getRatingList().add(rating);
                hotel = em.merge(hotel);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRating(rating.getRatingPK()) != null) {
                throw new PreexistingEntityException("Rating " + rating + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rating rating) throws NonexistentEntityException, Exception {
        rating.getRatingPK().setUserID(rating.getUser().getUserId());
        rating.getRatingPK().setHotelID(rating.getHotel().getHotelID());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rating persistentRating = em.find(Rating.class, rating.getRatingPK());
            User userOld = persistentRating.getUser();
            User userNew = rating.getUser();
            Hotel hotelOld = persistentRating.getHotel();
            Hotel hotelNew = rating.getHotel();
            if (userNew != null) {
                userNew = em.getReference(userNew.getClass(), userNew.getUserId());
                rating.setUser(userNew);
            }
            if (hotelNew != null) {
                hotelNew = em.getReference(hotelNew.getClass(), hotelNew.getHotelID());
                rating.setHotel(hotelNew);
            }
            rating = em.merge(rating);
            if (userOld != null && !userOld.equals(userNew)) {
                userOld.getRatingList().remove(rating);
                userOld = em.merge(userOld);
            }
            if (userNew != null && !userNew.equals(userOld)) {
                userNew.getRatingList().add(rating);
                userNew = em.merge(userNew);
            }
            if (hotelOld != null && !hotelOld.equals(hotelNew)) {
                hotelOld.getRatingList().remove(rating);
                hotelOld = em.merge(hotelOld);
            }
            if (hotelNew != null && !hotelNew.equals(hotelOld)) {
                hotelNew.getRatingList().add(rating);
                hotelNew = em.merge(hotelNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                RatingPK id = rating.getRatingPK();
                if (findRating(id) == null) {
                    throw new NonexistentEntityException("The rating with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(RatingPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rating rating;
            try {
                rating = em.getReference(Rating.class, id);
                rating.getRatingPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rating with id " + id + " no longer exists.", enfe);
            }
            User user = rating.getUser();
            if (user != null) {
                user.getRatingList().remove(rating);
                user = em.merge(user);
            }
            Hotel hotel = rating.getHotel();
            if (hotel != null) {
                hotel.getRatingList().remove(rating);
                hotel = em.merge(hotel);
            }
            em.remove(rating);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rating> findRatingEntities() {
        return findRatingEntities(true, -1, -1);
    }

    public List<Rating> findRatingEntities(int maxResults, int firstResult) {
        return findRatingEntities(false, maxResults, firstResult);
    }

    private List<Rating> findRatingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rating.class));
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

    public Rating findRating(RatingPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rating.class, id);
        } finally {
            em.close();
        }
    }

    public int getRatingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rating> rt = cq.from(Rating.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
