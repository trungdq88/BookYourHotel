/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.dal;

import com.library.b.entities.Rating;
import com.library.controllers.RatingJpaController;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author Quang Trung
 */
public class RatingDAL {

  private static final RatingJpaController rjc = new RatingJpaController(Persistence.createEntityManagerFactory("HotelClassWebLibraryPU"));
  private static final EntityManager em = rjc.getEntityManager();

  public static boolean addRating(Rating r) {
    try {
      rjc.create(r);
      return true;
    } catch (Exception e) {
      System.out.println("RatingDAL.addRating: " + e.toString());
      return false;
    }

  }
}

