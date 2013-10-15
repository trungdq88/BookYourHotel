package samples;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

public class UpdateBean {

  private Book book;
  private HttpServlet servlet;
  private DataSource ds;

  public UpdateBean(HttpServlet servlet, DataSource ds) {
    this.servlet = servlet;
    this.ds = ds;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
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

  public void update() {
    Connection conn = null;
    PreparedStatement pstmt;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(
              "UPDATE Books SET name=?, price=? WHERE id=?");
      pstmt.setString(1, book.getName());
      pstmt.setFloat(2, book.getPrice());
      pstmt.setString(3, book.getId());
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
