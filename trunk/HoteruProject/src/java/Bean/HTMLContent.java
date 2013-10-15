/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author ThuHoa
 */
public class HTMLContent {
  private String ulr;

  public String getUlr() {
    return ulr;
  }

  public void setUlr(String ulr) {
    this.ulr = ulr;
  }

  public HTMLContent(String ulr) {
    this.ulr = ulr;
    String contents = downloadContent(ulr);
    System.out.println("Raw contents " + contents);
  }
  
  private String downloadContent(String theUrl){
    URL  url;
    InputStream inputStr = null;
    DataInputStream dataIn;
    String tmp;
    StringBuffer stringBuf = new StringBuffer();
    try {
      url = new URL(theUrl);
      inputStr = url.openStream();
      dataIn = new DataInputStream(new BufferedInputStream(inputStr));
      while ((tmp = dataIn.readLine()) != null){
        stringBuf.append(tmp + "\n");
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    finally {
      try {
        inputStr.close();
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
    return stringBuf.toString();
  }
  public static void main(String[] args) {
    HTMLContent web1 = new HTMLContent("http://www.booking.com/");
  }
  
}
