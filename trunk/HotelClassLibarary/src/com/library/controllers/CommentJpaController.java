/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.controllers;

import com.library.b.entities.Comment;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.library.b.entities.User;
import com.library.b.entities.Hotel;
import com.library.controllers.exceptions.NonexistentEntityException;
import com.library.controllers.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ThuHoa
 */
public class CommentJpaController implements Serializable {

    public CommentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comment comment) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userID = comment.getUserID();
            if (userID != null) {
                userID = em.getReference(userID.getClass(), userID.getUserId());
                comment.setUserID(userID);
            }
            Hotel hotelID = comment.getHotelID();
            if (hotelID != null) {
                hotelID = em.getReference(hotelID.getClass(), hotelID.getHotelID());
                comment.setHotelID(hotelID);
            }
            em.persist(comment);
            if (userID != null) {
                userID.getCommentList().add(comment);
                userID = em.merge(userID);
            }
            if (hotelID != null) {
                hotelID.getCommentList().add(comment);
                hotelID = em.merge(hotelID);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComment(comment.getCommentID()) != null) {
                throw new PreexistingEntityException("Comment " + comment + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comment comment) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comment persistentComment = em.find(Comment.class, comment.getCommentID());
            User userIDOld = persistentComment.getUserID();
            User userIDNew = comment.getUserID();
            Hotel hotelIDOld = persistentComment.getHotelID();
            Hotel hotelIDNew = comment.getHotelID();
            if (userIDNew != null) {
                userIDNew = em.getReference(userIDNew.getClass(), userIDNew.getUserId());
                comment.setUserID(userIDNew);
            }
            if (hotelIDNew != null) {
                hotelIDNew = em.getReference(hotelIDNew.getClass(), hotelIDNew.getHotelID());
                comment.setHotelID(hotelIDNew);
            }
            comment = em.merge(comment);
            if (userIDOld != null && !userIDOld.equals(userIDNew)) {
                userIDOld.getCommentList().remove(comment);
                userIDOld = em.merge(userIDOld);
            }
            if (userIDNew != null && !userIDNew.equals(userIDOld)) {
                userIDNew.getCommentList().add(comment);
                userIDNew = em.merge(userIDNew);
            }
            if (hotelIDOld != null && !hotelIDOld.equals(hotelIDNew)) {
                hotelIDOld.getCommentList().remove(comment);
                hotelIDOld = em.merge(hotelIDOld);
            }
            if (hotelIDNew != null && !hotelIDNew.equals(hotelIDOld)) {
                hotelIDNew.getCommentList().add(comment);
                hotelIDNew = em.merge(hotelIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comment.getCommentID();
                if (findComment(id) == null) {
                    throw new NonexistentEntityException("The comment with id " + id + " no longer exists.");
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
            Comment comment;
            try {
                comment = em.getReference(Comment.class, id);
                comment.getCommentID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comment with id " + id + " no longer exists.", enfe);
            }
            User userID = comment.getUserID();
            if (userID != null) {
                userID.getCommentList().remove(comment);
                userID = em.merge(userID);
            }
            Hotel hotelID = comment.getHotelID();
            if (hotelID != null) {
                hotelID.getCommentList().remove(comment);
                hotelID = em.merge(hotelID);
            }
            em.remove(comment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comment> findCommentEntities() {
        return findCommentEntities(true, -1, -1);
    }

    public List<Comment> findCommentEntities(int maxResults, int firstResult) {
        return findCommentEntities(false, maxResults, firstResult);
    }

    private List<Comment> findCommentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comment.class));
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

    public Comment findComment(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comment.class, id);
        } finally {
            em.close();
        }
    }

    public int getCommentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comment> rt = cq.from(Comment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
