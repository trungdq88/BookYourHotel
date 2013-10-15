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
import com.library.b.entities.District;
import com.library.b.entities.Hotel;
import com.library.b.entities.Ward;
import com.library.controllers.exceptions.NonexistentEntityException;
import com.library.controllers.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ThuHoa
 */
public class WardJpaController implements Serializable {

    public WardJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ward ward) throws PreexistingEntityException, Exception {
        if (ward.getHotelList() == null) {
            ward.setHotelList(new ArrayList<Hotel>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            District districtid = ward.getDistrictid();
            if (districtid != null) {
                districtid = em.getReference(districtid.getClass(), districtid.getDistrictid());
                ward.setDistrictid(districtid);
            }
            List<Hotel> attachedHotelList = new ArrayList<Hotel>();
            for (Hotel hotelListHotelToAttach : ward.getHotelList()) {
                hotelListHotelToAttach = em.getReference(hotelListHotelToAttach.getClass(), hotelListHotelToAttach.getHotelID());
                attachedHotelList.add(hotelListHotelToAttach);
            }
            ward.setHotelList(attachedHotelList);
            em.persist(ward);
            if (districtid != null) {
                districtid.getWardList().add(ward);
                districtid = em.merge(districtid);
            }
            for (Hotel hotelListHotel : ward.getHotelList()) {
                Ward oldWardidOfHotelListHotel = hotelListHotel.getWardid();
                hotelListHotel.setWardid(ward);
                hotelListHotel = em.merge(hotelListHotel);
                if (oldWardidOfHotelListHotel != null) {
                    oldWardidOfHotelListHotel.getHotelList().remove(hotelListHotel);
                    oldWardidOfHotelListHotel = em.merge(oldWardidOfHotelListHotel);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findWard(ward.getWardid()) != null) {
                throw new PreexistingEntityException("Ward " + ward + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ward ward) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ward persistentWard = em.find(Ward.class, ward.getWardid());
            District districtidOld = persistentWard.getDistrictid();
            District districtidNew = ward.getDistrictid();
            List<Hotel> hotelListOld = persistentWard.getHotelList();
            List<Hotel> hotelListNew = ward.getHotelList();
            if (districtidNew != null) {
                districtidNew = em.getReference(districtidNew.getClass(), districtidNew.getDistrictid());
                ward.setDistrictid(districtidNew);
            }
            List<Hotel> attachedHotelListNew = new ArrayList<Hotel>();
            for (Hotel hotelListNewHotelToAttach : hotelListNew) {
                hotelListNewHotelToAttach = em.getReference(hotelListNewHotelToAttach.getClass(), hotelListNewHotelToAttach.getHotelID());
                attachedHotelListNew.add(hotelListNewHotelToAttach);
            }
            hotelListNew = attachedHotelListNew;
            ward.setHotelList(hotelListNew);
            ward = em.merge(ward);
            if (districtidOld != null && !districtidOld.equals(districtidNew)) {
                districtidOld.getWardList().remove(ward);
                districtidOld = em.merge(districtidOld);
            }
            if (districtidNew != null && !districtidNew.equals(districtidOld)) {
                districtidNew.getWardList().add(ward);
                districtidNew = em.merge(districtidNew);
            }
            for (Hotel hotelListOldHotel : hotelListOld) {
                if (!hotelListNew.contains(hotelListOldHotel)) {
                    hotelListOldHotel.setWardid(null);
                    hotelListOldHotel = em.merge(hotelListOldHotel);
                }
            }
            for (Hotel hotelListNewHotel : hotelListNew) {
                if (!hotelListOld.contains(hotelListNewHotel)) {
                    Ward oldWardidOfHotelListNewHotel = hotelListNewHotel.getWardid();
                    hotelListNewHotel.setWardid(ward);
                    hotelListNewHotel = em.merge(hotelListNewHotel);
                    if (oldWardidOfHotelListNewHotel != null && !oldWardidOfHotelListNewHotel.equals(ward)) {
                        oldWardidOfHotelListNewHotel.getHotelList().remove(hotelListNewHotel);
                        oldWardidOfHotelListNewHotel = em.merge(oldWardidOfHotelListNewHotel);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = ward.getWardid();
                if (findWard(id) == null) {
                    throw new NonexistentEntityException("The ward with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ward ward;
            try {
                ward = em.getReference(Ward.class, id);
                ward.getWardid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ward with id " + id + " no longer exists.", enfe);
            }
            District districtid = ward.getDistrictid();
            if (districtid != null) {
                districtid.getWardList().remove(ward);
                districtid = em.merge(districtid);
            }
            List<Hotel> hotelList = ward.getHotelList();
            for (Hotel hotelListHotel : hotelList) {
                hotelListHotel.setWardid(null);
                hotelListHotel = em.merge(hotelListHotel);
            }
            em.remove(ward);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ward> findWardEntities() {
        return findWardEntities(true, -1, -1);
    }

    public List<Ward> findWardEntities(int maxResults, int firstResult) {
        return findWardEntities(false, maxResults, firstResult);
    }

    private List<Ward> findWardEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ward.class));
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

    public Ward findWard(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ward.class, id);
        } finally {
            em.close();
        }
    }

    public int getWardCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ward> rt = cq.from(Ward.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
