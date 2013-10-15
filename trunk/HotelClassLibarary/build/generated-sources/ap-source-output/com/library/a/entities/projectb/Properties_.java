package com.library.a.entities.projectb;

import com.library.a.entities.HotelProperties;
import com.library.a.entities.Properties;
import com.library.a.entities.RoomProperties;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-10-15T10:56:25")
@StaticMetamodel(Properties.class)
public class Properties_ { 

    public static volatile SingularAttribute<Properties, Integer> propertyID;
    public static volatile SingularAttribute<Properties, String> description;
    public static volatile SingularAttribute<Properties, String> name;
    public static volatile ListAttribute<Properties, HotelProperties> hotelPropertiesList;
    public static volatile ListAttribute<Properties, RoomProperties> roomPropertiesList;

}