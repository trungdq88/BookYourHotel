package com.library.b.entities.projectb;

import com.library.b.entities.Hotel;
import com.library.b.entities.Room;
import com.library.b.entities.RoomPrice;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-10-15T10:56:45")
@StaticMetamodel(Room.class)
public class Room_ { 

    public static volatile SingularAttribute<Room, String> roomName;
    public static volatile SingularAttribute<Room, String> description;
    public static volatile SingularAttribute<Room, String> imageLinks;
    public static volatile SingularAttribute<Room, String> otherProperties;
    public static volatile ListAttribute<Room, RoomPrice> roomPriceList;
    public static volatile SingularAttribute<Room, Integer> capicity;
    public static volatile SingularAttribute<Room, Integer> roomID;
    public static volatile SingularAttribute<Room, Hotel> hotelID;

}