package com.library.b.entities.projecta;

import com.library.b.entities.Room;
import com.library.b.entities.RoomPrice;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-10-15T10:56:45")
@StaticMetamodel(RoomPrice.class)
public class RoomPrice_ { 

    public static volatile SingularAttribute<RoomPrice, Date> checkOut;
    public static volatile SingularAttribute<RoomPrice, Double> price;
    public static volatile SingularAttribute<RoomPrice, Integer> roomPriceID;
    public static volatile SingularAttribute<RoomPrice, Date> checkedIn;
    public static volatile SingularAttribute<RoomPrice, Room> roomID;

}