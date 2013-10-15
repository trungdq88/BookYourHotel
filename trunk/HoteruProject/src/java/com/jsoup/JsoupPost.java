/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsoup;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author ThuHoa
 */
public class JsoupPost {

  public static String getTitle(Element element) {
    return element.select(".hotel-detail-path li").last().text();
  }

  public static String getCity(Element element) {
    return element.select(".hotel-detail-path li").eq(2).text();
  }

  public static int getStart(Element element) {
    return element.select("h1.title img").size();
  }

  public static String getAddress(Element element) {
    return element.select("div.add").text().replace("Địa chỉ: ", "").replace(" (Bản đồ)", "");
  }

  public static String getDescription(Element element) {
    return element.select("div.info_product p").text();
  }

  public static double getRating(Element element) {
    return Double.parseDouble(element.select("div.evaluation h4").text());
  }
  
  public static void main(String[] args) throws Exception {
    String url = "http://www.mangdatphong.vn/da-nang/khach-san-phuong-thu-da-nang.html";
    
    Connection.Response res = Jsoup.connect(url)
            .method(Method.GET)
            .execute();
    Document doc = res.parse();
    System.out.println(doc);
     res.cookies(); // you will need to check what the right cookie name is
    System.out.println(res.cookies());
    
    Document doc2 = Jsoup.connect(url)
            .cookies(res.cookies())
            .data("check_in_date", "25/09/2013")
            .data("check_out_date", "27/09/2013")
            .data("check_availability", "abc")
            .data("form_block_id", "72831")
            .get();
    System.out.println(doc2);


//    String url = "http://www.mangdatphong.vn/da-nang/khach-san-phuong-thu-da-nang.html";
//    Document doc = Jsoup.connect(url)
//            .data("check_in_date", "25/09/2013")
//            .data("check_out_date", "27/09/2013")
//            .userAgent("Mozilla")
//            .post();
//    System.out.println(doc);
    //get title and city by path
//    Element hotel = doc.getElementById("module_72830").getElementsByClass("product_detail").first();
//    System.out.println("Title:" + getTitle(hotel));
//    System.out.println("City: " + getCity(hotel));
//    System.out.println("Start: " + getStart(hotel));
//    System.out.println("Adress: " + getAddress(hotel));
//    System.out.println("Rating: " + getRating(hotel));
//    System.out.println("Description: " + getDescription(hotel));
//    System.out.println("---------------------------------");

  }
}
