/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet.user;

import com.library.b.entities.User;
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
public class LoginServlet extends HttpServlet {

    String ref;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || password == null) {
            request.getRequestDispatcher("WEB-INF/user/login.jsp").forward(request, response);
        } else {
            User user = (new UserDAL()).checkLogin(username, password);
            if (user != null) {
                request.getSession().setAttribute("user", user);

                request.setAttribute("message", "Welcome " + user.getUsername() + "!");
                request.setAttribute("linktext", "Click HERE if you wait too long.");
                if (ref != null) {
                    request.setAttribute("link", ref);
                } else {
                    request.setAttribute("link", "HotelView");
                }
                request.getRequestDispatcher("WEB-INF/message.jsp").forward(request, response);
            } else {
                request.setAttribute("isLoginFalse", true);
                request.getRequestDispatcher("WEB-INF/user/login.jsp").forward(request, response);
            }

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/user/login.jsp").forward(request, response);
        ref = request.getParameter("ref");
    }
}
