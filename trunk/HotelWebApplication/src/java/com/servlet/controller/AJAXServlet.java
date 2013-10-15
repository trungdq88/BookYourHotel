/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet.controller;

import com.webapp.helper.GeoHelper;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Huynh Quang Thao
 */
@WebServlet(name = "AJAXServlet", urlPatterns = {"/AJAXServlet"})
public class AJAXServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        
        String res = GeoHelper.getWardsByDistrict("Ba Đình");
        out.println(res);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        String type = request.getParameter("type");
        if (type == null) {
            return;
        }

        if (type.equals("search_province")) {
            String provinceStr = request.getParameter("province");
            String res = GeoHelper.getProvinceByFragmentName(provinceStr);
            out.println(res);
            return;
        }

        if (type.equals("search_district")) {
            String provinceStr = request.getParameter("province");
            String res = GeoHelper.getDistrictsByProvince(provinceStr);
            out.println(res);
            return;
        }

        if (type.equals("search_ward")) {
            String districtStr = request.getParameter("district");
            String res = GeoHelper.getWardsByDistrict(districtStr);
            out.println(res);
            return;
        }

        out.println();

    }
}
