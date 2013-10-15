/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.controllers;

import com.library.b.entities.District;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.library.b.entities.Province;
import com.library.b.entities.Ward;
import java.util.ArrayList;
import java.util.List;
import com.library.b.entities.Hotel;
import com.library.controllers.exceptions.IllegalOrphanException;
import com.library.controllers.exceptions.NonexistentEntityException;
import com.library.controllers.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ThuHoa
 */
public class DistrictJpaController implements Serializable {

    public DistrictJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(District district) throws PreexistingEntityException, Exception {
        if (district.getWardList() == null) {
            district.setWardList(new ArrayList<Ward>());
        }
        if (district.getHotelList() == null) {
            district.setHotelList(new ArrayList<Hotel>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Province provinceid = district.getProvinceid();
            if (provinceid != null) {
                provinceid = em.getReference(provinceid.getClass(), provinceid.getProvinceid());
                district.setProvinceid(provinceid);
            }
            List<Ward> attachedWardList = new ArrayList<Ward>();
            for (Ward wardListWardToAttach : district.getWardList()) {
                wardListWardToAttach = em.getReference(wardListWardToAttach.getClass(), wardListWardToAttach.getWardid());
                attachedWardList.add(wardListWardToAttach);
            }
            district.setWardList(attachedWardList);
            List<Hotel> attachedHotelList = new ArrayList<Hotel>();
            for (Hotel hotelListHotelToAttach : district.getHotelList()) {
                hotelListHotelToAttach = em.getReference(hotelListHotelToAttach.getClass(), hotelListHotelToAttach.getHotelID());
                attachedHotelList.add(hotelListHotelToAttach);
            }
            district.setHotelList(attachedHotelList);
            em.persist(district);
            if (provinceid != null) {
                provinceid.getDistrictList().add(district);
                provinceid = em.merge(provinceid);
            }
            for (Ward wardListWard : district.getWardList()) {
                District oldDistrictidOfWardListWard = wardListWard.getDistrictid();
                wardListWard.setDistrictid(district);
                wardListWard = em.merge(wardListWard);
                if (oldDistrictidOfWardListWard != null) {
                    oldDistrictidOfWardListWard.getWardList().remove(wardListWard);
                    oldDistrictidOfWardListWard = em.merge(oldDistrictidOfWardListWard);
                }
            }
            for (Hotel hotelListHotel : district.getHotelList()) {
                District oldDistrictidOfHotelListHotel = hotelListHotel.getDistrictid();
                hotelListHotel.setDistrictid(district);
                hotelListHotel = em.merge(hotelListHotel);
                if (oldDistrictidOfHotelListHotel != null) {
                    oldDistrictidOfHotelListHotel.getHotelList().remove(hotelListHotel);
                    oldDistrictidOfHotelListHotel = em.merge(oldDistrictidOfHotelListHotel);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDistrict(district.getDistrictid()) != null) {
                throw new PreexistingEntityException("District " + district + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(District district) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            District persistentDistrict = em.find(District.class, district.getDistrictid());
            Province provinceidOld = persistentDistrict.getProvinceid();
            Province provinceidNew = district.getProvinceid();
            List<Ward> wardListOld = persistentDistrict.getWardList();
            List<Ward> wardListNew = district.getWardList();
            List<Hotel> hotelListOld = persistentDistrict.getHotelList();
            List<Hotel> hotelListNew = district.getHotelList();
            List<String> illegalOrphanMessages = null;
            for (Ward wardListOldWard : wardListOld) {
                if (!wardListNew.contains(wardListOldWard)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ward " + wardListOldWard + " since its districtid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (provinceidNew != null) {
                provinceidNew = em.getReference(provinceidNew.getClass(), provinceidNew.getProvinceid());
                district.setProvinceid(provinceidNew);
            }
            List<Ward> attachedWardListNew = new ArrayList<Ward>();
            for (Ward wardListNewWardToAttach : wardListNew) {
                wardListNewWardToAttach = em.getReference(wardListNewWardToAttach.getClass(), wardListNewWardToAttach.getWardid());
                attachedWardListNew.add(wardListNewWardToAttach);
            }
            wardListNew = attachedWardListNew;
            district.setWardList(wardListNew);
            List<Hotel> attachedHotelListNew = new ArrayList<Hotel>();
            for (Hotel hotelListNewHotelToAttach : hotelListNew) {
                hotelListNewHotelToAttach = em.getReference(hotelListNewHotelToAttach.getClass(), hotelListNewHotelToAttach.getHotelID());
                attachedHotelListNew.add(hotelListNewHotelToAttach);
            }
            hotelListNew = attachedHotelListNew;
            district.setHotelList(hotelListNew);
            district = em.merge(district);
            if (provinceidOld != null && !provinceidOld.equals(provinceidNew)) {
                provinceidOld.getDistrictList().remove(district);
                provinceidOld = em.merge(provinceidOld);
            }
            if (provinceidNew != null && !provinceidNew.equals(provinceidOld)) {
                provinceidNew.getDistrictList().add(district);
                provinceidNew = em.merge(provinceidNew);
            }
            for (Ward wardListNewWard : wardListNew) {
                if (!wardListOld.contains(wardListNewWard)) {
                    District oldDistrictidOfWardListNewWard = wardListNewWard.getDistrictid();
                    wardListNewWard.setDistrictid(district);
                    wardListNewWard = em.merge(wardListNewWard);
                    if (oldDistrictidOfWardListNewWard != null && !oldDistrictidOfWardListNewWard.equals(district)) {
                        oldDistrictidOfWardListNewWard.getWardList().remove(wardListNewWard);
                        oldDistrictidOfWardListNewWard = em.merge(oldDistrictidOfWardListNewWard);
                    }
                }
            }
            for (Hotel hotelListOldHotel : hotelListOld) {
                if (!hotelListNew.contains(hotelListOldHotel)) {
                    hotelListOldHotel.setDistrictid(null);
                    hotelListOldHotel = em.merge(hotelListOldHotel);
                }
            }
            for (Hotel hotelListNewHotel : hotelListNew) {
                if (!hotelListOld.contains(hotelListNewHotel)) {
                    District oldDistrictidOfHotelListNewHotel = hotelListNewHotel.getDistrictid();
                    hotelListNewHotel.setDistrictid(district);
                    hotelListNewHotel = em.merge(hotelListNewHotel);
                    if (oldDistrictidOfHotelListNewHotel != null && !oldDistrictidOfHotelListNewHotel.equals(district)) {
                        oldDistrictidOfHotelListNewHotel.getHotelList().remove(hotelListNewHotel);
                        oldDistrictidOfHotelListNewHotel = em.merge(oldDistrictidOfHotelListNewHotel);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = district.getDistrictid();
                if (findDistrict(id) == null) {
                    throw new NonexistentEntityException("The district with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            District district;
            try {
                district = em.getReference(District.class, id);
                district.getDistrictid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The district with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ward> wardListOrphanCheck = district.getWardList();
            for (Ward wardListOrphanCheckWard : wardListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This District (" + district + ") cannot be destroyed since the Ward " + wardListOrphanCheckWard + " in its wardList field has a non-nullable districtid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Province provinceid = district.getProvinceid();
            if (provinceid != null) {
                provinceid.getDistrictList().remove(district);
                provinceid = em.merge(provinceid);
            }
            List<Hotel> hotelList = district.getHotelList();
            for (Hotel hotelListHotel : hotelList) {
                hotelListHotel.setDistrictid(null);
                hotelListHotel = em.merge(hotelListHotel);
            }
            em.remove(district);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<District> findDistrictEntities() {
        return findDistrictEntities(true, -1, -1);
    }

    public List<District> findDistrictEntities(int maxResults, int firstResult) {
        return findDistrictEntities(false, maxResults, firstResult);
    }

    private List<District> findDistrictEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(District.class));
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

    public District findDistrict(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(District.class, id);
        } finally {
            em.close();
        }
    }

    public int getDistrictCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<District> rt = cq.from(District.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
