/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.dal;

import com.library.b.entities.RoomPrice;
import com.library.controllers.RoomPriceJpaController;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author Quang Trung
 */
public class RoomPriceDAL {

    private static final RoomPriceJpaController rjc = new RoomPriceJpaController(Persistence.createEntityManagerFactory("HotelClassWebLibraryPU"));
    private static final EntityManager em = rjc.getEntityManager();

    /**
     * Add room price, tested.
     * @param price
     * @return 
     */
    public static boolean addRoomPrice(RoomPrice price) {
        try {
            rjc.create(price);
            return true;
        } catch (Exception e) {
            System.out.println("RoomDAL.addRoom: " + e.toString());
            return false;
        }
    }
}
