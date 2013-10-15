/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet.user;

import com.library.b.entities.Comment;
import com.library.b.entities.Rating;
import com.library.b.entities.User;
import com.library.dal.CommentDAL;
import com.library.dal.HotelDAL;
import com.library.dal.RatingDAL;
import com.library.dal.UserDAL;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Minh LTN
 */
public class RatingServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    // get comment, rating, hotelId 
    int rating = Integer.parseInt(request.getParameter("rating"));
    String comment = request.getParameter("comment");
    int hotelId = Integer.parseInt(request.getParameter("hotelId"));
    User user = (User) request.getSession().getAttribute("user");

    try {
      if (rating != 0) {

        Rating r = new Rating();
        r.setCreatedDate(new Date());
        r.setHotel(HotelDAL.getHotel(hotelId));
        r.setRating(rating);
        r.setUser((new UserDAL()).getUser(user.getUserId()));
        RatingDAL.addRating(r);

      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    try {
      if (comment != null) {


        Comment c = new Comment();
        c.setCommentText(comment);
        c.setCreatedDate(new Date());
        c.setHotelID(HotelDAL.getHotel(hotelId));
        c.setUserID((new UserDAL()).getUser(user.getUserId()));
        CommentDAL.addComment(c);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    
    //response.sendRedirect("HotelView?hotel_id=" + hotelId);
  }
}
