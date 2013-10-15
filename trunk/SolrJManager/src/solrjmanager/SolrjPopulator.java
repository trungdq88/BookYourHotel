package solrjmanager;

import com.library.b.entities.Hotel;
import com.library.dal.HotelDAL;
import java.io.IOException;
import java.util.List;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class SolrjPopulator {

    public static void createIndex() throws IOException, SolrServerException {
        List<Hotel> hotels = HotelDAL.getAllHotels();
        
        HttpSolrServer server = new HttpSolrServer("http://localhost:8983/solr");
        
        int flushCount = 100;
        int flushVar = 0;
        
        for (Hotel hotel : hotels) {
            SolrInputDocument doc = new SolrInputDocument();
            
            doc.addField("id", hotel.getHotelID());
            doc.addField("name", hotel.getHotelName());
            doc.addField("address", hotel.getAddress());
            doc.addField("description", hotel.getDescription());
            
            server.add(doc);
            if (flushVar++ % flushCount == 0) {
                server.commit();  // periodically flush
            }
        }
        server.commit();
    }

    public static void search() throws SolrServerException {
        HttpSolrServer solr = new HttpSolrServer("http://localhost:8983/solr");

        SolrQuery query = new SolrQuery();
        query.setQuery("khach sn");
       
        query.setRows(20);
        
        QueryResponse response = solr.query(query);
        SolrDocumentList results = response.getResults();
        for (int i = 0; i < results.size(); ++i) {
            System.out.println(results.get(i));
        }
    }
}