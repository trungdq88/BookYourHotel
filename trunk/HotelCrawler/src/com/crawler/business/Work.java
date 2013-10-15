package com.crawler.business;

import com.library.a.entities.LinkDetail;
import com.library.a.entities.Website;
import com.library.b.entities.Hotel;
import com.library.dal.HotelDAL;
import com.library.dal.LinkDetailDAL;
import com.library.dal.WebsiteDAL;
import com.library.helper.CommonObjConverter;
import com.library.projecta.midclasses.ParsedHotel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Work implements IWork {

    /**
     * this is main method will be called outside like API will crawl whole
     * webpages and return a database of Hotel works like pipeline : from step
     * to step Crawler -> Parser -> Normalization -> Database
     */
    @Override
    public void doWork(Website site) {
        // TrungDQ: Comment these two lines to parse without crawl
//        CrawlerPipeline crawler = CrawlerPipeline.getInstance();
//        crawler.doWork(site);

        List<LinkDetail> linksList = (new LinkDetailDAL()).getLinksByWebsite(site);

        // move to Parser pipeline
        ParserPipeline parser = ParserPipeline.getInstance();
        List<ParsedHotel> parsedHotels = parser.doWork(linksList, site);

        // convert parsted hotel to normal hotel
        List<Hotel> hotels = new ArrayList<>();
        for (ParsedHotel parstedHotel : parsedHotels) {
            Hotel hotel = null;
            try {
                hotel = CommonObjConverter.toHotel(parstedHotel);
            } catch (Exception ex) {
                Logger.getLogger(Work.class.getName()).log(Level.SEVERE, null, ex);
            }
            hotels.add(hotel);
        }


        // normalization those hotel
        NormalizationPipeline engine = NormalizationPipeline.getInstance();
        hotels = engine.doWork(hotels);

        System.out.println("Hotels:::: " + hotels);
        // save those hotel to database
        for (Hotel hotel : hotels) {
            try {
                if (!hotel.getHotelName().equals("0")) {
                    HotelDAL.addHotel(hotel);
                }
            } catch (Exception ex) {
                Logger.getLogger(Work.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void doAllWorks() {
        WebsiteDAL websiteDal = new WebsiteDAL();
        List<Website> websites = websiteDal.getAllWebsites();
        System.out.println("Total: " + websites.size() + " websites in database");
        for (Website website : websites) {
            doWork(website);
        }
    }
}
