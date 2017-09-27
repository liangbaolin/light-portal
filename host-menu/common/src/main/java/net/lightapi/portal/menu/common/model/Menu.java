package net.lightapi.portal.menu.common.model;

import com.arangodb.velocypack.annotations.SerializedName;

import java.util.List;

public class Menu {
    @SerializedName("_key")
    String host;         // this is key host for menu
    String description; // description for the host menu
    List<String> contains;  // menu items that belong to this host

    public Menu() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getContains() {
        return contains;
    }

    public void setContains(List<String> contains) {
        this.contains = contains;
    }
}
