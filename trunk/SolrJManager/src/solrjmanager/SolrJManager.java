/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package solrjmanager;

import com.library.dal.HotelDAL;
import java.io.IOException;
import org.apache.solr.client.solrj.SolrServerException;

/**
 *
 * @author Quang Trung
 */
public class SolrJManager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, SolrServerException {
        //SolrjPopulator.createIndex();
        SolrjPopulator.search();
        //System.out.println("size: " + HotelDAL.getAllHotels());
    }
}