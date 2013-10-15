package com.crawler.business;

import com.crawler.helper.Config;
import com.library.b.entities.District;
import com.library.b.entities.Hotel;
import com.library.b.entities.Province;
import com.library.b.entities.Ward;
import com.library.dal.DistrictDAL;
import com.library.dal.ProvinceDAL;
import com.library.dal.WardDAL;
import com.library.helper.Helper;
import com.library.helper.LevenshteinDistance;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Normalize all hotels name 
 * Normalize all cities of hotels
 * @author Huynh Quang Thao
 */
public class NormalizationPipeline {

    private static volatile NormalizationPipeline engine;
    List<Province> allCities;
    Map<Province, List<District>> allDistricts = new HashMap<>();
    Map<District, List<Ward>> allWards = new HashMap<>();
    
    private NormalizationPipeline() {
        loadData();
    }

    public static NormalizationPipeline getInstance() {
        if (engine == null) {
            synchronized (CrawlerPipeline.class) {
                if (engine == null) {
                    engine = new NormalizationPipeline();
                }
            }
        }
        return engine;
    }
    
    private void loadData() {
        allCities = ProvinceDAL.getAllProvinces();
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
    
    /**
     * Normalize all hotels in list
     * 1. convert normal address to structure address
     * 2. remove duplicated address in parsing pharse
     */
    public List<Hotel> doWork(List<Hotel> hotels) {
        for (Hotel hotel : hotels) {
            String address = hotel.getAddress();
            Map<String, Object> res = getAddressGeo(address);
            Ward ward = (Ward) res.get("Ward");
            District district = (District) res.get("District");
            Province province = (Province) res.get("Province");
            hotel.setWardid(ward);
            hotel.setDistrictid(district);
            hotel.setProvinceid(province);
        }
        
        return hotels;
    }
    
    /**
     * return map between :
     * "Ward" to Ward Object
     * "District" to District Object
     * "Province" to Province Object
     */
    public Map<String, Object> getAddressGeo(String address) {
        Province defaultProvince = allCities.get(0);
        District defaultDistrict = allDistricts.get(defaultProvince).get(0);
        Ward defaultWard = allWards.get(defaultDistrict).get(0);
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> defaultResult = new HashMap<>();       
        defaultResult.put("Ward", defaultWard);
        defaultResult.put("District", defaultDistrict);
        defaultResult.put("Province", defaultProvince);
        
        
        String[] tokens = address.split(",");
        if (tokens.length < 3) {
            return result;
        }
        
        // normalize this
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = Helper.NormalizeString(tokens[i]);
        }
        
        int count = 0;
        int index = tokens.length - 1;
        
        // process : remove country from address
        // because last string may contains another ...
        String[] VNCountry = new String[] {"vn", "vietnam", "viet nam"};
        for (String vn : VNCountry) {
            if (tokens[index].contains(vn)) {
                index--;
                break;
            }
        }
        if (index < 2) return defaultResult;
        
        Province currProvince = null;
        District currDistrict = null;
        Ward currWard = null;
        int min = Integer.MAX_VALUE;    // variable to store min value of similarity

        // process : get city from address
        String[] cityHeader = new String[] {"tp", "thanh pho", "t.p"};
        // remove those words from string
         String strCity = tokens[index];
         strCity = ReplaceString(strCity, cityHeader);
         
        for (Province city : allCities) {
            int distance = LevenshteinDistance.computeDistance(city.getName(), strCity);
            if (distance < min) {
                min = distance;
                currProvince = city;
            }
            if (distance == 0) break;
        }
        
        if (min <= 5) {
            index--;
            result.put("Province", currProvince);
            District tmpDistrict = allDistricts.get(currProvince).get(0);
            result.put("District", tmpDistrict);
            result.put("Ward", allWards.get(tmpDistrict).get(0));
        }
        else return defaultResult;
        
        if (index < 1) return defaultResult;
        
        // process : get district from address 
        String[] districtHeader = new String[]{"q", "quan"};
        // remove those words from string
        String strDistrict = tokens[index];
        strDistrict = ReplaceString(strDistrict, districtHeader);
        
        min = Integer.MAX_VALUE;
        for (District district : allDistricts.get(currProvince)) {
            int distance = LevenshteinDistance.computeDistance(strDistrict, district.getName());
            if (distance < min) {
                min = distance;
                currDistrict = district;
            }
            if (distance == 0) break;
        }
        if (min <= 6) {
            index--;
            result.put("District", currDistrict);
            result.put("Ward", allWards.get(currDistrict).get(0));
        }
        else return result;
        
        // process : get ward from address
        String[] wardHeader = new String[]{"phuong", "phuong."};
        // remove those words from string
        String strWard1 = tokens[index];
        String strWard2 = strWard1;
        if (index > 1) strWard2 = tokens[0];
        strWard1 = ReplaceString(strWard1, wardHeader);
        strWard2 = ReplaceString(strWard2, wardHeader);
        
        min = Integer.MAX_VALUE;
        for (Ward ward : allWards.get(currDistrict)) {
            int distance = LevenshteinDistance.computeDistance(strWard1, ward.getName());
            if (distance < min) {
                min = distance;
                currWard = ward;
            }
            distance = LevenshteinDistance.computeDistance(strWard2, ward.getName());
            if (distance < min) {
                min = distance;
                currWard = ward;
            }
            if (min == 0) break;
        }
        
        if (min <= 6) {
            result.put("Ward", currWard);
        }
        else return result;
        
        return result;
    }    
    
    private String ReplaceString(String obj, String[] patterns) {
        String res = obj;
        for (String pattern : patterns) {
            if (res.contains(pattern)) res = res.replace(pattern, "");
        }
        return res;
    }
    
    public static void main(String[] args) {
        NormalizationPipeline pipeline = NormalizationPipeline.getInstance();
        String address = "175/21 Phạm Ngũ Lão, Quận 1, TP. Hồ Chí Minh, Việt Nam";
        Map<String, Object> res = pipeline.getAddressGeo(address);
        Ward ward = (Ward) res.get("Ward");
        District district = (District) res.get("District");
        Province province = (Province) res.get("Province");
        System.out.println("Ward: " + ward.getName());
        System.out.println("District: " + district.getName());
        System.out.println("Province: " + province.getName());
    }
}
