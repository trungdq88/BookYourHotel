package samples;

import java.util.ArrayList;

public class Member {

  private String username;
  private String fullname;
  private String email;
  private String address;
  private String phone;
  private ArrayList<String> roles = new ArrayList<String>();

  public Member(String username, String fullname, String email, String address, String phone) {
    this.username = username;
    this.fullname = fullname;
    this.email = email;
    this.address = address;
    this.phone = phone;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public ArrayList<String> getRoles() {
    return roles;
  }

  public void setRoles(ArrayList<String> roles) {
    this.roles = roles;
  }

  public boolean isAdmin() {
    for (String s : roles) {
      if (s.equals("admin")) {
        return true;
      }
    }
    return false;
  }

  public boolean isUser() {
    for (String s : roles) {
      if (s.equals("user")) {
        return true;
      }
    }
    return false;
  }
}
