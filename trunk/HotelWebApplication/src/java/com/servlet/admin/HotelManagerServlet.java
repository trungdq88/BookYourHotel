package com.servlet.admin;

import com.library.b.entities.District;
import com.library.b.entities.Hotel;
import com.library.b.entities.Province;
import com.library.b.entities.Ward;
import com.library.dal.DistrictDAL;
import com.library.dal.HotelDAL;
import com.library.dal.ProvinceDAL;
import com.library.dal.WardDAL;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ThuHoa
 */
public class HotelManagerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Hotel> hotels = HotelDAL.getAllHotels();
        request.setAttribute("hotels", hotels);

        //get action to determine
        String action = request.getParameter("action");

        if (action == null) {
            // this line will be wrong ??
            // response.sendRedirect("WEB-INF/admin_view/hotel/view.jsp");
            request.getRequestDispatcher("WEB-INF/admin_view/hotel/view.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            /**
             *
             * delete hotel
             *
             */
            int id = Integer.parseInt(request.getParameter("id"));
            HotelDAL.removeHotel(id);
            hotels = HotelDAL.getAllHotels();
            request.setAttribute("hotels", hotels);
            request.getRequestDispatcher("WEB-INF/admin_view/hotel/view.jsp").forward(request, response);
        } else if (action.equals("insert")) {
            /*
             *
             * insert new hotel
             *
             */
            request.getRequestDispatcher("WEB-INF/admin_view/hotel/insert.jsp").forward(request, response);

        } else if (action.equals("update")) {
            /**
             *
             * edit hotel
             *
             */
            String id = request.getParameter("id");
            request.setAttribute("id", id);
            request.getRequestDispatcher("WEB-INF/admin_view/hotel/update.jsp").forward(request, response);
        } else {
            /**
             *
             * no action ==> view all hotels
             *
             */
            request.getRequestDispatcher("/WEB-INF/admin_view/hotel/view.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get action to determine
        String action = request.getParameter("submit");
        if (action == null) {
            request.getRequestDispatcher("/WEB-INF/admin_view/hotel/insert.jsp").forward(request, response);
        } else if (action.equals("Insert")) {
            /*
             * 
             * Insert hotel
             * 
             */
            Hotel insertedHotel = insertHotel(request);
            request.setAttribute("insertedHotel", insertedHotel);
            // this variable to check the first time go to insert.jsp
            request.setAttribute("check", true);
            request.getRequestDispatcher("/WEB-INF/admin_view/hotel/insert.jsp").forward(request, response);
        }
    }
    
    /**
     * This function to insert new object Hotel
     * @param request
     * @return If insert success return object Hotel , else return null
     */
    private Hotel insertHotel(HttpServletRequest request) {
        try {
                String websiteName = request.getParameter("websiteName");
                String linkDetail = request.getParameter("linkDetail");
                String hotelName = request.getParameter("hotelName");
                String description = request.getParameter("description");
                int star = Integer.parseInt(request.getParameter("star"));
                String imageLink = request.getParameter("imageLink");
                double rating = Double.parseDouble(request.getParameter("rating"));
                String otherDescription = request.getParameter("otherDescription");
                String provinceId = request.getParameter("provinceId");
                String districtId = request.getParameter("districtId");
                String wardId = request.getParameter("wardId");
                String address = request.getParameter("address");

                // create new Hotel
                // Don't touch, something is magic
                Hotel hotel = new Hotel(0);
                // get district, province and ward from id
                District district = DistrictDAL.getDistrict(districtId);
                Province province = ProvinceDAL.getProvince(provinceId);
                Ward ward = WardDAL.getWard(wardId);

                hotel.setWebsiteName(websiteName);
                hotel.setAddress(address);
                hotel.setDescription(description);
                hotel.setHotelName(hotelName);
                hotel.setImageLinks(imageLink);
                hotel.setLinkDetailURL(linkDetail);
                hotel.setOtherProperties(otherDescription);
                hotel.setRating(rating);
                hotel.setStar(star);

                hotel.setDistrictid(district);
                hotel.setProvinceid(province);
                hotel.setWardid(ward);

                hotel.setRoomList(null);
                hotel.setRatingList(null);
                hotel.setCommentList(null);

                // insert hotel
                HotelDAL.addHotel(hotel);
                return hotel;
            } catch (Exception e) {
                return null;
            }
            
    }
    
}
