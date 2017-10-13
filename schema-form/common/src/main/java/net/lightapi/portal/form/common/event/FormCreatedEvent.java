package net.lightapi.portal.form.common.event;

public class FormCreatedEvent implements FormEvent {
    String data;

    public FormCreatedEvent() {
    }

    public FormCreatedEvent(String data) {
        this.data = data;
    }

    public String getForm() {
        return data;
    }

    public void setForm(String data) {
        this.data = data;
    }
}
