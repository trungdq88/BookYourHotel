/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author ThuHoa
 */
public class example_asia {
  public static String getTitle(Element element){
    return element.select("a.hotel").text();
  }
  
  public static double getPrice(Element element){
    String price = element.select(".right b.online").text();
    if(price.equals("")) return 0.0;
    else return Double.parseDouble(price.replace(" VND", "").replace(",", ""));
  }
  
  public static String getAddress(Element element){
    return element.select("div.address span").text();
  }
  
  public static String getDescription(Element element){
    return element.select(".left p").text();
  }
  
  public static double getRating(Element element){
    return element.select(".DivStart img").size();
  }
  public static void main(String[] args) throws IOException {
    File input = new File("asia.txt");
    Document doc = Jsoup.parse(input, "UTF-8");
    Elements elements = doc.select("ul.ListHotel li");
    for (Element element: elements){
      System.out.println(getTitle(element));
      System.out.println(getPrice(element));
      System.out.println(getAddress(element));
      System.out.println(getDescription(element));
      System.out.println("Rating: "+ getRating(element));
      System.out.println("---------------------------------");
    }
  }
}
