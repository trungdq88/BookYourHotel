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
import jpa.entities.LinkDetail;
import jpa.entities.HotelProperties;
import jpa.entities.Website;

/**
 *
 * @author Quang Trung
 */
public class WebsiteJpaController implements Serializable {

    public WebsiteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Website website) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (website.getRoomPropertiesList() == null) {
            website.setRoomPropertiesList(new ArrayList<RoomProperties>());
        }
        if (website.getLinkDetailList() == null) {
            website.setLinkDetailList(new ArrayList<LinkDetail>());
        }
        if (website.getHotelPropertiesList() == null) {
            website.setHotelPropertiesList(new ArrayList<HotelProperties>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<RoomProperties> attachedRoomPropertiesList = new ArrayList<RoomProperties>();
            for (RoomProperties roomPropertiesListRoomPropertiesToAttach : website.getRoomPropertiesList()) {
                roomPropertiesListRoomPropertiesToAttach = em.getReference(roomPropertiesListRoomPropertiesToAttach.getClass(), roomPropertiesListRoomPropertiesToAttach.getRoomPropertiesPK());
                attachedRoomPropertiesList.add(roomPropertiesListRoomPropertiesToAttach);
            }
            website.setRoomPropertiesList(attachedRoomPropertiesList);
            List<LinkDetail> attachedLinkDetailList = new ArrayList<LinkDetail>();
            for (LinkDetail linkDetailListLinkDetailToAttach : website.getLinkDetailList()) {
                linkDetailListLinkDetailToAttach = em.getReference(linkDetailListLinkDetailToAttach.getClass(), linkDetailListLinkDetailToAttach.getLinkDetailID());
                attachedLinkDetailList.add(linkDetailListLinkDetailToAttach);
            }
            website.setLinkDetailList(attachedLinkDetailList);
            List<HotelProperties> attachedHotelPropertiesList = new ArrayList<HotelProperties>();
            for (HotelProperties hotelPropertiesListHotelPropertiesToAttach : website.getHotelPropertiesList()) {
                hotelPropertiesListHotelPropertiesToAttach = em.getReference(hotelPropertiesListHotelPropertiesToAttach.getClass(), hotelPropertiesListHotelPropertiesToAttach.getHotelPropertiesPK());
                attachedHotelPropertiesList.add(hotelPropertiesListHotelPropertiesToAttach);
            }
            website.setHotelPropertiesList(attachedHotelPropertiesList);
            em.persist(website);
            for (RoomProperties roomPropertiesListRoomProperties : website.getRoomPropertiesList()) {
                Website oldWebsiteOfRoomPropertiesListRoomProperties = roomPropertiesListRoomProperties.getWebsite();
                roomPropertiesListRoomProperties.setWebsite(website);
                roomPropertiesListRoomProperties = em.merge(roomPropertiesListRoomProperties);
                if (oldWebsiteOfRoomPropertiesListRoomProperties != null) {
                    oldWebsiteOfRoomPropertiesListRoomProperties.getRoomPropertiesList().remove(roomPropertiesListRoomProperties);
                    oldWebsiteOfRoomPropertiesListRoomProperties = em.merge(oldWebsiteOfRoomPropertiesListRoomProperties);
                }
            }
            for (LinkDetail linkDetailListLinkDetail : website.getLinkDetailList()) {
                Website oldWebsiteIDOfLinkDetailListLinkDetail = linkDetailListLinkDetail.getWebsiteID();
                linkDetailListLinkDetail.setWebsiteID(website);
                linkDetailListLinkDetail = em.merge(linkDetailListLinkDetail);
                if (oldWebsiteIDOfLinkDetailListLinkDetail != null) {
                    oldWebsiteIDOfLinkDetailListLinkDetail.getLinkDetailList().remove(linkDetailListLinkDetail);
                    oldWebsiteIDOfLinkDetailListLinkDetail = em.merge(oldWebsiteIDOfLinkDetailListLinkDetail);
                }
            }
            for (HotelProperties hotelPropertiesListHotelProperties : website.getHotelPropertiesList()) {
                Website oldWebsiteOfHotelPropertiesListHotelProperties = hotelPropertiesListHotelProperties.getWebsite();
                hotelPropertiesListHotelProperties.setWebsite(website);
                hotelPropertiesListHotelProperties = em.merge(hotelPropertiesListHotelProperties);
                if (oldWebsiteOfHotelPropertiesListHotelProperties != null) {
                    oldWebsiteOfHotelPropertiesListHotelProperties.getHotelPropertiesList().remove(hotelPropertiesListHotelProperties);
                    oldWebsiteOfHotelPropertiesListHotelProperties = em.merge(oldWebsiteOfHotelPropertiesListHotelProperties);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findWebsite(website.getWebsiteID()) != null) {
                throw new PreexistingEntityException("Website " + website + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Website website) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Website persistentWebsite = em.find(Website.class, website.getWebsiteID());
            List<RoomProperties> roomPropertiesListOld = persistentWebsite.getRoomPropertiesList();
            List<RoomProperties> roomPropertiesListNew = website.getRoomPropertiesList();
            List<LinkDetail> linkDetailListOld = persistentWebsite.getLinkDetailList();
            List<LinkDetail> linkDetailListNew = website.getLinkDetailList();
            List<HotelProperties> hotelPropertiesListOld = persistentWebsite.getHotelPropertiesList();
            List<HotelProperties> hotelPropertiesListNew = website.getHotelPropertiesList();
            List<String> illegalOrphanMessages = null;
            for (RoomProperties roomPropertiesListOldRoomProperties : roomPropertiesListOld) {
                if (!roomPropertiesListNew.contains(roomPropertiesListOldRoomProperties)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RoomProperties " + roomPropertiesListOldRoomProperties + " since its website field is not nullable.");
                }
            }
            for (HotelProperties hotelPropertiesListOldHotelProperties : hotelPropertiesListOld) {
                if (!hotelPropertiesListNew.contains(hotelPropertiesListOldHotelProperties)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HotelProperties " + hotelPropertiesListOldHotelProperties + " since its website field is not nullable.");
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
            website.setRoomPropertiesList(roomPropertiesListNew);
            List<LinkDetail> attachedLinkDetailListNew = new ArrayList<LinkDetail>();
            for (LinkDetail linkDetailListNewLinkDetailToAttach : linkDetailListNew) {
                linkDetailListNewLinkDetailToAttach = em.getReference(linkDetailListNewLinkDetailToAttach.getClass(), linkDetailListNewLinkDetailToAttach.getLinkDetailID());
                attachedLinkDetailListNew.add(linkDetailListNewLinkDetailToAttach);
            }
            linkDetailListNew = attachedLinkDetailListNew;
            website.setLinkDetailList(linkDetailListNew);
            List<HotelProperties> attachedHotelPropertiesListNew = new ArrayList<HotelProperties>();
            for (HotelProperties hotelPropertiesListNewHotelPropertiesToAttach : hotelPropertiesListNew) {
                hotelPropertiesListNewHotelPropertiesToAttach = em.getReference(hotelPropertiesListNewHotelPropertiesToAttach.getClass(), hotelPropertiesListNewHotelPropertiesToAttach.getHotelPropertiesPK());
                attachedHotelPropertiesListNew.add(hotelPropertiesListNewHotelPropertiesToAttach);
            }
            hotelPropertiesListNew = attachedHotelPropertiesListNew;
            website.setHotelPropertiesList(hotelPropertiesListNew);
            website = em.merge(website);
            for (RoomProperties roomPropertiesListNewRoomProperties : roomPropertiesListNew) {
                if (!roomPropertiesListOld.contains(roomPropertiesListNewRoomProperties)) {
                    Website oldWebsiteOfRoomPropertiesListNewRoomProperties = roomPropertiesListNewRoomProperties.getWebsite();
                    roomPropertiesListNewRoomProperties.setWebsite(website);
                    roomPropertiesListNewRoomProperties = em.merge(roomPropertiesListNewRoomProperties);
                    if (oldWebsiteOfRoomPropertiesListNewRoomProperties != null && !oldWebsiteOfRoomPropertiesListNewRoomProperties.equals(website)) {
                        oldWebsiteOfRoomPropertiesListNewRoomProperties.getRoomPropertiesList().remove(roomPropertiesListNewRoomProperties);
                        oldWebsiteOfRoomPropertiesListNewRoomProperties = em.merge(oldWebsiteOfRoomPropertiesListNewRoomProperties);
                    }
                }
            }
            for (LinkDetail linkDetailListOldLinkDetail : linkDetailListOld) {
                if (!linkDetailListNew.contains(linkDetailListOldLinkDetail)) {
                    linkDetailListOldLinkDetail.setWebsiteID(null);
                    linkDetailListOldLinkDetail = em.merge(linkDetailListOldLinkDetail);
                }
            }
            for (LinkDetail linkDetailListNewLinkDetail : linkDetailListNew) {
                if (!linkDetailListOld.contains(linkDetailListNewLinkDetail)) {
                    Website oldWebsiteIDOfLinkDetailListNewLinkDetail = linkDetailListNewLinkDetail.getWebsiteID();
                    linkDetailListNewLinkDetail.setWebsiteID(website);
                    linkDetailListNewLinkDetail = em.merge(linkDetailListNewLinkDetail);
                    if (oldWebsiteIDOfLinkDetailListNewLinkDetail != null && !oldWebsiteIDOfLinkDetailListNewLinkDetail.equals(website)) {
                        oldWebsiteIDOfLinkDetailListNewLinkDetail.getLinkDetailList().remove(linkDetailListNewLinkDetail);
                        oldWebsiteIDOfLinkDetailListNewLinkDetail = em.merge(oldWebsiteIDOfLinkDetailListNewLinkDetail);
                    }
                }
            }
            for (HotelProperties hotelPropertiesListNewHotelProperties : hotelPropertiesListNew) {
                if (!hotelPropertiesListOld.contains(hotelPropertiesListNewHotelProperties)) {
                    Website oldWebsiteOfHotelPropertiesListNewHotelProperties = hotelPropertiesListNewHotelProperties.getWebsite();
                    hotelPropertiesListNewHotelProperties.setWebsite(website);
                    hotelPropertiesListNewHotelProperties = em.merge(hotelPropertiesListNewHotelProperties);
                    if (oldWebsiteOfHotelPropertiesListNewHotelProperties != null && !oldWebsiteOfHotelPropertiesListNewHotelProperties.equals(website)) {
                        oldWebsiteOfHotelPropertiesListNewHotelProperties.getHotelPropertiesList().remove(hotelPropertiesListNewHotelProperties);
                        oldWebsiteOfHotelPropertiesListNewHotelProperties = em.merge(oldWebsiteOfHotelPropertiesListNewHotelProperties);
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
                Integer id = website.getWebsiteID();
                if (findWebsite(id) == null) {
                    throw new NonexistentEntityException("The website with id " + id + " no longer exists.");
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
            Website website;
            try {
                website = em.getReference(Website.class, id);
                website.getWebsiteID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The website with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RoomProperties> roomPropertiesListOrphanCheck = website.getRoomPropertiesList();
            for (RoomProperties roomPropertiesListOrphanCheckRoomProperties : roomPropertiesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Website (" + website + ") cannot be destroyed since the RoomProperties " + roomPropertiesListOrphanCheckRoomProperties + " in its roomPropertiesList field has a non-nullable website field.");
            }
            List<HotelProperties> hotelPropertiesListOrphanCheck = website.getHotelPropertiesList();
            for (HotelProperties hotelPropertiesListOrphanCheckHotelProperties : hotelPropertiesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Website (" + website + ") cannot be destroyed since the HotelProperties " + hotelPropertiesListOrphanCheckHotelProperties + " in its hotelPropertiesList field has a non-nullable website field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<LinkDetail> linkDetailList = website.getLinkDetailList();
            for (LinkDetail linkDetailListLinkDetail : linkDetailList) {
                linkDetailListLinkDetail.setWebsiteID(null);
                linkDetailListLinkDetail = em.merge(linkDetailListLinkDetail);
            }
            em.remove(website);
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

    public List<Website> findWebsiteEntities() {
        return findWebsiteEntities(true, -1, -1);
    }

    public List<Website> findWebsiteEntities(int maxResults, int firstResult) {
        return findWebsiteEntities(false, maxResults, firstResult);
    }

    private List<Website> findWebsiteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Website.class));
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

    public Website findWebsite(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Website.class, id);
        } finally {
            em.close();
        }
    }

    public int getWebsiteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Website> rt = cq.from(Website.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
