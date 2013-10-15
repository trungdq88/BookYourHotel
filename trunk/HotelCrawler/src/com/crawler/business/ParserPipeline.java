package com.crawler.business;

import com.crawler.helper.Config;
import com.crawler.helper.Helper;
import com.crawler.helper.ResultModeHelper;
import com.library.a.entities.HotelProperties;
import com.library.a.entities.LinkDetail;
import com.library.a.entities.RoomProperties;
import com.library.a.entities.Website;
import com.library.dal.PropertiesDAL;
import com.library.projecta.midclasses.ParsedHotel;
import com.library.projecta.midclasses.ParsedRoom;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Contains list of all links of a website start to parse all those links into
 * hotels object
 *
 * @author Huynh Quang Thao
 */
public class ParserPipeline {

    private static volatile ParserPipeline parser;

    private ParserPipeline() {
    }

    public static ParserPipeline getInstance() {
        if (parser == null) {
            synchronized (CrawlerPipeline.class) {
                if (parser == null) {
                    parser = new ParserPipeline();
                }
            }
        }
        return parser;
    }

    public List<ParsedHotel> doWork(List<LinkDetail> links, Website site) {
        List<ParsedHotel> list = new ArrayList<>();


        // TrungDQ: get one cookies for all link (reduce time cost)
        Map<String, String> cookies = site.getUseCookie() > 0
                ? Helper.getCookies(site.getHomepage())
                : (new HashMap<String, String>());

        // prevent duplicated
        Set<LinkDetail> linksSet = new HashSet<>();
        linksSet.addAll(links);
        for (LinkDetail link : linksSet) {
            try {
                String content = getHTMLPage(link, site, cookies);
                ParsedHotel hotel = parseHotel(link, content, site);
                System.out.println("Added: " + hotel.getProperties().get("HotelName"));
                list.add(hotel);
                // System.out.println("Address of Hotel: " + hotel.getProperties().get(Config.HOTEL_ADDRESS));
            } catch (Exception e) {
                System.out.println("Link error: ["+link.getUrl()+"] " + e.toString());
            }

        }
        return list;
    }

    // return an html page, base on GET/POST/COOKIE Method
    private String getHTMLPage(LinkDetail link, Website site, Map<String, String> cookies) {
        try {

            // A Map contains all parameters need to send to server
            Map<String, String> parameters = new HashMap<>();

            // add timeIn and timeOut for this parameter
            Calendar calendar = Calendar.getInstance();
            Date today = calendar.getTime();
            calendar.add(Calendar.DAY_OF_YEAR, Config.BOOKING_DATES); // TrungDQ: save the world!
            Date tomorrow = calendar.getTime();
            DateFormat dateFormat = new SimpleDateFormat(site.getDateFormat());
            String todayAsString = dateFormat.format(today);
            String tomorrowAsString = dateFormat.format(tomorrow);
            parameters.put(site.getCheckInParaName(), todayAsString);
            parameters.put(site.getCheckOutParaName(), tomorrowAsString);

            // add other parameters to current request
            parameters.putAll(Helper.getParametersFromRequestString(site.getOtherParaNames()));

            // add all into connection information
            // include should use cookies or not

            Connection conn = Jsoup.connect(link.getUrl()).data(parameters)
                    .cookies(cookies)
                    .userAgent("Mozilla");

            // decide to use GET or POST method
            // base on site config
            // this site maybe use AJAX. but we don't handle this case
            Document doc = null;
            switch (site.getRequestMethod().toUpperCase().trim()) {
                case "GET":
                    doc = conn.get();
                    break;
                case "POST":
                    doc = conn.post();
                    break;
                default:
                    throw new UnsupportedOperationException("this method still not suppport yet.");
            }
            return doc.toString();
        } catch (IOException ex) {
            Logger.getLogger(ParserPipeline.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    // parse html file to get hotel information
    // such as city. name. rating. ...
    private ParsedHotel parseHotel(LinkDetail link, String html, Website site) {

        // browse all attributes of website.
        // each attribute. get data base on CSS Selector and some its configuration
        List<HotelProperties> properties = (new PropertiesDAL()).getHotelProperties(site);
        ParsedHotel hotel = new ParsedHotel();
        hotel.setLinkID(link.getLinkDetailID());
        hotel.setLinkUrl(link.getUrl());
        hotel.setWebsiteID(site.getWebsiteID());
        hotel.setWebsiteName(site.getWebsiteName());

        // get all attributes of hotel
        // except attribute block type : such as Room / Comments / Promotions
        Map<String, String> mapAttrs = new HashMap<>();
        Document doc = Jsoup.parse(html, "UTF-8");
        for (HotelProperties property : properties) {
            try {
                // parse one property of page
                mapAttrs.putAll(ResultModeHelper.getPropertyValue(property, doc));
            } catch (Exception e) {
                System.out.println("parseHotel: Could not get " + property.getProperties().getName() + " property");
            }
        }

        // get all rooms of currrent hotel        
        String roomBlockSelector = Helper.getSelectorByPropertyName(site, Config.ROOM_BLOCK);
        List<ParsedRoom> rooms = parseRooms(roomBlockSelector, doc, site);

        hotel.setRooms(rooms);
        hotel.setProperties(mapAttrs);

        return hotel;
    }

    // return a list of rooms of one hotel
    private List<ParsedRoom> parseRooms(String selector, Document doc, Website site) {
        // browse all Room atrributes of this website
        // each attribute. get data base on CSS Selector and some its configuration
        // as in parseHotel function
        List<ParsedRoom> res = new ArrayList<>();
        Elements rooms = doc.select(selector);
        for (Element room : rooms) {
            ParsedRoom parsedRoom = parseRoom(room, site);
            res.add(parsedRoom);
        }
        return res;
    }

    private ParsedRoom parseRoom(Element element, Website site) {
        List<RoomProperties> properties = (new PropertiesDAL()).getRoomProperties(site);
        Document doc = Jsoup.parse(element.html(), "UTF-8");
        ParsedRoom room = new ParsedRoom();

        // get all attributes of rooms
        Map<String, String> mapAttrs = new HashMap<>();
        for (RoomProperties property : properties) {
            try {
                HotelProperties hotelPropery = Helper.HotelToRoomPropertyAdapter(property);
                // parse one property of page
                mapAttrs.putAll(ResultModeHelper.getPropertyValue(hotelPropery, doc));
            } catch (Exception e) {
                System.out.println("parseRoom: Could not get " + property.getProperties().getName() + " property");
            }
        }
        mapAttrs.put(Config.ROOM_LAST_UPDATE, new Date().toString());
        room.setProperties(mapAttrs);
        return room;
    }
}
