package com.crawler.business;

import com.crawler.model.CustomWebCrawler;
import com.crawler.model.WebCrawlerController;
import com.library.a.entities.LinkDetail;
import com.library.a.entities.Website;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * First step of Crawler
 * Crawl a webpage base on configuration and move to databases
 * @author Huynh Quang Thao
 */
public class CrawlerPipeline {
    
    private static volatile CrawlerPipeline crawler;
    
    private CrawlerPipeline() {}
    
    public static CrawlerPipeline getInstance() {
        if (crawler == null) {
            synchronized(CrawlerPipeline.class) {
                if (crawler == null) {
                    crawler = new CrawlerPipeline();
                }
            }
        }
        return crawler;
    }
    
    /**
     * give a website object
     * include all information : crawler config
     * using Crawler to generate all sub-pages into tables for Parser using
     * ThaoHQ
     */
    public List<LinkDetail> doWork(Website site){
        try {

            String crawlStorageFolder = "crawler" + site.getWebsiteName();
            int numberOfCrawlers = 4;

            // config object
            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder(crawlStorageFolder);

            // keep don't send more than 1 request per second
            config.setPolitenessDelay(1000);

            // set maximum crawler depth : -1 for unlimited
            config.setMaxDepthOfCrawling(4);

            // set max page to fetch : -1 for unlimited
            config.setMaxPagesToFetch(10000);
            
            // use normal browser : prevent some website return mobile site
            config.setUserAgentString("Mozilla");

            // This config parameter can be used to set your crawl to be resumable
            // if enable resuming feature and want to start a fresh crawl
            // we need to delete the contents of rootFolder manually
            config.setResumableCrawling(true);

            // Instantiate the controller for this crawl.
            PageFetcher pageFetcher = new PageFetcher(config);
            RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
            RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
            WebCrawlerController controller = new WebCrawlerController(config, pageFetcher, robotstxtServer);

            // add seed URL for each crawler
            String[] seeds = site.getCrawlerSeed().split(";");
            for (String seed : seeds) {
               System.out.println("Seed: " + seed);
               controller.addSeed(seed.trim());
            }
            
            // add parameters for this controller.
            // such as information about currently site crawling
            controller.setCustomData(site);

            // starting the crawler.
            // this is a blocking operation.
            // that means : our code will readh the line after when crawling finished
            controller.start(CustomWebCrawler.class, numberOfCrawlers);
            
            System.out.println("Successfully Crawl " + site.getWebsiteName());
            return controller.getLinks();
            
        } catch (Exception ex) {
            Logger.getLogger(CrawlerPipeline.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     *  auto crawl on websites in database
     */
    public void doAllWorks() {
        
    }
}
