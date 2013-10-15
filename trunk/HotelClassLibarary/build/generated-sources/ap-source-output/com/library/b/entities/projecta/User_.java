package com.library.b.entities.projecta;

import com.library.b.entities.Comment;
import com.library.b.entities.Rating;
import com.library.b.entities.Role;
import com.library.b.entities.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-10-15T10:56:25")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> username;
    public static volatile SingularAttribute<User, String> email;
    public static volatile ListAttribute<User, Rating> ratingList;
    public static volatile SingularAttribute<User, Integer> userId;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, Role> roleID;
    public static volatile ListAttribute<User, Comment> commentList;

}