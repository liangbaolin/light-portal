package net.lightapi.portal.form.common.model;


public class FormRequest {

  private String host;
  private String service;
  private String action;
  private String version;
  private Form data;

  public FormRequest() {
  }

  public FormRequest(String host, String service, String action, String version) {
    this.host = host;
    this.service = service;
    this.action = action;
    this.version = version;

  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public Form getData() {
    return data;
  }

  public void setData(Form data) {
    this.data = data;
  }
}
