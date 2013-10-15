/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Currency;
import java.util.Locale;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.select.Elements;

/**
 *
 * @author ThuHoa
 */
public class MangDatPhongHotel {
  public static String getTitle(Element element) {
    //return element.getElementsByTag("a").attr("title");
    return element.select("a").attr("title");
  }
  public static String getAddress(Element element) {
//    element.getElementsByClass("add").first().getElementsByTag("span").remove();
    element.select(".add span").remove();
    return element.getElementsByClass("add").first().text();
  }
  public static int getRating(Element element){
//    return element.getElementsByClass("rating").first().getElementsByTag("img").size();
   return element.select(".rating img").size();
  }
  
  public static String getDescription(Element element){
    return element.select(".brief").text();
  }
  
  public static double getPrice(Element element){
    String a = element.select(".rates span.rate-bound").first().text().trim().replace(".","");
    return Double.parseDouble(a);//1.000,678: 1,000.567
  }
  
  public static String getCurrency(Element element){
    return element.select("span.site-currency").text();
  }
 
  public static void getHotel(String html) throws IOException {
//    File input = new File("sample.txt");
//    Document doc = Jsoup.parse(input, "UTF-8");
//    Document doc = Jsoup.connect("http://www.mangdatphong.vn/khach-san-tai-ha-noi.html")
//  .data("query", "Java")
//  .userAgent("Mozilla")
//  .cookie("auth", "token")
//  .timeout(3000)
//  .post();
    Document doc = Jsoup.parse(html, "UTF-8");
    Elements elements = doc.getElementsByClass("products");
    for(Element element : elements){
      String title = getTitle(element);
      System.out.println(title);
      System.out.println(getAddress(element));
      System.out.println("Rating: " + getRating(element));
      System.out.println(getDescription(element));
      System.out.println(getPrice(element));
      System.out.println(getCurrency(element));
      System.out.println("-----------------------------------");
    }
//      Document doc=  Jsoup.parse(new URL("http://www.chudu24.com/khachsan/phu-quoc.html/").openStream(), "utf-8","http://www.chudu24.com/khachsan/phu-quoc.html/");  
//      doc.outputSettings().escapeMode(EscapeMode.xhtml);  
//  .data("query", "Java")
//  .userAgent("Mozilla")
//  .cookie("auth", "token")
//  .timeout(3000)
//  .post();
//      Element titlle = doc.createElement("p");
//      titlle.append("thu hoa");
//      String html = "<div><p>Lorem ipsum.</p>";
//Document doc1 = Jsoup.parseBodyFragment(html);
//      Element body = doc.body();
  }
}
