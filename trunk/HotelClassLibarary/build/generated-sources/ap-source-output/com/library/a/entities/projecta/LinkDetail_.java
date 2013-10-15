package com.library.a.entities.projecta;

import com.library.a.entities.LinkDetail;
import com.library.a.entities.Website;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2013-10-15T10:56:45")
@StaticMetamodel(LinkDetail.class)
public class LinkDetail_ { 

    public static volatile SingularAttribute<LinkDetail, Integer> linkDetailID;
    public static volatile SingularAttribute<LinkDetail, String> url;
    public static volatile SingularAttribute<LinkDetail, Website> websiteID;

}