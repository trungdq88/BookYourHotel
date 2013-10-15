package com.crawler.helper;

import com.library.a.entities.HotelProperties;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Quang Trung
 */
public class ResultModeHelper {

    /**
     * Get property of a selector according to Result Mode. ResultMode: para1
     * para2 para3 ... para1: text or size or attr('src') or attr('title')
     * para2: all (join with ;)
     */
    public static Map<String, String> getPropertyValue(HotelProperties property, Document doc) throws Exception {
        Map<String, String> res = new HashMap<>();
        // TODO: process as query for result mode @@@
        String key = property.getProperties().getName();
        String value = null;

        // how to take data after using CSS Selector
        // such as : text or size or attr('src') or attr('title')
        String[] resultMode = property.getResultMode().split(" ");

        if (resultMode.length == 0) {
            return new HashMap<>();
        }

        Elements elements = doc.select(property.getSelector());

        //Get value with selector
        if (resultMode.length > 1) { //If there is a second parameter
            if (resultMode[1].equals("all")) { //"all": get all value and join with ; seperator
                //Get all element value by selector and join against with ; sperator
                String tmpValue = "";
                for (Element element : elements) {
                    tmpValue += getOnePropertyValue(resultMode[0], element) + ";";
                }
                value = tmpValue;
            } else {
                throw new Exception("Only 'all' parameter is defined");
            }
        } else { // If there is only 1 parameter
            //Get elements value by selector
            value = getAllPropertyValue(property.getResultMode(), elements);
        }

        //Process Regex
        if (property.getAfterRegex() != null) {
            if (!property.getAfterRegex().equals("")) {
                Pattern AFTER_REGEX = Pattern.compile(property.getAfterRegex());
                Matcher m = AFTER_REGEX.matcher(value);
                if (m.find()) {
                    value = m.group(0);
                }
            }
        }
        
        res.put(key, value);
        return res;
    }

    /**
     * Get property with one element
     */
    private static String getOnePropertyValue(String method, Element element) {
        String value = "";
        method = method.toLowerCase().trim();
        if (method.equals("text")) {
            value = element.text();
        } else if (method.startsWith("attr")) {
            String attr = method.replaceAll("attr\\(", "").replaceAll("'", "").replaceAll("\\)", "");
            value = element.attr(attr);
        }
        return value;
    }

    /**
     * Get property value of many element (support .size() and more method)
     */
    private static String getAllPropertyValue(String method, Elements elements) {
        String value = "";
        method = method.toLowerCase().trim();
        if (method.equals("text")) {
            value = elements.text();
        } else if (method.equals("size")) {
            value = elements.size() + "";
        } else if (method.startsWith("attr")) {
            String attr = method.replaceAll("attr\\(", "").replaceAll("'", "").replaceAll("\\)", "");
            value = elements.first().attr(attr);
        }
        return value;
    }
}
