package com.mangdatphong;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import Bean.MangDatPhongHotel;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.util.List;
import java.util.regex.Pattern;

public class MangDatPhongCrawler extends WebCrawler {
public int i = 0;
        private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" 
                                                          + "|png|tiff?|mid|mp2|mp3|mp4"
                                                          + "|wav|avi|mov|mpeg|ram|m4v|pdf" 
                                                          + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

        /**
         * You should implement this function to specify whether
         * the given url should be crawled or not (based on your
         * crawling logic).
         */
        @Override
        public boolean shouldVisit(WebURL url) {
                String href = url.getURL().toLowerCase();
                return !FILTERS.matcher(href).matches() && href.startsWith("http://www.mangdatphong.vn/");
                      //  && href.contains("ha-noi");
        }

        /**
         * This function is called when a page is fetched and ready 
         * to be processed by your program.
         */
         @Override
        public void visit(Page page){
                int docid = page.getWebURL().getDocid();
                String url = page.getWebURL().getURL();
                String domain = page.getWebURL().getDomain();
                String path = page.getWebURL().getPath();
                String subDomain = page.getWebURL().getSubDomain();
                String parentUrl = page.getWebURL().getParentUrl();
                String anchor = page.getWebURL().getAnchor();

                System.out.println("Docid: " + docid);
                System.out.println("URL: " + url);
                System.out.println("Domain: '" + domain + "'");
                System.out.println("Sub-domain: '" + subDomain + "'");
                System.out.println("Path: '" + path + "'");
                System.out.println("Parent page: " + parentUrl);
                System.out.println("Anchor text: " + anchor);
                if (page.getParseData() instanceof HtmlParseData) {
                        HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                        String text = htmlParseData.getText();
                        String html = htmlParseData.getHtml();
                        List<WebURL> links = htmlParseData.getOutgoingUrls();

                        System.out.println("Text length: " + text.length());
                        System.out.println("Html length: " + html.length());
                        System.out.println("Number of outgoing links: " + links.size());
//                        try {
//                     MangDatPhongHotel.getHotel(html);
//                  } catch (Exception e) {
//                          System.out.println(e.getMessage());
//                  }
                      
                }
                 i = i+1;
                 System.out.println(i);
                System.out.println("======= Getting URLs... ======");
        }
         
         
}