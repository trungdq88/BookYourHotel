/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.dal;

import com.library.b.entities.Hotel;
import com.library.b.entities.Room;
import com.library.b.entities.RoomPrice;
import com.library.controllers.RoomJpaController;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Quang Trung
 */
public class RoomDAL {

    private static final RoomJpaController rjc = new RoomJpaController(Persistence.createEntityManagerFactory("HotelClassWebLibraryPU"));
    private static final EntityManager em = rjc.getEntityManager();

    /**
     * Get room by name, in a hotel, used to compare if the room is exits
     * @param hotel
     * @param name
     * @return 
     */
    public static Room getRoomByName(Hotel hotel, String name) {
        // First list all room in the hotel
//        TypedQuery<Room> q = em.createQuery("SELECT hp FROM Properties p, HotelProperties hp WHERE hp.hotelPropertiesPK.websiteID = ?1 AND hp.hotelPropertiesPK.propertyID = p.propertyID", Room.class);
        TypedQuery<Room> q = em.createQuery("SELECT r FROM Room r WHERE r.hotelID.hotelID = ?1 AND r.roomName = ?2", Room.class);
        q.setParameter(1, hotel.getHotelID());
        q.setParameter(2, name);

        List<Room> results = q.getResultList();
        if (results.size() > 0) {
            return results.get(0);
        } else {
            return null;
        }
    }

    /**
     * Get a room by id
     * @param id
     * @return 
     */
    public static Room getRoom(int id) {
        TypedQuery<Room> query = em.createNamedQuery("Room.findByRoomID", Room.class);
        query.setParameter("roomID", id);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Add a room, included RoomPrices, Tested
     * @param room
     * @return 
     */
    public static boolean addRoom(Room room) {
        try {
            // Do the same operation with addHotel
            
            // First get all RoomPrice
            List<RoomPrice> prices = room.getRoomPriceList();
            room.setRoomPriceList(null);
            
            // Insert the room
            rjc.create(room);
            
            // Insert all RoomPrice to database
            for (RoomPrice price: prices) {
                RoomPriceDAL.addRoomPrice(price);
            }
            return true;
        } catch (Exception e) {
            System.out.println("RoomDAL.addRoom: " + e.toString());
            return false;
        }
    }
}
