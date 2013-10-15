package com.library.helper;

import com.google.gson.Gson;
import com.library.b.entities.Hotel;
import com.library.b.entities.Room;
import com.library.b.entities.RoomPrice;
import com.library.projecta.midclasses.ParsedHotel;
import com.library.projecta.midclasses.ParsedRoom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Quang Trung
 */
public class CommonObjConverter {

    /**
     * Convert ParsedHotel object to Hotel object - used in Normalization
     * Process
     *
     * @param parsedHotel
     * @return Hotel object, debug
     */
    public static Hotel toHotel(ParsedHotel parsedHotel) throws Exception {
        // real hotel object in database
        Hotel hotel = new Hotel();
        
        // get all properties of parsedHotel that real Hotel need 
        Map<String, String> props = parsedHotel.getProperties();
        
        props.put("HotelName", ignoreNull(props.get("HotelName")));
        props.put("Address", ignoreNull(props.get("Address")));
        props.put("Region", ignoreNull(props.get("Region")));
        props.put("Description", ignoreNull(props.get("Description")));
        props.put("Stars", ignoreNull(props.get("Stars")));
        props.put("Images", ignoreNull(props.get("Images")));
        props.put("Rating", ignoreNull(props.get("Rating")));
        props.put("Images", ignoreNull(props.get("Images")));

        //Validate properties
        int stars = 0;
        double rating = 0;
        try {
            stars = Integer.parseInt(props.get("Stars"));
            rating = Double.parseDouble(props.get("Rating"));
        } catch (Exception e) {
            System.out.println("Parse failed! stars = [" + props.get("Stars") + "] rating = [" + props.get("Rating") + "]");
        }

        //Room Objects
        ArrayList<Room> rooms = new ArrayList<Room>();
        List<ParsedRoom> parsedRooms = parsedHotel.getRooms();
        for (ParsedRoom parsedRoom : parsedRooms) {
            rooms.add(toRoom(parsedRoom, hotel));
        }

        //Other properties
        Gson gson = new Gson();
        String otherProps = gson.toJson(props);

        //Set attribute for hotel
        hotel.setAddress(props.get("Address"));
        hotel.setDescription(props.get("Description"));
        hotel.setHotelName(props.get("HotelName"));
        hotel.setImageLinks(props.get("Images"));
        
        // TODO: Shit, how :|
        hotel.setLinkDetailURL( parsedHotel.getLinkUrl()); 
        hotel.setOtherProperties(otherProps);
        
        hotel.setRating(rating);
        hotel.setStar(stars);
        hotel.setRoomList(rooms);
        hotel.setWebsiteName(parsedHotel.getWebsiteName());
        
        /// something fucking here
        hotel.setDistrictid(null);
        hotel.setHotelID(0);
        hotel.setProvinceid(null);  
        hotel.setWardid(null);
            
        return hotel;
    }

    private static Room toRoom(ParsedRoom parsedRoom, Hotel hotel) throws Exception {
        Room room = new Room();
        Map<String, String> props = parsedRoom.getProperties();

        // Ignore null values
        props.put("RoomName", ignoreNull(props.get("RoomName")));
        props.put("RoomMax", ignoreNull(props.get("RoomMax")));
        props.put("RoomPrice", ignoreNull(props.get("RoomPrice")));
        props.put("RoomImages", ignoreNull(props.get("RoomImages")));
        props.put("RoomDescription", ignoreNull(props.get("RoomDescription")));

        //Validate properties
        int capacity = 0;
        double price = 0;

        capacity = Integer.parseInt(props.get("RoomMax"));
        price = Double.parseDouble(props.get("RoomPrice").replace(".", ""));


        // Other properties
        Gson gson = new Gson();
        String otherProps = gson.toJson(props);

        // get all room prices of room
        // in fact. just contains only one room price
        List<RoomPrice> prices = new ArrayList<RoomPrice>();
        RoomPrice roomPrice = new RoomPrice();
        roomPrice.setRoomID(room);
        roomPrice.setRoomPriceID(0);
        // TODO: format date : mm/dd/yyyys
        roomPrice.setCheckedIn(Helper.getToday());
        roomPrice.setCheckOut(Helper.getTomorow());
        roomPrice.setPrice(price);
        prices.add(roomPrice);
        
        room.setCapicity(capacity);
        room.setDescription(props.get("RoomDescription"));
        room.setHotelID(hotel);
        room.setImageLinks(props.get("RoomImages"));
        room.setOtherProperties(otherProps);
        room.setRoomName(props.get("RoomName"));
        room.setRoomID(0);
        room.setRoomPriceList(prices);

        return room;
    }
    
    /**
     * validate to prevent null value
     */
    private static String ignoreNull(String val) {
        return ((val == null || val.trim().equals("")) ? "0" : val.trim());
    }
}
