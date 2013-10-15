package com.library.a.entities.projecta;

import com.library.a.entities.HotelProperties;
import com.library.a.entities.LinkDetail;
import com.library.a.entities.RoomProperties;
import com.library.a.entities.Website;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-10-15T10:56:25")
@StaticMetamodel(Website.class)
public class Website_ { 

    public static volatile SingularAttribute<Website, String> checkInParaName;
    public static volatile SingularAttribute<Website, String> crawlerCheckContent;
    public static volatile SingularAttribute<Website, String> crawlerSeed;
    public static volatile ListAttribute<Website, RoomProperties> roomPropertiesList;
    public static volatile SingularAttribute<Website, Integer> websiteID;
    public static volatile SingularAttribute<Website, String> homepage;
    public static volatile ListAttribute<Website, LinkDetail> linkDetailList;
    public static volatile SingularAttribute<Website, Integer> crawlerIDFrom;
    public static volatile SingularAttribute<Website, String> checkOutParaName;
    public static volatile SingularAttribute<Website, String> websiteName;
    public static volatile SingularAttribute<Website, String> crawlerMatch;
    public static volatile SingularAttribute<Website, Integer> useCookie;
    public static volatile SingularAttribute<Website, Integer> crawlerIDTo;
    public static volatile SingularAttribute<Website, String> crawlerIDLink;
    public static volatile SingularAttribute<Website, String> dateFormat;
    public static volatile ListAttribute<Website, HotelProperties> hotelPropertiesList;
    public static volatile SingularAttribute<Website, String> requestMethod;
    public static volatile SingularAttribute<Website, String> otherParaNames;

}