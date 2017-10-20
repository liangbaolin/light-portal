package net.lightapi.portal.form.common.model;


import java.util.ArrayList;
import java.util.List;

public class Form {
  private String formId;
  private String version;
  private String description;

  private Action action;
  private Schema schema;
  private List<FormField> formFields;


  public Form() {
  }

  public Form(String formId, String version) {
    this.formId = formId;
    this.version = version;

  }

  public String getFormId() {
    return formId;
  }

  public void setFormId(String formId) {
    this.formId = formId;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Action getAction() {
    return action;
  }

  public void setAction(Action action) {
    this.action = action;
  }

  public Schema getSchema() {
    return schema;
  }

  public void setSchema(Schema schema) {
    this.schema = schema;
  }

  public List<FormField> getFormFields() {
    return formFields;
  }

  public void setFormFields(List<FormField> formFields) {
    this.formFields = formFields;
  }

  public void addFormField(FormField formField) {
    if (this.formFields == null ) {
      this.formFields = new ArrayList<>();
    }
    this.formFields.add(formField);
  }
}
