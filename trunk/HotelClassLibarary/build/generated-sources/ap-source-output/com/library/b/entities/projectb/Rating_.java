package com.library.b.entities.projectb;

import com.library.b.entities.Hotel;
import com.library.b.entities.Rating;
import com.library.b.entities.RatingPK;
import com.library.b.entities.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-10-15T10:56:25")
@StaticMetamodel(Rating.class)
public class Rating_ { 

    public static volatile SingularAttribute<Rating, Integer> rating;
    public static volatile SingularAttribute<Rating, Hotel> hotel;
    public static volatile SingularAttribute<Rating, Date> createdDate;
    public static volatile SingularAttribute<Rating, User> user;
    public static volatile SingularAttribute<Rating, RatingPK> ratingPK;

}