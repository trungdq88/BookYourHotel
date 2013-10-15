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
import java.util.ArrayList;
import java.util.List;
import com.library.b.entities.District;
import com.library.b.entities.Province;
import com.library.controllers.exceptions.IllegalOrphanException;
import com.library.controllers.exceptions.NonexistentEntityException;
import com.library.controllers.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ThuHoa
 */
public class ProvinceJpaController implements Serializable {

    public ProvinceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Province province) throws PreexistingEntityException, Exception {
        if (province.getHotelList() == null) {
            province.setHotelList(new ArrayList<Hotel>());
        }
        if (province.getDistrictList() == null) {
            province.setDistrictList(new ArrayList<District>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Hotel> attachedHotelList = new ArrayList<Hotel>();
            for (Hotel hotelListHotelToAttach : province.getHotelList()) {
                hotelListHotelToAttach = em.getReference(hotelListHotelToAttach.getClass(), hotelListHotelToAttach.getHotelID());
                attachedHotelList.add(hotelListHotelToAttach);
            }
            province.setHotelList(attachedHotelList);
            List<District> attachedDistrictList = new ArrayList<District>();
            for (District districtListDistrictToAttach : province.getDistrictList()) {
                districtListDistrictToAttach = em.getReference(districtListDistrictToAttach.getClass(), districtListDistrictToAttach.getDistrictid());
                attachedDistrictList.add(districtListDistrictToAttach);
            }
            province.setDistrictList(attachedDistrictList);
            em.persist(province);
            for (Hotel hotelListHotel : province.getHotelList()) {
                Province oldProvinceidOfHotelListHotel = hotelListHotel.getProvinceid();
                hotelListHotel.setProvinceid(province);
                hotelListHotel = em.merge(hotelListHotel);
                if (oldProvinceidOfHotelListHotel != null) {
                    oldProvinceidOfHotelListHotel.getHotelList().remove(hotelListHotel);
                    oldProvinceidOfHotelListHotel = em.merge(oldProvinceidOfHotelListHotel);
                }
            }
            for (District districtListDistrict : province.getDistrictList()) {
                Province oldProvinceidOfDistrictListDistrict = districtListDistrict.getProvinceid();
                districtListDistrict.setProvinceid(province);
                districtListDistrict = em.merge(districtListDistrict);
                if (oldProvinceidOfDistrictListDistrict != null) {
                    oldProvinceidOfDistrictListDistrict.getDistrictList().remove(districtListDistrict);
                    oldProvinceidOfDistrictListDistrict = em.merge(oldProvinceidOfDistrictListDistrict);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProvince(province.getProvinceid()) != null) {
                throw new PreexistingEntityException("Province " + province + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Province province) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Province persistentProvince = em.find(Province.class, province.getProvinceid());
            List<Hotel> hotelListOld = persistentProvince.getHotelList();
            List<Hotel> hotelListNew = province.getHotelList();
            List<District> districtListOld = persistentProvince.getDistrictList();
            List<District> districtListNew = province.getDistrictList();
            List<String> illegalOrphanMessages = null;
            for (District districtListOldDistrict : districtListOld) {
                if (!districtListNew.contains(districtListOldDistrict)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain District " + districtListOldDistrict + " since its provinceid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Hotel> attachedHotelListNew = new ArrayList<Hotel>();
            for (Hotel hotelListNewHotelToAttach : hotelListNew) {
                hotelListNewHotelToAttach = em.getReference(hotelListNewHotelToAttach.getClass(), hotelListNewHotelToAttach.getHotelID());
                attachedHotelListNew.add(hotelListNewHotelToAttach);
            }
            hotelListNew = attachedHotelListNew;
            province.setHotelList(hotelListNew);
            List<District> attachedDistrictListNew = new ArrayList<District>();
            for (District districtListNewDistrictToAttach : districtListNew) {
                districtListNewDistrictToAttach = em.getReference(districtListNewDistrictToAttach.getClass(), districtListNewDistrictToAttach.getDistrictid());
                attachedDistrictListNew.add(districtListNewDistrictToAttach);
            }
            districtListNew = attachedDistrictListNew;
            province.setDistrictList(districtListNew);
            province = em.merge(province);
            for (Hotel hotelListOldHotel : hotelListOld) {
                if (!hotelListNew.contains(hotelListOldHotel)) {
                    hotelListOldHotel.setProvinceid(null);
                    hotelListOldHotel = em.merge(hotelListOldHotel);
                }
            }
            for (Hotel hotelListNewHotel : hotelListNew) {
                if (!hotelListOld.contains(hotelListNewHotel)) {
                    Province oldProvinceidOfHotelListNewHotel = hotelListNewHotel.getProvinceid();
                    hotelListNewHotel.setProvinceid(province);
                    hotelListNewHotel = em.merge(hotelListNewHotel);
                    if (oldProvinceidOfHotelListNewHotel != null && !oldProvinceidOfHotelListNewHotel.equals(province)) {
                        oldProvinceidOfHotelListNewHotel.getHotelList().remove(hotelListNewHotel);
                        oldProvinceidOfHotelListNewHotel = em.merge(oldProvinceidOfHotelListNewHotel);
                    }
                }
            }
            for (District districtListNewDistrict : districtListNew) {
                if (!districtListOld.contains(districtListNewDistrict)) {
                    Province oldProvinceidOfDistrictListNewDistrict = districtListNewDistrict.getProvinceid();
                    districtListNewDistrict.setProvinceid(province);
                    districtListNewDistrict = em.merge(districtListNewDistrict);
                    if (oldProvinceidOfDistrictListNewDistrict != null && !oldProvinceidOfDistrictListNewDistrict.equals(province)) {
                        oldProvinceidOfDistrictListNewDistrict.getDistrictList().remove(districtListNewDistrict);
                        oldProvinceidOfDistrictListNewDistrict = em.merge(oldProvinceidOfDistrictListNewDistrict);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = province.getProvinceid();
                if (findProvince(id) == null) {
                    throw new NonexistentEntityException("The province with id " + id + " no longer exists.");
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
            Province province;
            try {
                province = em.getReference(Province.class, id);
                province.getProvinceid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The province with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<District> districtListOrphanCheck = province.getDistrictList();
            for (District districtListOrphanCheckDistrict : districtListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Province (" + province + ") cannot be destroyed since the District " + districtListOrphanCheckDistrict + " in its districtList field has a non-nullable provinceid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Hotel> hotelList = province.getHotelList();
            for (Hotel hotelListHotel : hotelList) {
                hotelListHotel.setProvinceid(null);
                hotelListHotel = em.merge(hotelListHotel);
            }
            em.remove(province);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Province> findProvinceEntities() {
        return findProvinceEntities(true, -1, -1);
    }

    public List<Province> findProvinceEntities(int maxResults, int firstResult) {
        return findProvinceEntities(false, maxResults, firstResult);
    }

    private List<Province> findProvinceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Province.class));
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

    public Province findProvince(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Province.class, id);
        } finally {
            em.close();
        }
    }

    public int getProvinceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Province> rt = cq.from(Province.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
