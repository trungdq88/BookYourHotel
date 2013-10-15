/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.dal;

import com.library.b.entities.Comment;
import com.library.b.entities.Hotel;
import com.library.controllers.CommentJpaController;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Quang Trung
 */
public class CommentDAL {

  private static final CommentJpaController cjc = new CommentJpaController(Persistence.createEntityManagerFactory("HotelClassWebLibraryPU"));
  private static final EntityManager em = cjc.getEntityManager();

  public static boolean addComment(Comment c) {
        try {
            em.getTransaction().begin();
            Query query = em.createNativeQuery("INSERT INTO [Comment] ([CommentText], [CreatedDate], [HotelID], [UserID]) VALUES (?1, ?2, ?3, ?4)");
            query.setParameter(1, c.getCommentText());
            query.setParameter(2, c.getCreatedDate());
            query.setParameter(3, c.getHotelID().getHotelID());
            query.setParameter(4, c.getUserID().getUserId());
            query.executeUpdate();
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
  }


  private static Comment getComment(Integer commentId) {
    Comment c = new Comment();
    c.setCommentID(commentId);
    c.setCommentText(getTextComment(commentId));
    c.setUserID((new UserDAL()).getUser(getUserComment(commentId)));
    c.setCreatedDate(null); // TODO: later!
    c.setHotelID(null);
    Hotel h = new Hotel();
    
    return c;
  }
  
  
  
    public static Integer getUserComment(int id) {
    try {
      Query q = em.createNativeQuery("SELECT UserID FROM [Comment] WHERE CommentID = ?", Integer.class);
      q.setParameter(1, id);
      return (Integer) q.getSingleResult();
    } catch (Exception e) {
      System.out.println(e.toString());
      return null;
    }
  }

  
  public static String getTextComment(int id) {
    try {
      Query q = em.createNativeQuery("SELECT CommentText FROM [Comment] WHERE CommentID = ?", String.class);
      q.setParameter(1, id);
      return (String) q.getSingleResult();
    } catch (Exception e) {
      System.out.println(e.toString());
      return null;
    }
  }

  public static List<Comment> loadCommentsByHotelId(int id) {
    Query q = em.createNativeQuery("SELECT CommentID FROM [Comment] WHERE HotelID = ?");
    q.setParameter(1, id);
    List<Integer> commentIdList = q.getResultList();
    
    List<Comment> res = new ArrayList<Comment>();
    for (Integer commentId : commentIdList) {
      Comment c = getComment(commentId);
      res.add(c);
    }
    return res;
  }

  public static void main(String[] args) {
    System.out.println("test");
    System.out.println(getComment(1));
//    List<Comment> comments = CommentDAL.loadCommentsByHotelId(52);
//    System.out.println(comments);
//    System.out.println(comments.size());
//    for (Comment comment : comments) {
//      System.out.println(comment.getCommentText());
//    }
  }
}
