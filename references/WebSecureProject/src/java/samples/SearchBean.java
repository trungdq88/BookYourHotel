package samples;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

public class SearchBean {

  private String name;
  private float min;
  private float max;
  private HttpServlet servlet;
  private DataSource ds;

  public SearchBean(HttpServlet servlet, DataSource ds) {
    this.servlet = servlet;
    this.ds = ds;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public float getMin() {
    return min;
  }

  public void setMin(float min) {
    this.min = min;
  }

  public float getMax() {
    return max;
  }

  public void setMax(float max) {
    this.max = max;
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

  public ArrayList<Book> findByName() {
    ArrayList<Book> books = new ArrayList<Book>();
    Connection conn = null;
    PreparedStatement pstmt;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement("SELECT * FROM Books WHERE name LIKE ?");
      pstmt.setString(1, "%" + name + "%");
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Book book = new Book(rs.getString("id"), rs.getString("name"), rs.getFloat("price"));
        books.add(book);
      }
    } catch (Exception e) {
      servlet.log(e.getMessage());
    } finally {
      try {
        rs.close();
        conn.close();
      } catch (Exception e) {
      }
    }
    return books;
  }

  public ArrayList<Book> findByPrice() {
    ArrayList<Book> books = new ArrayList<Book>();
    Connection conn = null;
    PreparedStatement pstmt;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement("SELECT * FROM Books WHERE price BETWEEN ? AND ?");
      pstmt.setFloat(1, min);
      pstmt.setFloat(2, max);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Book book = new Book(rs.getString("id"), rs.getString("name"), rs.getFloat("price"));
        books.add(book);
      }
    } catch (Exception e) {
      servlet.log(e.getMessage());
    } finally {
      try {
        rs.close();
        conn.close();
      } catch (Exception e) {
      }
    }
    return books;
  }
}
