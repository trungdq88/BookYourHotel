package com.crawler.model;

import com.library.a.entities.LinkDetail;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Custom CrawlController Using this to add custom data structure to store some
 * links
 * ThaoHQ
 */
public class WebCrawlerController extends CrawlController {

    // using ArrayList of LinkDetail to store Crawler Link in this sites
    // synchronized version : crawler can work on multithread
    List<LinkDetail> links = Collections.synchronizedList(new ArrayList<LinkDetail>());

    public WebCrawlerController(CrawlConfig config, PageFetcher pageFetcher,
            RobotstxtServer robotstxtServer) throws Exception {
        super(config, pageFetcher, robotstxtServer);
    }
    
    public List<LinkDetail> getLinks() {
        return links;
    }
    
    public synchronized void saveData(List<LinkDetail> list) {
        System.out.println("Add set of links: " + list.size() + " items");
        links.addAll(list);
    }
}
