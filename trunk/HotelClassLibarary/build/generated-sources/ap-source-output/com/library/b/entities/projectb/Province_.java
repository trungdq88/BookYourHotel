package com.library.b.entities.projectb;

import com.library.b.entities.District;
import com.library.b.entities.Hotel;
import com.library.b.entities.Province;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-10-15T10:56:45")
@StaticMetamodel(Province.class)
public class Province_ { 

    public static volatile ListAttribute<Province, District> districtList;
    public static volatile SingularAttribute<Province, String> name;
    public static volatile ListAttribute<Province, Hotel> hotelList;
    public static volatile SingularAttribute<Province, String> provinceid;
    public static volatile SingularAttribute<Province, String> type;

}