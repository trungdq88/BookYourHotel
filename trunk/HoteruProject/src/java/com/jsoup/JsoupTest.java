/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsoup;

import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities.EscapeMode;

/**
 *
 * @author ThuHoa
 */
public class JsoupTest {
  public static void main(String[] args) throws Exception {
    String url = "http://www.yesgo.vn/vi/khach-san/viet-nam/ha-noi/khach-san-ha-noi-graceful-9700.html?in_date=19%2F09%2F2013&out_date=21%2F09%2F2013&show_price=1";
    Document doc=  Jsoup.parse(new URL(url).openStream(), "utf-8",url);  
      doc.outputSettings().escapeMode(EscapeMode.xhtml);  
      System.out.println(doc);
      
//  .data("query", "Java")
//  .userAgent("Mozilla")
//  .cookie("auth", "token")
//  .timeout(3000)
//  .post();
  }
}
