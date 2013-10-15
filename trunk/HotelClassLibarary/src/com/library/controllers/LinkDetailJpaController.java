/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.controllers;

import com.library.a.entities.LinkDetail;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.library.a.entities.Website;
import com.library.controllers.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ThuHoa
 */
public class LinkDetailJpaController implements Serializable {

    public LinkDetailJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LinkDetail linkDetail) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Website websiteID = linkDetail.getWebsiteID();
            if (websiteID != null) {
                websiteID = em.getReference(websiteID.getClass(), websiteID.getWebsiteID());
                linkDetail.setWebsiteID(websiteID);
            }
            em.persist(linkDetail);
            if (websiteID != null) {
                websiteID.getLinkDetailList().add(linkDetail);
                websiteID = em.merge(websiteID);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LinkDetail linkDetail) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LinkDetail persistentLinkDetail = em.find(LinkDetail.class, linkDetail.getLinkDetailID());
            Website websiteIDOld = persistentLinkDetail.getWebsiteID();
            Website websiteIDNew = linkDetail.getWebsiteID();
            if (websiteIDNew != null) {
                websiteIDNew = em.getReference(websiteIDNew.getClass(), websiteIDNew.getWebsiteID());
                linkDetail.setWebsiteID(websiteIDNew);
            }
            linkDetail = em.merge(linkDetail);
            if (websiteIDOld != null && !websiteIDOld.equals(websiteIDNew)) {
                websiteIDOld.getLinkDetailList().remove(linkDetail);
                websiteIDOld = em.merge(websiteIDOld);
            }
            if (websiteIDNew != null && !websiteIDNew.equals(websiteIDOld)) {
                websiteIDNew.getLinkDetailList().add(linkDetail);
                websiteIDNew = em.merge(websiteIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = linkDetail.getLinkDetailID();
                if (findLinkDetail(id) == null) {
                    throw new NonexistentEntityException("The linkDetail with id " + id + " no longer exists.");
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
            LinkDetail linkDetail;
            try {
                linkDetail = em.getReference(LinkDetail.class, id);
                linkDetail.getLinkDetailID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The linkDetail with id " + id + " no longer exists.", enfe);
            }
            Website websiteID = linkDetail.getWebsiteID();
            if (websiteID != null) {
                websiteID.getLinkDetailList().remove(linkDetail);
                websiteID = em.merge(websiteID);
            }
            em.remove(linkDetail);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LinkDetail> findLinkDetailEntities() {
        return findLinkDetailEntities(true, -1, -1);
    }

    public List<LinkDetail> findLinkDetailEntities(int maxResults, int firstResult) {
        return findLinkDetailEntities(false, maxResults, firstResult);
    }

    private List<LinkDetail> findLinkDetailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LinkDetail.class));
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

    public LinkDetail findLinkDetail(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LinkDetail.class, id);
        } finally {
            em.close();
        }
    }

    public int getLinkDetailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LinkDetail> rt = cq.from(LinkDetail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
