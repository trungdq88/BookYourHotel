/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import jpa.controller.HotelJpaController;
import jpa.entities.Hotel;

/**
 *
 * @author Quang Trung
 */
@ManagedBean
@RequestScoped
public class HomepageBean {

    @PersistenceUnit(unitName = "HotelRecommenderPU") //inject from your application server 
    EntityManagerFactory emf;
    InitialContext context;
    @ManagedProperty(value = "#{param.page}")
    private String page;
    @ManagedProperty(value = "#{param.perpage}")
    private int perpage = 0;

    private int defaultPerpage = 6;
    
    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    
    
    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    /**
     * Creates a new instance of HomepageBean
     */
    public HomepageBean() {
        try {
            context = new InitialContext();
        } catch (Exception e) {
        }

    }

    public List<Hotel> index() {
        try {
            UserTransaction utx = (UserTransaction) context.lookup("java:comp/UserTransaction");
            HotelJpaController hjc = new HotelJpaController(utx, emf);
            EntityManager em = hjc.getEntityManager();

            TypedQuery<Hotel> q = em.createQuery("SELECT h FROM Hotel h", Hotel.class);

            int intPage = 1;
            try {
                intPage = Integer.parseInt(page);
            } catch (Exception e) {
            }
            if (perpage == 0) {
                perpage = defaultPerpage;
            }
            q.setMaxResults(perpage);
            q.setFirstResult(intPage);

            List<Hotel> results = q.getResultList();
            return results;
        } catch (NamingException ex) {
            Logger.getLogger(HomepageBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int getMaxPages() {
        try {
            UserTransaction utx = (UserTransaction) context.lookup("java:comp/UserTransaction");
            HotelJpaController hjc = new HotelJpaController(utx, emf);
            EntityManager em = hjc.getEntityManager();

            TypedQuery<Hotel> q = em.createQuery("SELECT h FROM Hotel h", Hotel.class);

            List<Hotel> results = q.getResultList();
            
            if (perpage == 0) {
                perpage = defaultPerpage;
            }
            return (int)Math.ceil(results.size() / perpage);
        } catch (Exception e) {
            return 0;
        }
    }

    public List<Integer> getPagination() {
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= getMaxPages(); i++ ) {
            result.add(i);
        }
        return result;
    }
    
    public String abc() {
        return "ABC";
    }
}
