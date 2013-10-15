package samples;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

public class LoginBean {
  private String username;
  private String password;
  
  private HttpServlet servlet;
  private DataSource ds;

  public LoginBean(HttpServlet servlet, DataSource ds) {
    this.servlet = servlet;
    this.ds = ds;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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
  
  public Member getMember() {
    Member member = null;
    Connection conn = null;
    PreparedStatement pstmt;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement("SELECT * FROM Users WHERE username=? AND password=?");
      pstmt.setString(1, username);
      pstmt.setString(2, password);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        member = new Member(
                rs.getString("username"), rs.getString("fullname"),
                rs.getString("email"), rs.getString("address"), rs.getString("phone"));
        pstmt = conn.prepareStatement("SELECT * FROM Roles WHERE username=?");
        pstmt.setString(1, username);
        rs = pstmt.executeQuery();
        while (rs.next()) {
          member.getRoles().add(rs.getString("role"));
        }
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
    return member;
  }
}
