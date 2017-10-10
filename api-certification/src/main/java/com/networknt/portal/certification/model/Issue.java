package com.networknt.portal.certification.model;

import com.networknt.config.Config;

import java.util.Map;

public class Issue {
    public static final String CONFIG_NAME = "issue";
    public static final Map<String, Object> config = Config.getInstance().getJsonMapConfigNoCache(CONFIG_NAME);

    String id;
    String description;
    String url;

    public Issue(final String id, final Object... args) {
        this.id = id;
        Map<String, Object> map = (Map<String, Object>)config.get(id);
        if(map != null) {
            this.description = (String)map.get("description");
            this.url = (String)map.get("url");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "{\"id\":" + id
                + ",\"url\":\"" + url
                + "\",\"description\":\""
                + description + "\"}";
    }

}
