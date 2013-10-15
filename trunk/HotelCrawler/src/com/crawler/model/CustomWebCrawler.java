package com.crawler.model;

import com.library.a.entities.LinkDetail;
import com.library.a.entities.Website;
import com.library.dal.LinkDetailDAL;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * CustomWebCrawler class : use to chec page and add to a list
 *
 * @author Huynh Quang Thao
 */
public class CustomWebCrawler extends WebCrawler {

    // just filter html or htm file
    private final Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
            + "|png|tiff?|mid|mp2|mp3|mp4"
            + "|wav|avi|mov|mpeg|ram|m4v|pdf"
            + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
    private Pattern SITE_PATTERN;
    private Website site;
    List<LinkDetail> links = new ArrayList<>();

    /**
     * This function is called just before starting the crawl by this crawler It
     * can be used for setting up the data structures or initializations for
     * this crawler
     */
    @Override
    public void onStart() {
        site = (Website) myController.getCustomData();
        SITE_PATTERN = Pattern.compile(site.getCrawlerMatch());
        System.out.println("onStart: Site to crawl: " + site.getHomepage());
    }

    /**
     * Check if we should visited (download this page) and process it check if
     * html/htm start with homepage (such as : www.mangdatphong.com |
     * www.google.com)
     */
    @Override
    public boolean shouldVisit(WebURL url) {
        String href = url.getURL().toLowerCase();
        //  System.out.println("hef: " + href);

        // return !FILTERS.matcher(href).matches() && href.startsWith(site.getHomepage());
        return !FILTERS.matcher(href).matches() && SITE_PATTERN.matcher(href).lookingAt();

    }

    /**
     * This function is called when a page is fetched and ready to be processed
     * Add this page into tables/database for Parser phase
     */
    @Override
    public void visit(Page page) {
        // TrungDQ: first check if the Seed is match.
        if (SITE_PATTERN.matcher(page.getWebURL().getURL()).lookingAt()) { 
            
            // create link object
            String url = page.getWebURL().getURL();
            if (url.contains("?")) url = url.substring(0, url.indexOf("?"));
            LinkDetail link = new LinkDetail();
            link.setLinkDetailID(myId);
            link.setUrl(url);
            link.setWebsiteID(site);
            links.add(link);

            // add to database
            LinkDetailDAL detailDAL = new LinkDetailDAL();
            detailDAL.insertLinkDetail(link);

            // log
            System.out.println("Add: " + link.getUrl());
        }
    }

    /**
     * This function is called just before the termination of the current
     * crawler instance. Can be used for persisting in-memory data or other
     * finalization tasks.
     */ 
    @Override
    public void onBeforeExit() {
        // save those link again to controller
        ((WebCrawlerController) myController).saveData(links);
    }
}
