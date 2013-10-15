package com.webapp.helper;

import com.google.gson.Gson;
import com.library.b.entities.District;
import com.library.b.entities.Province;
import com.library.b.entities.Ward;
import com.library.dal.DistrictDAL;
import com.library.dal.ProvinceDAL;
import com.library.dal.WardDAL;
import com.library.helper.Helper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *
 * @author Huynh Quang Thao
 */
public class GeoHelper {
    
    static final List<Province> allProvinces;
    static final Map<Province, List<District>> allDistricts = new HashMap<>();
    static final Map<District, List<Ward>> allWards = new HashMap<>();
    
    static {
        allProvinces = ProvinceDAL.getAllProvinces();
        List<District> allDistrictsList = DistrictDAL.getAllDistricts();
        List<Ward> allWardsList = WardDAL.getAllWards();
        
        // group districts
        for (District district : allDistrictsList) {
            Province province = district.getProvinceid();
            if (!allDistricts.containsKey(province)) {
                List<District> lst = new ArrayList<>();
                lst.add(district);
                allDistricts.put(province, lst);
            }
            else {
                List<District> lst = allDistricts.get(province);
                lst.add(district);
                allDistricts.put(province, lst);
            }
        }
        
        // group wards
        for (Ward ward : allWardsList) {
            District district = ward.getDistrictid();
            if (!allWards.containsKey(district)) {
                List<Ward> lst = new ArrayList<>();
                lst.add(ward);
                allWards.put(district, lst);
            }
            else {
                List<Ward> lst = allWards.get(district);
                lst.add(ward);
                allWards.put(district, lst);
            }
        }
    }
    
    
    public static String getDistrictsByProvince(String provinceStr) {
        List<District> districts = DistrictDAL.getDistrictByProvinceName(provinceStr);
        for (District district : districts) {
            district.setHotelList(null);
            district.setLocation(null);
            district.setType(null);
            district.setWardList(null);
            district.setProvinceid(null);
        }
        
        Gson gson = new Gson();
        return gson.toJson(districts);
    }
    
    public static String getWardsByDistrict(String districtStr) {
        List<Ward> wards = WardDAL.getWardsByDistrictName(districtStr);
        for (Ward ward : wards) {
            ward.setDistrictid(null);
            ward.setHotelList(null);
            ward.setLocation(null);
            ward.setType(null);
        }
        
        Gson gson = new Gson();
        return gson.toJson(wards);
    }
    
    // get a part of province. return list of provinces
    public static String getProvinceByFragmentName(String provinceStr) {
        provinceStr = Helper.NormalizeString(provinceStr);
        List<Province> provinces = ProvinceDAL.getAllProvinces();
        List<Province> res = new ArrayList<>();
        for (Province province : provinces) {
            String _provinceName = Helper.NormalizeString(province.getName());
            if (_provinceName.contains(provinceStr)) {
                province.setDistrictList(null);
                province.setHotelList(null);
                province.setType(null);
                res.add(province);
            }
        }
        
        Gson gson = new Gson();
        return gson.toJson(res);
    }
    
}
