package com.crawler.helper;

import com.library.a.entities.HotelProperties;
import com.library.a.entities.RoomProperties;
import com.library.a.entities.Website;
import com.library.dal.PropertiesDAL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

/**
 * Some Helper function for parsing HTML Page
 *
 * @author Huynh Quang Thao
 */
public class Helper {

    public static Map<String, String> getParametersFromRequestString(String otherParameters) {
        //split each property separated by "&"
        List<String> elephantList = Arrays.asList(otherParameters.split("&"));
        Map<String, String> map = new HashMap<>();
        for (String element : elephantList) {
            String[] tokens = element.split("=");
            if (tokens.length != 2) {
                return new HashMap<>();
            }
            //map.put(element.substring(0, index), element.substring(index, element.length()));
            map.put(tokens[0], tokens[1]);
        }

        return map;
    }

    public static Map<String, String> getCookies(String webpage) {
        try {
            Connection.Response res = Jsoup.connect(webpage)
                    .method(Connection.Method.GET)
                    .execute();
            return res.cookies();
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    public static String getSelectorByPropertyName(Website site, String name) {
        List<HotelProperties> properties = (new PropertiesDAL()).getHotelProperties(site);
        for (HotelProperties property : properties) {
            if (property.getProperties().getName().equals(name)) {
                return property.getSelector();
            }
        }
        return null;
    }

    public static HotelProperties HotelToRoomPropertyAdapter(RoomProperties roomProperty) {
        HotelProperties hotelProperty = new HotelProperties();
        hotelProperty.setWebsite(roomProperty.getWebsite());
        hotelProperty.setProperties(roomProperty.getProperties());
        hotelProperty.setResultMode(roomProperty.getResultMode());
        hotelProperty.setSelector(roomProperty.getSelector());
        hotelProperty.setAfterRegex(roomProperty.getAfterRegex());
        return hotelProperty;
    }
}
