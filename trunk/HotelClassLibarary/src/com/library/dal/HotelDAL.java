/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.dal;

import com.library.b.entities.District;
import com.library.b.entities.Hotel;
import com.library.b.entities.Province;
import com.library.b.entities.Room;
import com.library.b.entities.RoomPrice;
import com.library.b.entities.Ward;
import com.library.controllers.HotelJpaController;
import com.library.helper.Helper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

/**
 *
 * @author Quang Trung
 */
public class HotelDAL {

    private static HotelJpaController ldjc = new HotelJpaController(Persistence.createEntityManagerFactory("HotelClassWebLibraryPU"));
    private static EntityManager em = ldjc.getEntityManager();

    /**
     * Get all Hotel with offset and limit
     *
     * @return List of Hotel Object
     */
    public static List<Hotel> getAllHotels(int offset, int limit) {
        TypedQuery<Hotel> query = em.createNamedQuery("Hotel.findAll", Hotel.class);
        if (limit > 0) {
            query.setMaxResults(limit);
        }
        List<Hotel> results = query.getResultList();
        return results;
    }

    /**
     * Overload 1: Get ALL hotel
     *
     * @return
     */
    public static List<Hotel> getAllHotels() {
        return getAllHotels(0, 0);
    }

    /**
     * Overload 2: get all hotel with offset
     *
     * @param offset
     * @return
     */
    public static List<Hotel> getAllHotels(int offset) {
        return getAllHotels(offset, 0);
    }

