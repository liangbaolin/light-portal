package net.lightapi.portal.form.common.model;


public class FormField {
  private String key;
  private boolean multiple;

  private String type;
  private String rows;

  public FormField() {
  }

  public FormField(String key, String type, boolean multiple, String rows) {
    this.key = key;
    this.type = type;
    this.multiple = multiple;
    this.rows = rows;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public boolean isMultiple() {
    return multiple;
  }

  public void setMultiple(boolean multiple) {
    this.multiple = multiple;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getRows() {
    return rows;
  }

  public void setRows(String rows) {
    this.rows = rows;
  }
}
