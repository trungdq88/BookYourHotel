package com.library.a.entities.projectb;

import com.library.a.entities.Properties;
import com.library.a.entities.RoomProperties;
import com.library.a.entities.RoomPropertiesPK;
import com.library.a.entities.Website;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-10-15T10:56:25")
@StaticMetamodel(RoomProperties.class)
public class RoomProperties_ { 

    public static volatile SingularAttribute<RoomProperties, String> selector;
    public static volatile SingularAttribute<RoomProperties, String> afterRegex;
    public static volatile SingularAttribute<RoomProperties, Website> website;
    public static volatile SingularAttribute<RoomProperties, RoomPropertiesPK> roomPropertiesPK;
    public static volatile SingularAttribute<RoomProperties, String> resultMode;
    public static volatile SingularAttribute<RoomProperties, Properties> properties;

}