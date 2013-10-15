/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet.controller;

import com.library.b.entities.Role;
import com.library.b.entities.User;
import com.library.dal.UserDAL;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Minh LTN
 */
public class RegisterServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    request.getRequestDispatcher("WEB-INF/view/register.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = request.getParameter("Username");
    String password = request.getParameter("Password");
    String repassword = request.getParameter("RePassword");
    String email = request.getParameter("Email");

    User user = new User(1);
    user.setUsername(username);
    user.setPassword(password);
    user.setEmail(email);
    
    Role role = new Role(0);
    role.setRoleID(0);
    user.setRoleID(role);
    
    UserDAL.addUser(user);

  }
}
