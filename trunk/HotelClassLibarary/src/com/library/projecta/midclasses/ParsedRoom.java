package com.library.projecta.midclasses;

import java.util.HashMap;
import java.util.Map;

public class ParsedRoom {
    private Map<String, String> properties;

    public ParsedRoom() {
        this.properties = null;
    }

    public ParsedRoom(HashMap<String, String> properties) {
        this.properties = properties;
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
        hash = 67 * hash + (this.properties != null ? this.properties.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ParsedRoom other = (ParsedRoom) obj;
        if (this.properties != other.properties && (this.properties == null || !this.properties.equals(other.properties))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ParsedRoom{" + "properties=" + properties + '}';
    }
}