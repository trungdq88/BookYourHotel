package samples;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

public class InsertBean {

  private HttpServlet servlet;
  private DataSource ds;
  private String name;
  private float price;

  public InsertBean(HttpServlet servlet, DataSource ds) {
    this.servlet = servlet;
    this.ds = ds;
  }

  public HttpServlet getServlet() {
    return servlet;
  }

  public void setServlet(HttpServlet servlet) {
    this.servlet = servlet;
  }

  public DataSource getDs() {
    return ds;
  }

  public void setDs(DataSource ds) {
    this.ds = ds;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public void insert() {
    Connection conn = null;
    PreparedStatement pstmt;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement("INSERT INTO Books VALUES (?,?,?)");
      long date = System.currentTimeMillis();
      String code = String.format("%s%04d",
          name.substring(0, 3).toUpperCase().replace(' ', 'X'), date % 10000);
      pstmt.setString(1, code);
      pstmt.setString(2, name);
      pstmt.setFloat(3, price);
      pstmt.executeUpdate();
    } catch (Exception e) {
      servlet.log(e.getMessage());
    } finally {
      try {
        conn.close();
      } catch (Exception e) {
      }
    }
  }
}
