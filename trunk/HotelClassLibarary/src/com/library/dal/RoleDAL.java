/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.dal;

import com.library.b.entities.District;
import com.library.b.entities.Role;
import com.library.controllers.UserJpaController;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author ThuHoa
 */
public class RoleDAL {

    private static UserJpaController pjc = new UserJpaController(Persistence.createEntityManagerFactory("HotelClassWebLibraryPU"));
    private static EntityManager em = pjc.getEntityManager();

    /*
     * Find role
     * 
     */
    public static Role findRole(int id) {
        TypedQuery<Role> query = em.createNamedQuery("Role.findByRoleID", Role.class);
        query.setParameter("roleID", id);
        List<Role> results = query.getResultList();
        return results.get(0);
    }
}
