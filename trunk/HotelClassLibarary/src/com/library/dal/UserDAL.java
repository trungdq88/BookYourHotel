/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.dal;

import com.library.b.entities.User;
import com.library.controllers.UserJpaController;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author ThuHoa
 */
public class UserDAL {

    private static UserJpaController pjc = new UserJpaController(Persistence.createEntityManagerFactory("HotelClassWebLibraryPU"));
    private static EntityManager em = pjc.getEntityManager();

    /**
     * Check login
     *
     * @param username
     * @param password
     * @return
     */
    public static User checkLogin(String username, String password) {
        //TypedQuery<User> q = em.createQuery("SELECT hp FROM Properties p, HotelProperties hp WHERE hp.hotelPropertiesPK.websiteID = ?1 AND hp.hotelPropertiesPK.propertyID = p.propertyID", User1.class);
        Query q = em.createNativeQuery("SELECT * FROM [User] WHERE username = ? AND password = ?", User.class);
        q.setParameter(1, username);
        q.setParameter(2, password);

        List<User> results = q.getResultList();
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    /**
     * Register user
     *
     * @param user
     * @return true if insert success
     */
    public static boolean addUser(User user) {
        try {
            em.getTransaction().begin();
            Query query = em.createNativeQuery("INSERT INTO [User] ([Username], [Password], [Email], [RoleID]) VALUES (?1, ?2, ?3, ?4)");
            query.setParameter(1, user.getUsername());
            query.setParameter(2, user.getPassword());
            query.setParameter(3, user.getEmail());
            query.setParameter(4, user.getRoleID().getRoleID());
            query.executeUpdate();
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Get all user
     *
     * @return
     */
    public List<User> getAllUsers() {
        Query query = em.createNativeQuery("SELECT * FROM [User]", User.class);
        List<User> result = query.getResultList();
        return result;
    }

    public User getUser(int i) {
        Query query = em.createNativeQuery("SELECT * FROM [User] WHERE UserId = ?1", User.class);
        query.setParameter(1, i);
        try {
            return (User) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    
    public static boolean checkUsername(String username){
         Query query = em.createNativeQuery("SELECT * FROM [User] WHERE username = ?1", User.class);
         query.setParameter(1, username);
        if (query.getResultList().isEmpty()) return true;
        return false;
    }
    public static boolean checkEmail(String email){
         Query query = em.createNativeQuery("SELECT * FROM [User] WHERE email = ?1", User.class);
         query.setParameter(1, email);
        if (query.getResultList().isEmpty()) return true;
        return false;
    }
}
