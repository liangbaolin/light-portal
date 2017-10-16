package net.lightapi.portal.form;

import java.io.IOException;

public interface FormRepository {

    void setDataSource(Object dataSource);

    Object getDataSource();

    String getForm();

    String getFormById(String id);

    String getFormByEntityId(String id);

    void createForm(String entityId, String data);

    void updateForm(String entityId, String data);

    void removeForm(String entityId);
}
