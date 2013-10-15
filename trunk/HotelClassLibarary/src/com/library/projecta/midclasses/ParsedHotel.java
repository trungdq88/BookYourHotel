package com.library.projecta.midclasses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParsedHotel {

    private int websiteID;
    private String websiteName;
    private int linkID;
    private String linkUrl;
    private List<ParsedRoom> rooms;
    private Map<String, String> properties;

    public ParsedHotel(int websiteID, String websiteName, int linkID, String linkUrl, List<ParsedRoom> rooms, HashMap<String, String> properties) {
        this.websiteID = websiteID;
        this.websiteName = websiteName;
        this.linkID = linkID;
        this.linkUrl = linkUrl;
        this.rooms = rooms;
        this.properties = properties;
    }

    public ParsedHotel() {
        this.websiteID = 0;
        this.websiteName = "";
        this.linkID = 0;
        this.linkUrl = "";
        this.rooms = null;
        this.properties = null;
    }

    public int getWebsiteID() {
        return websiteID;
    }

    public void setWebsiteID(int websiteID) {
        this.websiteID = websiteID;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public int getLinkID() {
        return linkID;
    }

    public void setLinkID(int linkID) {
        this.linkID = linkID;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public List<ParsedRoom> getRooms() {
        return rooms;
    }

    public void setRooms(List<ParsedRoom> rooms) {
        this.rooms = rooms;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.websiteID;
        hash = 29 * hash + (this.websiteName != null ? this.websiteName.hashCode() : 0);
        hash = 29 * hash + this.linkID;
        hash = 29 * hash + (this.linkUrl != null ? this.linkUrl.hashCode() : 0);
        hash = 29 * hash + (this.rooms != null ? this.rooms.hashCode() : 0);
        hash = 29 * hash + (this.properties != null ? this.properties.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "ParsedHotel{" + "websiteID=" + websiteID + ", websiteName=" + websiteName + ", linkID=" + linkID + ", linkUrl=" + linkUrl + ", rooms=" + rooms + ", properties=" + properties + '}';
    }
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ParsedHotel other = (ParsedHotel) obj;
        if (this.websiteID != other.websiteID) {
            return false;
        }
        if ((this.websiteName == null) ? (other.websiteName != null) : !this.websiteName.equals(other.websiteName)) {
            return false;
        }
        if (this.linkID != other.linkID) {
            return false;
        }
        if ((this.linkUrl == null) ? (other.linkUrl != null) : !this.linkUrl.equals(other.linkUrl)) {
            return false;
        }
        if (this.rooms != other.rooms && (this.rooms == null || !this.rooms.equals(other.rooms))) {
            return false;
        }
        if (this.properties != other.properties && (this.properties == null || !this.properties.equals(other.properties))) {
            return false;
        }
        return true;
    }



    
}