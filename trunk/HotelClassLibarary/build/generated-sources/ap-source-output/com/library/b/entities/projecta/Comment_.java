package com.library.b.entities.projecta;

import com.library.b.entities.Comment;
import com.library.b.entities.Hotel;
import com.library.b.entities.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-10-15T10:56:26")
@StaticMetamodel(Comment.class)
public class Comment_ { 

    public static volatile SingularAttribute<Comment, String> commentText;
    public static volatile SingularAttribute<Comment, User> userID;
    public static volatile SingularAttribute<Comment, Integer> commentID;
    public static volatile SingularAttribute<Comment, Date> createdDate;
    public static volatile SingularAttribute<Comment, Hotel> hotelID;

}