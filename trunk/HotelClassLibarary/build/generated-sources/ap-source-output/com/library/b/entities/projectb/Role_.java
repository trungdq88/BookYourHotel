package com.library.b.entities.projectb;

import com.library.b.entities.Role;
import com.library.b.entities.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-10-15T10:56:25")
@StaticMetamodel(Role.class)
public class Role_ { 

    public static volatile SingularAttribute<Role, String> roleName;
    public static volatile ListAttribute<Role, User> userList;
    public static volatile SingularAttribute<Role, Integer> roleID;

}