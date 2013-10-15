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
import jpa.entities.Hotel;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpa.controller.exceptions.NonexistentEntityException;
import jpa.controller.exceptions.PreexistingEntityException;
import jpa.controller.exceptions.RollbackFailureException;
import jpa.entities.Region;

/**
 *
 * @author Quang Trung
 */
public class RegionJpaController implements Serializable {

    public RegionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Region region) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (region.getHotelList() == null) {
            region.setHotelList(new ArrayList<Hotel>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Hotel> attachedHotelList = new ArrayList<Hotel>();
            for (Hotel hotelListHotelToAttach : region.getHotelList()) {
                hotelListHotelToAttach = em.getReference(hotelListHotelToAttach.getClass(), hotelListHotelToAttach.getHotelID());
                attachedHotelList.add(hotelListHotelToAttach);
            }
            region.setHotelList(attachedHotelList);
            em.persist(region);
            for (Hotel hotelListHotel : region.getHotelList()) {
                Region oldRegionIDOfHotelListHotel = hotelListHotel.getRegionID();
                hotelListHotel.setRegionID(region);
                hotelListHotel = em.merge(hotelListHotel);
                if (oldRegionIDOfHotelListHotel != null) {
                    oldRegionIDOfHotelListHotel.getHotelList().remove(hotelListHotel);
                    oldRegionIDOfHotelListHotel = em.merge(oldRegionIDOfHotelListHotel);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findRegion(region.getRegionID()) != null) {
                throw new PreexistingEntityException("Region " + region + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Region region) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Region persistentRegion = em.find(Region.class, region.getRegionID());
            List<Hotel> hotelListOld = persistentRegion.getHotelList();
            List<Hotel> hotelListNew = region.getHotelList();
            List<Hotel> attachedHotelListNew = new ArrayList<Hotel>();
            for (Hotel hotelListNewHotelToAttach : hotelListNew) {
                hotelListNewHotelToAttach = em.getReference(hotelListNewHotelToAttach.getClass(), hotelListNewHotelToAttach.getHotelID());
                attachedHotelListNew.add(hotelListNewHotelToAttach);
            }
            hotelListNew = attachedHotelListNew;
            region.setHotelList(hotelListNew);
            region = em.merge(region);
            for (Hotel hotelListOldHotel : hotelListOld) {
                if (!hotelListNew.contains(hotelListOldHotel)) {
                    hotelListOldHotel.setRegionID(null);
                    hotelListOldHotel = em.merge(hotelListOldHotel);
                }
            }
            for (Hotel hotelListNewHotel : hotelListNew) {
                if (!hotelListOld.contains(hotelListNewHotel)) {
                    Region oldRegionIDOfHotelListNewHotel = hotelListNewHotel.getRegionID();
                    hotelListNewHotel.setRegionID(region);
                    hotelListNewHotel = em.merge(hotelListNewHotel);
                    if (oldRegionIDOfHotelListNewHotel != null && !oldRegionIDOfHotelListNewHotel.equals(region)) {
                        oldRegionIDOfHotelListNewHotel.getHotelList().remove(hotelListNewHotel);
                        oldRegionIDOfHotelListNewHotel = em.merge(oldRegionIDOfHotelListNewHotel);
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
                Integer id = region.getRegionID();
                if (findRegion(id) == null) {
                    throw new NonexistentEntityException("The region with id " + id + " no longer exists.");
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
            Region region;
            try {
                region = em.getReference(Region.class, id);
                region.getRegionID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The region with id " + id + " no longer exists.", enfe);
            }
            List<Hotel> hotelList = region.getHotelList();
            for (Hotel hotelListHotel : hotelList) {
                hotelListHotel.setRegionID(null);
                hotelListHotel = em.merge(hotelListHotel);
            }
            em.remove(region);
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

    public List<Region> findRegionEntities() {
        return findRegionEntities(true, -1, -1);
    }

    public List<Region> findRegionEntities(int maxResults, int firstResult) {
        return findRegionEntities(false, maxResults, firstResult);
    }

    private List<Region> findRegionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Region.class));
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

    public Region findRegion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Region.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Region> rt = cq.from(Region.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
