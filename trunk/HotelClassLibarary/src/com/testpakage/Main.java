package com.testpakage;

import com.library.dal.HotelDAL;
import com.library.dal.WardDAL;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        /*
         PropertiesDAL pd = new PropertiesDAL();
         List<HotelProperties> listProp = pd.getHotelProperties(null);
        
         System.out.println("getName:" + listProp.get(0).getProperties().getName());
         System.out.println("getDescription:" + listProp.get(0).getProperties().getDescription());
         System.out.println("getWebsiteName:" + listProp.get(0).getWebsite().getWebsiteName());
         System.out.println("getSelector:" + listProp.get(0).getSelector());
         System.out.println("getResultMode:" + listProp.get(0).getResultMode());
         System.out.println("getAfterRegex:" + listProp.get(0).getAfterRegex());
        
        
         WebsiteDAL wd = new WebsiteDAL();
         List<Website> listWeb = wd.getAllWebsites();
         System.out.println("Website: " + listWeb.size());
        
         Website a = wd.getWebsiteByID(1);
         System.out.println("Web 1: " + a.getWebsiteName());
        
         LinkDetail ld = new LinkDetail();
         ld.setUrl("abc");
         ld.setWebsiteID(a);
        
         LinkDetailDAL ldd = new LinkDetailDAL();
         ldd.insertLinkDetail(ld);
        
         HotelDAL hd = new HotelDAL();
         List<Hotel> list = hd.getAllHotels();
         System.out.println("Hotel objects: " + list.size());
         */


        /**
         * Snippet to create new Comment
         */
//        Comment c = new Comment();
//        c.setCommentText("comment text");
//        c.setCreatedDate(new Date());
//        c.setHotelID(HotelDAL.getHotel(1));
//        c.setUserID(UserDAL.getUser(1));
//        CommentDAL.addComment(c);
        /**
         * Snippet to create new Rating
         */
//        Rating r = new Rating();
//        r.setCreatedDate(new Date());
//        r.setHotel(HotelDAL.getHotel(1));
//        r.setRating(4);
//        r.setUser(UserDAL.getUser(1));
//        RatingDAL.addRating(r);
        /**
         * Snippet to insert RoomPrice
         */
//        RoomPrice price = new RoomPrice();
//        price.setCheckedIn("10/15/2013");
//        price.setCheckOut("10/16/2013");
//        price.setPrice(123.23);
//        price.setRoomID(RoomDAL.getRoom(1));
//        System.out.println("roompricetest: " + RoomPriceDAL.addRoomPrice(price));
        /**
         * Snippet to insert a room
         */
//        //// Room
//        Room r = new Room();
//        r.setCapicity(4);
//        r.setDescription("Description");
//        r.setHotelID(HotelDAL.getHotel(1));
//        r.setImageLinks("links");
//        r.setOtherProperties("others");
//        r.setRoomName("name");
//        
//        //// Room prices for r
//        List<RoomPrice> prices = new ArrayList<RoomPrice>();
//        RoomPrice rp = new RoomPrice();
//        rp.setCheckOut("10/15/2013");
//        rp.setCheckedIn("10/16/2013");
//        rp.setPrice(323.2);
//        rp.setRoomID(r);
//        
//        RoomPrice rp2 = new RoomPrice();
//        rp2.setCheckOut("10/16/2013");
//        rp2.setCheckedIn("10/17/2013");
//        rp2.setPrice(323.2);
//        rp2.setRoomID(r);
//        
//        
//        prices.add(rp);
//        prices.add(rp2);
//        //// END Roomo prices for r
//        
//        r.setRoomPriceList(prices);
//        RoomDAL.addRoom(r);
//        //// END Room
        /**
         * Snippet to insert a hotel
         */
//        
//        Hotel h = new Hotel();
//        h.setAddress("adrress nao do");
//        h.setDescription("description");
//        h.setHotelName("Hotel name");
//        h.setImageLinks("links");
//        h.setLinkDetailURL("http://abc.com.vn");
//        h.setOtherProperties("ohter");
//        h.setRating(6.2);
//        h.setStar(3);
//        h.setWebsiteName("website");
//        
//        h.setProvinceid(ProvinceDAL.getProvince("01"));
//        h.setDistrictid(DistrictDAL.getDistrict("001"));
//        h.setWardid(WardDAL.getWard("00001"));
//        
//        //// Room
//        List<Room> rooms = new ArrayList<Room>();
//        Room r = new Room();
//        r.setCapicity(4);
//        r.setDescription("Description");
//        r.setHotelID(h);
//        r.setImageLinks("links");
//        r.setOtherProperties("others");
//        r.setRoomName("name");
//        
//        //// Room prices for r
//        List<RoomPrice> prices = new ArrayList<RoomPrice>();
//        RoomPrice rp = new RoomPrice();
//        rp.setCheckOut(Helper.getToday());
//        rp.setCheckedIn(Helper.getTomorow());
//        rp.setPrice(323.2);
//        rp.setRoomID(r);
//        
//        RoomPrice rp2 = new RoomPrice();
//        rp2.setCheckOut(Helper.getToday());
//        rp2.setCheckedIn(Helper.getTomorow());
//        rp2.setPrice(323.2);
//        rp2.setRoomID(r);
//        
//        
//        prices.add(rp);
//        prices.add(rp2);
//        //// END Roomo prices for r
//        
//        r.setRoomPriceList(prices);
//        rooms.add(r);
//        //// END Room
//        
//        h.setRoomList(rooms); // Set a room with 2 prices
//        
//        
//        HotelDAL.addHotel(h);
//        String checkin = "10/15/2013";
//        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
//        Date date = new Date();
//        try {
//            date = format.parse(checkin);
//        } catch (Exception e) {
//        }
//        System.out.println("hotels: " + HotelDAL.getHotelsByDate(date));
//        List<Province> list = ProvinceDAL.getAllProvincesByHotelCount();
//        System.out.println("province: " + list.get(0).getName());
//        System.out.println("size: " + HotelDAL.getHotelsByWardAndDate(WardDAL.getWard("00001"), new Date(), 1, 10));
    }
}