    /**
     * Get a hotel by id
     *
     * @param hotelId
     * @return object
     */
    public static Hotel getHotel(int hotelId) {
        HotelJpaController ldjc = new HotelJpaController(Persistence.createEntityManagerFactory("HotelClassWebLibraryPU"));
        EntityManager em = ldjc.getEntityManager();
        TypedQuery<Hotel> query = em.createNamedQuery("Hotel.findByHotelID", Hotel.class);
        query.setParameter("hotelID", hotelId);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get a hotel by name, used to check if the real hotel is exists
     *
     * @param name
     * @return
     */
    public static Hotel getHotelByName(String name) {
        TypedQuery<Hotel> query = em.createNamedQuery("Hotel.findByHotelName", Hotel.class);
        query.setParameter("hotelName", name);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Delete a hotel
     *
     * @param id
     */
    public static void removeHotel(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Add a hotel, include all rooms in that hotel, each room include room
     * price, synchronized for duplicated room. TESTED, BITCH!
     *
     * @param hotel
     * @return
     */
    public static boolean addHotel(Hotel hotel) {
        try {
            // Check if the hotel is exits
            Hotel checkHotel = getHotelByName(hotel.getHotelName());
            if (checkHotel != null) {

                // TrungDQ:
                // To add a hotel, just follow my instruction here:

                // First get all the room and set hotel rooms to null
                List<Room> rooms = hotel.getRoomList();
                hotel.setRoomList(null);

                // Add hotel          
                ldjc.create(hotel);

                // Add all rooms to database
                for (Room room : rooms) {
                    // If the room is exists
                    if (RoomDAL.getRoomByName(hotel, room.getRoomName()) != null) {
                        // Add new prices for existing room
                        for (RoomPrice price : room.getRoomPriceList()) {
                            RoomPriceDAL.addRoomPrice(price);
                        }
                    } else {
                        // Add this room (include prices)
                        RoomDAL.addRoom(room);
                    }
                }

            } else {
                // If this hotel is already exists, just add its room
                List<Room> rooms = hotel.getRoomList();

                for (Room room : rooms) {
                    // Re-assign foregin key to the exists hotel
                    room.setHotelID(checkHotel);

                    // If the room is exists
                    if (RoomDAL.getRoomByName(checkHotel, room.getRoomName()) != null) {
                        // Add new prices for existing room
                        for (RoomPrice price : room.getRoomPriceList()) {
                            RoomPriceDAL.addRoomPrice(price);
                        }
                    } else {
                        // Add this room (include prices)
                        RoomDAL.addRoom(room);
                    }

                }
            }

            return true;
        } catch (Exception e) {
            System.out.println("HotelDAL.addHotel: " + e.toString());
            return false;
        }
    }

    public static List<Hotel> getHotelsByDate(Date date) {
        Date nextDate = Helper.getNextDate(date);
        TypedQuery<RoomPrice> q = em.createQuery("SELECT rp FROM RoomPrice rp WHERE rp.checkedIn > ?1 AND rp.checkedIn < ?2", RoomPrice.class);
        q.setParameter(1, date, TemporalType.DATE);
        q.setParameter(2, nextDate, TemporalType.DATE);
        List<RoomPrice> rps = q.getResultList();
        List<Hotel> hotels = new ArrayList<Hotel>();
        if (rps.size() > 0) {
            for (RoomPrice rp : rps) {
                Hotel h = rp.getRoomID().getHotelID();
                if (hotels.indexOf(h) == -1) {
                    hotels.add(h);
                }
            }
            return hotels;
        } else {
            return new ArrayList<Hotel>();
        }
    }

    public static List<Hotel> getHotelsByProvince(Province p) {
        TypedQuery<Hotel> q = em.createQuery("SELECT h FROM Hotel h WHERE h.provinceid = ?1", Hotel.class);
        q.setParameter(1, p.getProvinceid());

        return q.getResultList();
    }

    public static List<Hotel> getHotelsByProvinceAndDate(Province p, Date date) {
        Date nextDate = Helper.getNextDate(date);
        //TypedQuery<RoomPrice> q = em.createQuery("SELECT rp FROM RoomPrice rp WHERE rp.checkedIn > ?1 AND rp.checkedIn < ?2", RoomPrice.class);
        Query q = em.createNativeQuery("SELECT * FROM [Hotel] WHERE HotelID IN "
                + "(SELECT DISTINCT HotelID  "
                + "FROM Room  "
                + "WHERE RoomID IN  "
                + "	(SELECT RoomID FROM RoomPrice WHERE CheckedIn > ?1 AND CheckedIn < ?2)) "
                + "AND provinceid = ?3", Hotel.class);
        q.setParameter(1, date, TemporalType.DATE);
        q.setParameter(2, nextDate, TemporalType.DATE);
        q.setParameter(3, p.getProvinceid());

        return q.getResultList();
    }

    public static List<Hotel> getHotelsByDistrictAndDate(District d, Date date) {
        Date nextDate = Helper.getNextDate(date);
        //TypedQuery<RoomPrice> q = em.createQuery("SELECT rp FROM RoomPrice rp WHERE rp.checkedIn > ?1 AND rp.checkedIn < ?2", RoomPrice.class);
        Query q = em.createNativeQuery("SELECT * FROM [Hotel] WHERE HotelID IN "
                + "(SELECT DISTINCT HotelID  "
                + "FROM Room  "
                + "WHERE RoomID IN  "
                + "	(SELECT RoomID FROM RoomPrice WHERE CheckedIn > ?1 AND CheckedIn < ?2)) "
                + "AND provinceid = ?3", Hotel.class);
        q.setParameter(1, date, TemporalType.DATE);
        q.setParameter(2, nextDate, TemporalType.DATE);
        q.setParameter(3, d.getDistrictid());

        return q.getResultList();
    }

    public static List<Hotel> getHotelsByWardAndDate(Ward w, Date date) {
        Date nextDate = Helper.getNextDate(date);
        //TypedQuery<RoomPrice> q = em.createQuery("SELECT rp FROM RoomPrice rp WHERE rp.checkedIn > ?1 AND rp.checkedIn < ?2", RoomPrice.class);
        Query q = em.createNativeQuery("SELECT * FROM [Hotel] WHERE HotelID IN "
                + "(SELECT DISTINCT HotelID  "
                + "FROM Room  "
                + "WHERE RoomID IN  "
                + "	(SELECT RoomID FROM RoomPrice WHERE CheckedIn > ?1 AND CheckedIn < ?2)) "
                + "AND provinceid = ?3", Hotel.class);
        q.setParameter(1, date, TemporalType.DATE);
        q.setParameter(2, nextDate, TemporalType.DATE);
        q.setParameter(3, w.getWardid());

        return q.getResultList();
    }

    /**
     * Get the number of result if user search for date only
     *
     * @param date
     * @return
     */
    public static int getHotelsByDateCount(Date date) {
        return getHotelsByDate(date).size();
    }

    /**
     * Get the number of result if user search for date and province
     *
     * @param p
     * @param date
     * @return
     */
    public static int getHotelsByProvinceAndDateCount(Province p, Date date) {
        return getHotelsByProvinceAndDate(p, date).size();
    }

    public static int getHotelsByDistrictAndDateCount(District d, Date date) {
        return getHotelsByDistrictAndDate(d, date).size();
    }

    public static int getHotelsByWardAndDateCount(Ward w, Date date) {
        return getHotelsByWardAndDate(w, date).size();
    }

    /**
     * Get the list of hotel in a specific page with perPage. (Date and
     * Province)
     *
     * @param p
     * @param date
     * @param curPage
     * @param perPage
     * @return
     */
    public static List<Hotel> getHotelsByProvinceAndDate(Province p, Date date, int curPage, int perPage) {
        Date nextDate = Helper.getNextDate(date);
        //TypedQuery<RoomPrice> q = em.createQuery("SELECT rp FROM RoomPrice rp WHERE rp.checkedIn > ?1 AND rp.checkedIn < ?2", RoomPrice.class);
        Query q = em.createNativeQuery("SELECT * FROM [Hotel] WHERE HotelID IN "
                + "(SELECT DISTINCT HotelID  "
                + "FROM Room  "
                + "WHERE RoomID IN  "
                + "	(SELECT RoomID FROM RoomPrice WHERE CheckedIn > ?1 AND CheckedIn < ?2)) "
                + "AND provinceid = ?3", Hotel.class);
        q.setParameter(1, date, TemporalType.DATE);
        q.setParameter(2, nextDate, TemporalType.DATE);
        q.setParameter(3, p.getProvinceid());

        // Pagination
        q.setFirstResult((curPage - 1) * perPage + 1);
        q.setMaxResults(perPage);

        return q.getResultList();
    }

    /**
     * Get the list of hotel in a specific page with perPage. (Date only)
     *
     * @param date
     * @param curPage
     * @param perPage
     * @return
     */
    public static List<Hotel> getHotelsByDate(Date date, int curPage, int perPage) {
        Date nextDate = Helper.getNextDate(date);
        //TypedQuery<Hotel> q = em.createQuery("SELECT DISTINCT rp.roomID.hotelID FROM RoomPrice rp WHERE rp.checkedIn > ?1 AND rp.checkedIn < ?2", Hotel.class);
        Query q = em.createNativeQuery("SELECT * FROM [Hotel] WHERE HotelID IN "
                + "(SELECT DISTINCT HotelID  "
                + "FROM Room  "
                + "WHERE RoomID IN  "
                + "	(SELECT RoomID FROM RoomPrice WHERE CheckedIn > ?1 AND CheckedIn < ?2))", Hotel.class);
        q.setParameter(1, date, TemporalType.DATE);
        q.setParameter(2, nextDate, TemporalType.DATE);

        // Pagination
        q.setFirstResult((curPage - 1) * perPage + 1);
        q.setMaxResults(perPage);

        return q.getResultList();

    }

    public static List<Hotel> getHotelsByWardAndDate(Ward w, Date date, int curPage, int perPage) {
        Date nextDate = Helper.getNextDate(date);
        //TypedQuery<RoomPrice> q = em.createQuery("SELECT rp FROM RoomPrice rp WHERE rp.checkedIn > ?1 AND rp.checkedIn < ?2", RoomPrice.class);
        Query q = em.createNativeQuery("SELECT * FROM [Hotel] WHERE HotelID IN "
                + "(SELECT DISTINCT HotelID  "
                + "FROM Room  "
                + "WHERE RoomID IN  "
                + "	(SELECT RoomID FROM RoomPrice WHERE CheckedIn > ?1 AND CheckedIn < ?2)) "
                + "AND wardid = ?3", Hotel.class);
        q.setParameter(1, date, TemporalType.DATE);
        q.setParameter(2, nextDate, TemporalType.DATE);
        q.setParameter(3, w.getWardid());

        // Pagination
        q.setFirstResult((curPage - 1) * perPage + 1);
        q.setMaxResults(perPage);

        return q.getResultList();
    }

    public static List<Hotel> getHotelsByDistrictAndDate(District d, Date date, int curPage, int perPage) {
        Date nextDate = Helper.getNextDate(date);
        //TypedQuery<RoomPrice> q = em.createQuery("SELECT rp FROM RoomPrice rp WHERE rp.checkedIn > ?1 AND rp.checkedIn < ?2", RoomPrice.class);
        Query q = em.createNativeQuery("SELECT * FROM [Hotel] WHERE HotelID IN "
                + "(SELECT DISTINCT HotelID  "
                + "FROM Room  "
                + "WHERE RoomID IN  "
                + "	(SELECT RoomID FROM RoomPrice WHERE CheckedIn > ?1 AND CheckedIn < ?2)) "
                + "AND districtid = ?3", Hotel.class);
        q.setParameter(1, date, TemporalType.DATE);
        q.setParameter(2, nextDate, TemporalType.DATE);
        q.setParameter(3, d.getDistrictid());

        // Pagination
        q.setFirstResult((curPage - 1) * perPage + 1);
        q.setMaxResults(perPage);

        return q.getResultList();
    }
}
