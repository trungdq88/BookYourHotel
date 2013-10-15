package com.library.b.entities.projecta;

import com.library.b.entities.District;
import com.library.b.entities.Hotel;
import com.library.b.entities.Ward;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-10-15T10:56:45")
@StaticMetamodel(Ward.class)
public class Ward_ { 

    public static volatile SingularAttribute<Ward, String> location;
    public static volatile SingularAttribute<Ward, String> name;
    public static volatile ListAttribute<Ward, Hotel> hotelList;
    public static volatile SingularAttribute<Ward, String> wardid;
    public static volatile SingularAttribute<Ward, String> type;
    public static volatile SingularAttribute<Ward, District> districtid;

}