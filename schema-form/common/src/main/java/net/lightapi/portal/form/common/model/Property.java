package net.lightapi.portal.form.common.model;


public class Property {
  private String name;
  private boolean readonly;

  private String type;
  private String title;
  private Integer maxItems;


  public Property() {
  }

  public Property(String name, String type, String title, boolean readonly, Integer maxItems) {
    this.name = name;
    this.type = type;
    this.title = title;
    this.readonly = readonly;
    this.maxItems = maxItems;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isReadonly() {
    return readonly;
  }

  public void setReadonly(boolean readonly) {
    this.readonly = readonly;
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

  public Integer getMaxItems() {
    return maxItems;
  }

  public void setMaxItems(Integer maxItems) {
    this.maxItems = maxItems;
  }
}
