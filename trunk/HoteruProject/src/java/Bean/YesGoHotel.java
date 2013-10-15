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
public class YesGoHotel {
  public static String getTitle(Element element){
    return element.select(".ogrange_color a").text();
  }
  public static String getAddress(Element element){
    return element.select(".p5b").text();
  }
  public static String getDescription(Element element){
    return element.select(".textdeription").text();
  }
  public static double getRating(Element element){
    Element e = element.select(".box_danhgia b").first();
    if (e == null){
      return 0.0;
    } else {
      return Double.parseDouble(e.text());
  }
  }
  
  public static double getPrice(Element element){
   String price = element.select(".price p").first().text().trim().replace("Từ ", "").replace(".", "");
    return Double.parseDouble(price);
  }
    public static void getHotel(String html) throws IOException {
//    File input = new File("yesgo.txt");
//    Document doc = Jsoup.parse(input, "UTF-8");
      Document doc = Jsoup.parse(html,"UTF-8");
    Elements elements = doc.getElementsByClass("box_list_content");
    for (Element element: elements){
      System.out.println(getTitle(element));
      System.out.println(getAddress(element));
      System.out.println(getDescription(element));
      System.out.println(getRating(element));
      System.out.println(getPrice(element));
      System.out.println("---------------------------");
    }
  }
}
