package net.lightapi.portal.form.common.model;


import java.util.ArrayList;
import java.util.List;

public class Schema {

  private String type;
  private String title;
  private List<String> required;
  private List<Property> properties;


  public Schema() {
  }

  public Schema(String type, String title) {
    this.type = type;
    this.title = title;

  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<String> getRequired() {
    return required;
  }

  public void setRequired(List<String> required) {
    this.required = required;
  }

  public List<Property> getProperties() {
    return properties;
  }

  public void setProperties(List<Property> properties) {
    this.properties = properties;
  }

  public void addProperties(Property propertie) {
    if (this.properties == null ) {
      this.properties = new ArrayList<>();
    }
    this.properties.add(propertie);
  }

}
