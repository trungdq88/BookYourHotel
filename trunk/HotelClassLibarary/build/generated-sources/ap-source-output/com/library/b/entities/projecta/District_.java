package com.library.b.entities.projecta;

import com.library.b.entities.District;
import com.library.b.entities.Hotel;
import com.library.b.entities.Province;
import com.library.b.entities.Ward;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-10-15T10:56:45")
@StaticMetamodel(District.class)
public class District_ { 

    public static volatile ListAttribute<District, Ward> wardList;
    public static volatile SingularAttribute<District, String> location;
    public static volatile SingularAttribute<District, String> name;
    public static volatile ListAttribute<District, Hotel> hotelList;
    public static volatile SingularAttribute<District, Province> provinceid;
    public static volatile SingularAttribute<District, String> type;
    public static volatile SingularAttribute<District, String> districtid;

}