package com.library.b.entities.projectb;

import com.library.b.entities.Comment;
import com.library.b.entities.District;
import com.library.b.entities.Hotel;
import com.library.b.entities.Province;
import com.library.b.entities.Rating;
import com.library.b.entities.Room;
import com.library.b.entities.Ward;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-10-15T10:56:45")
@StaticMetamodel(Hotel.class)
public class Hotel_ { 

    public static volatile ListAttribute<Hotel, Room> roomList;
    public static volatile SingularAttribute<Hotel, Integer> star;
    public static volatile SingularAttribute<Hotel, Province> provinceid;
    public static volatile SingularAttribute<Hotel, Integer> hotelID;
    public static volatile SingularAttribute<Hotel, String> websiteName;
    public static volatile SingularAttribute<Hotel, String> hotelName;
    public static volatile SingularAttribute<Hotel, String> address;
    public static volatile ListAttribute<Hotel, Rating> ratingList;
    public static volatile SingularAttribute<Hotel, String> description;
    public static volatile SingularAttribute<Hotel, String> imageLinks;
    public static volatile SingularAttribute<Hotel, String> otherProperties;
    public static volatile SingularAttribute<Hotel, Double> rating;
    public static volatile SingularAttribute<Hotel, Ward> wardid;
    public static volatile SingularAttribute<Hotel, District> districtid;
    public static volatile ListAttribute<Hotel, Comment> commentList;
    public static volatile SingularAttribute<Hotel, String> linkDetailURL;

}