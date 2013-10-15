package samples;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

public class BookBean {

  private HttpServlet servlet;
  private DataSource ds;
  private String id;
  
  public BookBean(HttpServlet servlet, DataSource ds) {
    this.servlet = servlet;
    this.ds = ds;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ArrayList<Book> getBooks() {
    Connection conn = null;
    Statement stmt;
    ResultSet rs = null;
    ArrayList<Book> books = new ArrayList<Book>();
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT * FROM Books");
      while (rs.next()) {
        Book book = new Book(
                rs.getString("id"), rs.getString("name"), rs.getFloat("price"));
        books.add(book);
      }
    } catch (Exception e) {
      servlet.log("ERROR: " + e.getMessage());
    } finally {
      try {
        rs.close();
        conn.close();
      } catch (Exception e) {
      }
    } 
    return books;
  }
  
  public Book getBook() {
    Book book = null;
    Connection conn = null;
    PreparedStatement pstmt;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement("SELECT * FROM Books WHERE id=?");
      pstmt.setString(1, id);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        book = new Book(rs.getString("id"), rs.getString("name"), rs.getFloat("price"));
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
    return book;
  }
}
