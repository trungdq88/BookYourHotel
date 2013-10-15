/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet.controller;

import com.library.b.entities.District;
import com.library.b.entities.Hotel;
import com.library.b.entities.Province;
import com.library.b.entities.Ward;
import com.library.dal.DistrictDAL;
import com.library.dal.HotelDAL;
import com.library.dal.ProvinceDAL;
import com.library.dal.WardDAL;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Quang Trung
 */
public class SearchServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        try {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet SearchServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet SearchServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        } finally {            
//            out.close();
//        }
//    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        String province = request.getParameter("province");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        if (province != null || district != null || ward != null) {
            String checkin = request.getParameter("checkin");
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();
            try {
                date = format.parse(checkin);
            } catch (Exception e) {
            }
            List<Hotel> hotels;
            Province p = ProvinceDAL.getProvince(province);
            District d = DistrictDAL.getDistrict(district);
            Ward w = WardDAL.getWard(ward);

            int perPage = 9;
            int pageNum;


            // Get result count
            int resultSize;
            if (w != null) { // If user search for a specific province
                resultSize = HotelDAL.getHotelsByWardAndDateCount(w, date);
            } else if (d != null) {
                resultSize = HotelDAL.getHotelsByDistrictAndDateCount(d, date);
            } else if (p != null) {
                resultSize = HotelDAL.getHotelsByProvinceAndDateCount(p, date);
            } else { // If no province is selected
                resultSize = HotelDAL.getHotelsByDateCount(date);
            }

            // Get number of pages
            pageNum = resultSize / perPage;


            // Get current page
            String curPageString = request.getParameter("curPage");
            int curPage = 1;
            if (curPageString != null) {
                curPage = Integer.parseInt(curPageString);
            }
            int prevPage = curPage > 1 ? curPage - 1 : 1;
            int nextPage = curPage < pageNum ? curPage + 1 : pageNum;


            // Get hotel result
            if (w != null) { // If user search for a specific province
                hotels = HotelDAL.getHotelsByWardAndDate(w, date, curPage, perPage);
            } else if (d != null) {
                hotels = HotelDAL.getHotelsByDistrictAndDate(d, date, curPage, perPage);
            } else if (p != null) {
                hotels = HotelDAL.getHotelsByProvinceAndDate(p, date, curPage, perPage);
            } else { // If no province is selected
                hotels = HotelDAL.getHotelsByDate(date, curPage, perPage);
            }

            request.setAttribute("hotels", hotels);
            request.setAttribute("pageNum", pageNum);

            request.setAttribute("curPage", curPage);
            request.setAttribute("prevPage", prevPage);
            request.setAttribute("nextPage", nextPage);

            request.setAttribute("checkin", checkin);
            request.setAttribute("province", province == null ? "" : province);
            request.setAttribute("district", district == null ? "" : district);
            request.setAttribute("ward", ward == null ? "" : ward);

            request.getRequestDispatcher("WEB-INF/view/search/searchresult.jsp").forward(request, response);
        } else {
            response.sendRedirect("HotelView");
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
