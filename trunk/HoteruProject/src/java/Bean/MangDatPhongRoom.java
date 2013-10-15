/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.select.Elements;

/**
 *
 * @author ThuHoa
 */
public class MangDatPhongRoom {
  public static String getTitle(Element element){
    return element.select(".hotel-detail-path li").last().text();
  }
  
  public static String getCity(Element element){
    return element.select(".hotel-detail-path li").eq(2).text();
  }
  
  public static int getStart(Element element){
    return element.select("h1.title img").size();
  }
  
  public static String getAddress(Element element){
    return element.select("div.add").text().replace("Địa chỉ: ", "").replace(" (Bản đồ)", "");
  }
  
  public static String getDescription(Element element){
    return element.select("div.info_product p").text();
  }
  
  public static double getRating(Element element){
    return Double.parseDouble(element.select("div.evaluation h4").text());
  }
  public static void main(String[] args) throws Exception{
   Document doc = Jsoup.connect("http://www.mangdatphong.vn/hoan-kiem/khach-san-dong-thanh-ha-noi.html#availability")
  .data("query", "Java")
  .userAgent("Mozilla")
  .cookie("auth", "token")
  .timeout(3000)
  .post();
    // Read font Vietnamese
    doc.outputSettings().escapeMode(EscapeMode.xhtml);
    System.out.println(doc);
    // get title and city by path
//    Element hotel = doc.getElementById("module_72830").getElementsByClass("product_detail").first();
//    System.out.println("Title:" + getTitle(hotel));
//    System.out.println("City: " + getCity(hotel));
//    System.out.println("Start: " + getStart(hotel) );
//    System.out.println("Adress: " + getAddress(hotel));
//    System.out.println("Rating: " + getRating(hotel));
//    System.out.println("Description: " + getDescription(hotel));
//    System.out.println("---------------------------------");
    
  }
}
