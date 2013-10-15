/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet.controller;

import com.library.b.entities.Hotel;
import com.library.b.entities.Province;
import com.library.dal.HotelDAL;
import com.library.dal.ProvinceDAL;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Huynh Quang Thao
 */
public class HotelViewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        String hotelIDString = request.getParameter("hotel_id");
        if (hotelIDString == null) {
            List<Province> provinces = ProvinceDAL.getAllProvincesByHotelCount();
            List<Hotel> hotels = HotelDAL.getAllHotels(0, 12);
            request.setAttribute("today", new Date());
            request.setAttribute("hotels", hotels);
            request.setAttribute("provinces", provinces);
            
            request.getRequestDispatcher("WEB-INF/view/hotellist.jsp").forward(request, response);
        } else {
            // TODO: trycatch tranh giang ho nhap tam bay
            int hotelID = Integer.parseInt(hotelIDString);
            Hotel hotel = new Hotel();
            hotel = (new HotelDAL()).getHotel(hotelID);
            List<Hotel> hotels = HotelDAL.getAllHotels(0, 4);
            request.setAttribute("hotel", hotel);
            request.setAttribute("hotels", hotels);
            request.getRequestDispatcher("WEB-INF/view/hoteldetail.jsp").forward(request, response);
        }

    }

 
}
