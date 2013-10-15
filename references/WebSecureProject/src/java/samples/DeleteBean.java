package samples;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

public class DeleteBean {

  private HttpServlet servlet;
  private DataSource ds;
  private String id;

  public DeleteBean(HttpServlet servlet, DataSource ds) {
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void delete() {
    Connection conn = null;
    PreparedStatement pstmt;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement("DELETE FROM Books WHERE id=?");
      pstmt.setString(1, id);
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
