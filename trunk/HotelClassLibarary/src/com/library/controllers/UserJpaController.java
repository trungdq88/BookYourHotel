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
import com.library.b.entities.Role;
import com.library.b.entities.Rating;
import java.util.ArrayList;
import java.util.List;
import com.library.b.entities.Comment;
import com.library.b.entities.User;
import com.library.controllers.exceptions.IllegalOrphanException;
import com.library.controllers.exceptions.NonexistentEntityException;
import com.library.controllers.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ThuHoa
 */
public class UserJpaController implements Serializable {

    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) throws PreexistingEntityException, Exception {
        if (user.getRatingList() == null) {
            user.setRatingList(new ArrayList<Rating>());
        }
        if (user.getCommentList() == null) {
            user.setCommentList(new ArrayList<Comment>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Role roleID = user.getRoleID();
            if (roleID != null) {
                roleID = em.getReference(roleID.getClass(), roleID.getRoleID());
                user.setRoleID(roleID);
            }
            List<Rating> attachedRatingList = new ArrayList<Rating>();
            for (Rating ratingListRatingToAttach : user.getRatingList()) {
                ratingListRatingToAttach = em.getReference(ratingListRatingToAttach.getClass(), ratingListRatingToAttach.getRatingPK());
                attachedRatingList.add(ratingListRatingToAttach);
            }
            user.setRatingList(attachedRatingList);
            List<Comment> attachedCommentList = new ArrayList<Comment>();
            for (Comment commentListCommentToAttach : user.getCommentList()) {
                commentListCommentToAttach = em.getReference(commentListCommentToAttach.getClass(), commentListCommentToAttach.getCommentID());
                attachedCommentList.add(commentListCommentToAttach);
            }
            user.setCommentList(attachedCommentList);
            em.persist(user);
            if (roleID != null) {
                roleID.getUserList().add(user);
                roleID = em.merge(roleID);
            }
            for (Rating ratingListRating : user.getRatingList()) {
                User oldUserOfRatingListRating = ratingListRating.getUser();
                ratingListRating.setUser(user);
                ratingListRating = em.merge(ratingListRating);
                if (oldUserOfRatingListRating != null) {
                    oldUserOfRatingListRating.getRatingList().remove(ratingListRating);
                    oldUserOfRatingListRating = em.merge(oldUserOfRatingListRating);
                }
            }
            for (Comment commentListComment : user.getCommentList()) {
                User oldUserIDOfCommentListComment = commentListComment.getUserID();
                commentListComment.setUserID(user);
                commentListComment = em.merge(commentListComment);
                if (oldUserIDOfCommentListComment != null) {
                    oldUserIDOfCommentListComment.getCommentList().remove(commentListComment);
                    oldUserIDOfCommentListComment = em.merge(oldUserIDOfCommentListComment);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUser(user.getUserId()) != null) {
                throw new PreexistingEntityException("User " + user + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getUserId());
            Role roleIDOld = persistentUser.getRoleID();
            Role roleIDNew = user.getRoleID();
            List<Rating> ratingListOld = persistentUser.getRatingList();
            List<Rating> ratingListNew = user.getRatingList();
            List<Comment> commentListOld = persistentUser.getCommentList();
            List<Comment> commentListNew = user.getCommentList();
            List<String> illegalOrphanMessages = null;
            for (Rating ratingListOldRating : ratingListOld) {
                if (!ratingListNew.contains(ratingListOldRating)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rating " + ratingListOldRating + " since its user field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (roleIDNew != null) {
                roleIDNew = em.getReference(roleIDNew.getClass(), roleIDNew.getRoleID());
                user.setRoleID(roleIDNew);
            }
            List<Rating> attachedRatingListNew = new ArrayList<Rating>();
            for (Rating ratingListNewRatingToAttach : ratingListNew) {
                ratingListNewRatingToAttach = em.getReference(ratingListNewRatingToAttach.getClass(), ratingListNewRatingToAttach.getRatingPK());
                attachedRatingListNew.add(ratingListNewRatingToAttach);
            }
            ratingListNew = attachedRatingListNew;
            user.setRatingList(ratingListNew);
            List<Comment> attachedCommentListNew = new ArrayList<Comment>();
            for (Comment commentListNewCommentToAttach : commentListNew) {
                commentListNewCommentToAttach = em.getReference(commentListNewCommentToAttach.getClass(), commentListNewCommentToAttach.getCommentID());
                attachedCommentListNew.add(commentListNewCommentToAttach);
            }
            commentListNew = attachedCommentListNew;
            user.setCommentList(commentListNew);
            user = em.merge(user);
            if (roleIDOld != null && !roleIDOld.equals(roleIDNew)) {
                roleIDOld.getUserList().remove(user);
                roleIDOld = em.merge(roleIDOld);
            }
            if (roleIDNew != null && !roleIDNew.equals(roleIDOld)) {
                roleIDNew.getUserList().add(user);
                roleIDNew = em.merge(roleIDNew);
            }
            for (Rating ratingListNewRating : ratingListNew) {
                if (!ratingListOld.contains(ratingListNewRating)) {
                    User oldUserOfRatingListNewRating = ratingListNewRating.getUser();
                    ratingListNewRating.setUser(user);
                    ratingListNewRating = em.merge(ratingListNewRating);
                    if (oldUserOfRatingListNewRating != null && !oldUserOfRatingListNewRating.equals(user)) {
                        oldUserOfRatingListNewRating.getRatingList().remove(ratingListNewRating);
                        oldUserOfRatingListNewRating = em.merge(oldUserOfRatingListNewRating);
                    }
                }
            }
            for (Comment commentListOldComment : commentListOld) {
                if (!commentListNew.contains(commentListOldComment)) {
                    commentListOldComment.setUserID(null);
                    commentListOldComment = em.merge(commentListOldComment);
                }
            }
            for (Comment commentListNewComment : commentListNew) {
                if (!commentListOld.contains(commentListNewComment)) {
                    User oldUserIDOfCommentListNewComment = commentListNewComment.getUserID();
                    commentListNewComment.setUserID(user);
                    commentListNewComment = em.merge(commentListNewComment);
                    if (oldUserIDOfCommentListNewComment != null && !oldUserIDOfCommentListNewComment.equals(user)) {
                        oldUserIDOfCommentListNewComment.getCommentList().remove(commentListNewComment);
                        oldUserIDOfCommentListNewComment = em.merge(oldUserIDOfCommentListNewComment);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getUserId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Rating> ratingListOrphanCheck = user.getRatingList();
            for (Rating ratingListOrphanCheckRating : ratingListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Rating " + ratingListOrphanCheckRating + " in its ratingList field has a non-nullable user field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Role roleID = user.getRoleID();
            if (roleID != null) {
                roleID.getUserList().remove(user);
                roleID = em.merge(roleID);
            }
            List<Comment> commentList = user.getCommentList();
            for (Comment commentListComment : commentList) {
                commentListComment.setUserID(null);
                commentListComment = em.merge(commentListComment);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
