package com.crawler.business;

import com.library.a.entities.Website;


/**
 * Contains interface that both Crawler and Parser need to be done
 * @author Huynh Quang Thao
 */
public interface IWork {
    void doWork(Website site);
    void doAllWorks();
}
