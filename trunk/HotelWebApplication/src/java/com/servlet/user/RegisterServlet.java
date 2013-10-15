/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet.user;

import com.library.b.entities.Role;
import com.library.b.entities.User;
import com.library.dal.RoleDAL;
import com.library.dal.UserDAL;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ThuHoa
 */
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/user/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //go to register.jsp
        // get username, password and email
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String email = request.getParameter("email");
        Role role = RoleDAL.findRole(1);

        // create a user
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRoleID(role);
        user.setCommentList(null);
        user.setRatingList(null);
        // check username exit or not
        boolean checkUsername = UserDAL.checkUsername(username);
        // check email exit or not
        boolean checkEmail = UserDAL.checkEmail(email);
        
        // check variable to check insert success of fail
        
         boolean check = false; 
        if (checkUsername == true && checkEmail ==true && username.length() > 3  &&
                password.length() > 3  && password.equals(repassword) ){
            check = UserDAL.addUser(user);
        }
       

        if (check) {
            request.setAttribute("message", "Your account has been created!");
            request.setAttribute("linktext", "Click HERE to login");
            request.setAttribute("link", "Login");
            request.getRequestDispatcher("WEB-INF/message.jsp").forward(request, response);
        } else {
            request.setAttribute("isError", true);
            request.setAttribute("checkUsername", checkUsername);
            request.setAttribute("checkEmail", checkEmail);
            request.getRequestDispatcher("WEB-INF/user/register.jsp").forward(request, response);
        }
    }
}
