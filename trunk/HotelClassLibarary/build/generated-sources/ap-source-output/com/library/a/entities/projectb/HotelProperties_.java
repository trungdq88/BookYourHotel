package com.library.a.entities.projectb;

import com.library.a.entities.HotelProperties;
import com.library.a.entities.HotelPropertiesPK;
import com.library.a.entities.Properties;
import com.library.a.entities.Website;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-10-15T10:56:25")
@StaticMetamodel(HotelProperties.class)
public class HotelProperties_ { 

    public static volatile SingularAttribute<HotelProperties, HotelPropertiesPK> hotelPropertiesPK;
    public static volatile SingularAttribute<HotelProperties, String> selector;
    public static volatile SingularAttribute<HotelProperties, String> afterRegex;
    public static volatile SingularAttribute<HotelProperties, Website> website;
    public static volatile SingularAttribute<HotelProperties, String> resultMode;
    public static volatile SingularAttribute<HotelProperties, Properties> properties;

}